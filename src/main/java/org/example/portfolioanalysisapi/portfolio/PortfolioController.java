package org.example.portfolioanalysisapi.portfolio;


import org.example.portfolioanalysisapi.portfolio.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio(@RequestBody CreatePortfolioRequest request ) {
        PortfolioResponse savedPortfolio = portfolioService.createPortfolio(request);
        return ResponseEntity.ok(savedPortfolio);
    }

    @PostMapping("/{portfolioId}/holdings")
    public ResponseEntity<AddHoldingResponse> addHoldingToPortfolio(@PathVariable Long portfolioId, @RequestBody AddHoldingRequest request) {
        AddHoldingResponse response = portfolioService.addHolding(portfolioId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> getAllPortfolios() {
        List<PortfolioResponse> portfolios = portfolioService.getAllPortfolios();
        return ResponseEntity.ok(portfolios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDetailsResponse> getPortfolioById(@PathVariable Long id) {
        PortfolioDetailsResponse portfolio = portfolioService.getPortfolioById(id);
        return ResponseEntity.ok(portfolio);
    }

    @GetMapping("/overview")
    public ResponseEntity<List<PortfolioDetailsResponse>> getPortfolioOverview() {
        List<PortfolioDetailsResponse> overview = portfolioService.getPortfolioOverview();
        return ResponseEntity.ok(overview);
    }
}
