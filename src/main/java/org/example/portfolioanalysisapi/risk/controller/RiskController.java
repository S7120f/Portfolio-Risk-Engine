package org.example.portfolioanalysisapi.risk.controller;

import org.example.portfolioanalysisapi.portfolio.Portfolio;
import org.example.portfolioanalysisapi.portfolio.PortfolioRepository;
import org.example.portfolioanalysisapi.risk.calculation.DrawdownResult;
import org.example.portfolioanalysisapi.risk.dto.*;
import org.example.portfolioanalysisapi.risk.service.RiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:4200")
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
        DrawdownResult result = riskService.getMaxDaxDrawdownForTicker(ticker);

        MaxDrawdownResponse response = new MaxDrawdownResponse();
        response.setMaxDrawdown(result.maxDrawdown());
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
        PortfolioVolatilityResponse response = riskService.getVolatilityForPortfolio(portfolioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/portfolio/{portfolioId}/max-drawdown")
    public ResponseEntity<PortfolioMaxDrawdownResponse> getMaxDrawdownForPortfolio(@PathVariable long portfolioId) {
        PortfolioMaxDrawdownResponse response = riskService.getMaxDrawdownForPortfolio(portfolioId);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/portfolio/{portfolioId}/correlation")
    public ResponseEntity<PortfolioCorrelationResponse> getCorrelationForPortfolio(@PathVariable long portfolioId) {
        PortfolioCorrelationResponse response = riskService.getCorrelationForPortfolio(portfolioId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/portfolio/{portfolioId}/scenario")
    public ResponseEntity<ScenarioShockResponse> calculateScenarioSimulationForPortfolio(@PathVariable long portfolioId, @RequestBody ScenarioShockRequest request) {
        ScenarioShockResponse response = riskService.calculateScenarioForPortfolio(portfolioId, request);
        return ResponseEntity.ok(response);
    }
}
