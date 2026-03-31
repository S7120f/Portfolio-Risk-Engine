package org.example.portfolioanalysisapi.risk.controller;

import org.example.portfolioanalysisapi.portfolio.Portfolio;
import org.example.portfolioanalysisapi.portfolio.PortfolioRepository;
import org.example.portfolioanalysisapi.risk.dto.*;
import org.example.portfolioanalysisapi.risk.service.RiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/analytics")
public class RiskController {

    private final RiskService riskService;
    private final PortfolioRepository portfolioRepository;

    public RiskController(RiskService riskService, PortfolioRepository portfolioRepository) {
        this.riskService = riskService;
        this.portfolioRepository = portfolioRepository;
    }

    @GetMapping("/{ticker}/volatility")
    public ResponseEntity<VolatilityResponse> getVolatility(@PathVariable String ticker) {
        BigDecimal volatility = riskService.getVolatilityForTicker(ticker);

        VolatilityResponse response = new VolatilityResponse();
        response.setTicker(ticker);
        response.setVolatility(volatility);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ticker}/max-drawdown")
    public ResponseEntity<MaxDrawdownResponse> getMaxDrawdown(@PathVariable String ticker) {
        BigDecimal maxDrawdown = riskService.getMaxDaxDrawdownForTicker(ticker);

        MaxDrawdownResponse response = new MaxDrawdownResponse();
        response.setMaxDrawdown(maxDrawdown);
        response.setTicker(ticker);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/correlation")
    public ResponseEntity<CorrelationResponse> getCorrelation(@RequestParam String tickerA, @RequestParam String tickerB) {
        BigDecimal correlation = riskService.getCorrelationForTicker(tickerA, tickerB);

        CorrelationResponse response = new CorrelationResponse();
        response.setTickerA(tickerA);
        response.setTickerB(tickerB);
        response.setCorrelation(correlation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/portfolio/{portfolioId}/volatility")
    public ResponseEntity<PortfolioVolatilityResponse> getVolatilityForPortfolio(@PathVariable long portfolioId) {
        portfolioRepository.findById(portfolioId).orElseThrow(() -> new RuntimeException("No portfolio found"));
        PortfolioVolatilityResponse response = riskService.getVolatilityForPortfolio(portfolioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/portfolio/{portfolioId}/max-drawdown")
    public ResponseEntity<PortfolioMaxDrawdownResponse> getMaxDrawdownForPortfolio(@PathVariable long portfolioId) {
        portfolioRepository.findById(portfolioId).orElseThrow(() -> new RuntimeException("No portfolio found"));
        PortfolioMaxDrawdownResponse response = riskService.getMaxDrawdownForPortfolio(portfolioId);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/portfolio/{portfolioId}/correlation")
    public ResponseEntity<PortfolioCorrelationResponse> getCorrelationForPortfolio(@PathVariable long portfolioId) {
        portfolioRepository.findById(portfolioId).orElseThrow(() -> new RuntimeException("No portfolio found"));
        PortfolioCorrelationResponse response = riskService.getCorrelationForPortfolio(portfolioId);
        return ResponseEntity.ok(response);
    }
}
