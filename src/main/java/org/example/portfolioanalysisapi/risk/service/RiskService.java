package org.example.portfolioanalysisapi.risk.service;


import lombok.extern.slf4j.Slf4j;
import org.example.portfolioanalysisapi.asset.AssetType;
import org.example.portfolioanalysisapi.marketdata.MarketDataService;
import org.example.portfolioanalysisapi.marketdata.PricePoint;
import org.example.portfolioanalysisapi.portfolio.Portfolio;
import org.example.portfolioanalysisapi.portfolio.PortfolioAsset;
import org.example.portfolioanalysisapi.portfolio.PortfolioRepository;
import org.example.portfolioanalysisapi.risk.calculation.CorrelationCalculator;
import org.example.portfolioanalysisapi.risk.calculation.DrawdownResult;
import org.example.portfolioanalysisapi.risk.calculation.MaxDrawdownCalculator;
import org.example.portfolioanalysisapi.risk.calculation.VolatilityCalculator;
import org.example.portfolioanalysisapi.risk.calculation.support.ReturnSeriesCalculator;
import org.example.portfolioanalysisapi.risk.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RiskService {

    final private MarketDataService marketDataService;
    final private VolatilityCalculator volatilityCalculator;
    final private MaxDrawdownCalculator maxDrawdownCalculator;
    final private CorrelationCalculator correlationCalculator;
    final private ReturnSeriesCalculator returnSeriesCalculator;
    final private PortfolioRepository portfolioRepository;

    public RiskService(MarketDataService marketDataService, VolatilityCalculator volatilityCalculator,
                       MaxDrawdownCalculator maxDrawdownCalculator, CorrelationCalculator correlationCalculator,
                       ReturnSeriesCalculator returnSeriesCalculator, PortfolioRepository portfolioRepository) {
        this.marketDataService = marketDataService;
        this.volatilityCalculator = volatilityCalculator;
        this.maxDrawdownCalculator = maxDrawdownCalculator;
        this.correlationCalculator = correlationCalculator;
        this.returnSeriesCalculator = returnSeriesCalculator;
        this.portfolioRepository = portfolioRepository;
    }

    public BigDecimal getVolatilityForTicker(String ticker) {

        List<PricePoint> pricePoints = marketDataService.getPriceHistory(ticker);
        return volatilityCalculator.calculateVolatility(pricePoints);
    }

    public DrawdownResult getMaxDaxDrawdownForTicker(String ticker) {

        List<PricePoint> pricePoints = marketDataService.getPriceHistory(ticker);
        return maxDrawdownCalculator.calculateMaxDrawdown(pricePoints);
    }

    public BigDecimal getCorrelationForTicker(String tickerA, String tickerB) {

        List<PricePoint> pricePointsA = marketDataService.getPriceHistory(tickerA);
        List<PricePoint> pricePointsB = marketDataService.getPriceHistory(tickerB);

        List<BigDecimal> returnA = returnSeriesCalculator.dailyReturns(pricePointsA);
        List<BigDecimal> returnB = returnSeriesCalculator.dailyReturns(pricePointsB);

        return correlationCalculator.calculateCorrelation(returnA, returnB);
    }

    public PortfolioVolatilityResponse getVolatilityForPortfolio(long portfolioId) {

        Portfolio portfolio = getPortfolioOrThrow(portfolioId);

        List<PortfolioAsset> holdings = portfolio.getHoldings();
        validatePortfolioHasHoldings(holdings);

        List<PortfolioHoldingVolatilityResponse> holdingsResponses = new ArrayList<>();

        for (PortfolioAsset holding : holdings) {

            AssetType assetType = holding.getAsset().getAssetType();
            String ticker = holding.getAsset().getTicker();
            BigDecimal quantity = holding.getQuantity();
            List<PricePoint> pricePoints = marketDataService.getPriceHistory(ticker);
            BigDecimal volatility = volatilityCalculator.calculateVolatility(pricePoints);
            PricePoint closePrice = pricePoints.get(pricePoints.size() - 1);
            BigDecimal latestPrice = closePrice.getClosePrice();
            BigDecimal marketValue = quantity.multiply(latestPrice);

            PortfolioHoldingVolatilityResponse holdingResult = new PortfolioHoldingVolatilityResponse();
            holdingResult.setAssetType(assetType);
            holdingResult.setTicker(ticker);
            holdingResult.setQuantity(quantity);
            holdingResult.setLatestPrice(latestPrice);
            holdingResult.setMarketValue(marketValue);
            holdingResult.setAssetVolatility(volatility);
            holdingsResponses.add(holdingResult);
        }

        BigDecimal totalValue = BigDecimal.ZERO;
        for (PortfolioHoldingVolatilityResponse data : holdingsResponses) {
            totalValue = totalValue.add(data.getMarketValue());
        }

        if (totalValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("Total portfolio value is zero");
        }


        for (PortfolioHoldingVolatilityResponse data : holdingsResponses) {
            BigDecimal weight = data.getMarketValue().divide(totalValue, 6, RoundingMode.HALF_UP);
            data.setWeight(weight);
        }

        BigDecimal portfolioVolatility = BigDecimal.ZERO;
        for (PortfolioHoldingVolatilityResponse data : holdingsResponses) {
            BigDecimal contribution = data.getWeight().multiply(data.getAssetVolatility());
            portfolioVolatility = portfolioVolatility.add(contribution);
        }

        PortfolioVolatilityResponse result = new PortfolioVolatilityResponse();
        result.setPortfolioId(portfolio.getId());
        result.setPortfolioName(portfolio.getName());
        result.setTotalValue(totalValue);
        result.setPortfolioVolatility(portfolioVolatility);
        result.setHoldings(holdingsResponses);

        return result;
    }

    public PortfolioMaxDrawdownResponse getMaxDrawdownForPortfolio(long portfolioId) {

        Portfolio portfolio = getPortfolioOrThrow(portfolioId);
        List<PortfolioAsset> holdings = portfolio.getHoldings();
        validatePortfolioHasHoldings(holdings);


        DrawdownResult result = maxDrawdownCalculator.calculateMaxDrawdown(buildPortfolioPriceSeries(holdings));

        PortfolioMaxDrawdownResponse response = new PortfolioMaxDrawdownResponse();
        response.setPortfolioId(portfolio.getId());
        response.setPortfolioName(portfolio.getName());
        response.setMaxDrawdown(result.maxDrawdown());
        response.setPeakValue(result.peakValue());
        response.setPeakDate(result.peakDate());
        response.setTroughValue(result.troughValue());
        response.setTroughDate(result.troughDate());

        return response;
    }


    public PortfolioCorrelationResponse getCorrelationForPortfolio(long portfolioId) {

        Portfolio portfolio = getPortfolioOrThrow(portfolioId);
        List<PortfolioAsset> holdings = portfolio.getHoldings();
        validatePortfolioHasHoldings(holdings);

        List<PortfolioAssetCorrelationResponse> correlationResults = new ArrayList<>();


        for (int i = 0; i < holdings.size(); i++) {

            for (int j = i + 1; j < holdings.size(); j++) {

                PortfolioAsset holdingA = holdings.get(i);
                PortfolioAsset holdingB = holdings.get(j);

                String tickerA = holdingA.getAsset().getTicker();
                String tickerB = holdingB.getAsset().getTicker();

                List<PricePoint> pricePointsA = marketDataService.getPriceHistory(tickerA);
                List<PricePoint> pricePointsB = marketDataService.getPriceHistory(tickerB);


                List<BigDecimal> dailyReturnA = returnSeriesCalculator.dailyReturns(pricePointsA);
                List<BigDecimal> dailyReturnB = returnSeriesCalculator.dailyReturns(pricePointsB);
                BigDecimal correlation = correlationCalculator.calculateCorrelation(dailyReturnA, dailyReturnB);

                PortfolioAssetCorrelationResponse correlationResponse = new PortfolioAssetCorrelationResponse();
                correlationResponse.setTickerA(tickerA);
                correlationResponse.setTickerB(tickerB);
                correlationResponse.setCorrelation(correlation);

                correlationResults.add(correlationResponse);
            }

        }

        PortfolioCorrelationResponse portfolioResponse = new PortfolioCorrelationResponse();
        portfolioResponse.setPortfolioId(portfolio.getId());
        portfolioResponse.setPortfolioName(portfolio.getName());
        portfolioResponse.setCorrelations(correlationResults);

        return portfolioResponse;
    }

    public ScenarioShockResponse calculateScenarioForPortfolio(long portfolioId, ScenarioShockRequest request) {

        Portfolio portfolio = getPortfolioOrThrow(portfolioId);
        List<PortfolioAsset> holdings = portfolio.getHoldings();
        validatePortfolioHasHoldings(holdings);

        BigDecimal totalValueBefore = BigDecimal.ZERO;
        BigDecimal totalValueAfter = BigDecimal.ZERO;

        List<PortfolioHoldingScenarioResponse> holdingsResponses = new ArrayList<>();

        for (PortfolioAsset holding : holdings) {

            String ticker = holding.getAsset().getTicker();
            List<PricePoint> pricePointList = marketDataService.getPriceHistory(ticker);

            PricePoint lastestPricePoint = pricePointList.get(pricePointList.size() - 1);
            BigDecimal closePrice = lastestPricePoint.getClosePrice();

            BigDecimal value = closePrice.multiply(holding.getQuantity());
            totalValueBefore = totalValueBefore.add(value);

            AssetType assetType = holding.getAsset().getAssetType();

            BigDecimal shock;

            if (assetType == AssetType.STOCK) {
                shock = request.getStockShock();
            } else if (assetType == AssetType.CRYPTO) {
                shock = request.getCryptoShock();
            } else {
                shock = request.getEtfShock();
            }

            BigDecimal onePlusShock = BigDecimal.ONE.add(shock);
            BigDecimal valueAfter = value.multiply(onePlusShock);

            totalValueAfter = totalValueAfter.add(valueAfter);

            PortfolioHoldingScenarioResponse holdingsResult = new PortfolioHoldingScenarioResponse();
            holdingsResult.setAssetType(assetType);
            holdingsResult.setQuantity(holding.getQuantity());
            holdingsResult.setTicker(ticker);
            holdingsResult.setLatestPrice(closePrice);
            holdingsResult.setValueBefore(value);
            holdingsResult.setAppliedShock(shock);
            holdingsResult.setValueAfter(valueAfter);

            BigDecimal holdingChange = valueAfter.subtract(value);
            holdingsResult.setChange(holdingChange);

            holdingsResponses.add(holdingsResult);

        }

        if (totalValueBefore.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("Total portfolio value is zero");
        }

        BigDecimal totalChange = totalValueAfter.subtract(totalValueBefore);
        BigDecimal totalChangePercent = totalChange.divide(totalValueBefore, 6, RoundingMode.HALF_UP);

        ScenarioShockResponse response = new ScenarioShockResponse();
        response.setPortfolioId(portfolio.getId());
        response.setPortfolioName(portfolio.getName());
        response.setTotalValueBefore(totalValueBefore);
        response.setTotalValueAfter(totalValueAfter);
        response.setTotalChange(totalChange);
        response.setTotalChangePercent(totalChangePercent);
        response.setHoldings(holdingsResponses);

        return response;
    }

    // Helper
    private Portfolio getPortfolioOrThrow(long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("No portfolio found"));
    }

    private void validatePortfolioHasHoldings(List<PortfolioAsset> holdings) {
        if (holdings.isEmpty()) {
            throw new IllegalArgumentException("portfolio is empty");
        }
    }

    private List<PricePoint> buildPortfolioPriceSeries(List<PortfolioAsset> holdings) {

        PortfolioAsset firstHolding = holdings.get(0);
        String ticker = firstHolding.getAsset().getTicker();
        List<PricePoint> referencePricePoints = marketDataService.getPriceHistory(ticker);
        int numberOfDays = referencePricePoints.size();

        List<PricePoint> portfolioPricePoints = new ArrayList<>();

        for (int i = 0; i < numberOfDays; i++) {
            BigDecimal portfolioValueForDay = BigDecimal.ZERO;
            LocalDate date = referencePricePoints.get(i).getDate();


            for (PortfolioAsset holding : holdings) {
                String tickerForHolding = holding.getAsset().getTicker();
                BigDecimal quantity = holding.getQuantity();

                List<PricePoint> pricePointList = marketDataService.getPriceHistory(tickerForHolding);

                PricePoint pricePointForDay = pricePointList.get(i);
                BigDecimal closePrice = pricePointForDay.getClosePrice();

                BigDecimal holdingValueForDay = quantity.multiply(closePrice);

                portfolioValueForDay = portfolioValueForDay.add(holdingValueForDay);

            }

            portfolioPricePoints.add(new PricePoint(date, portfolioValueForDay));

        }

        return portfolioPricePoints;
    }


}
