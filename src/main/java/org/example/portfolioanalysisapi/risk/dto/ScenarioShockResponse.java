package org.example.portfolioanalysisapi.risk.dto;

import java.math.BigDecimal;
import java.util.List;

public class ScenarioShockResponse {

    private Long portfolioId;
    private String portfolioName;
    private BigDecimal totalValueBefore;
    private BigDecimal totalValueAfter;
    private BigDecimal totalChange;
    private BigDecimal totalChangePercent;
    private List<PortfolioHoldingScenarioResponse> holdings;


    public List<PortfolioHoldingScenarioResponse> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<PortfolioHoldingScenarioResponse> holdings) {
        this.holdings = holdings;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public BigDecimal getTotalValueBefore() {
        return totalValueBefore;
    }

    public void setTotalValueBefore(BigDecimal totalValueBefore) {
        this.totalValueBefore = totalValueBefore;
    }

    public BigDecimal getTotalValueAfter() {
        return totalValueAfter;
    }

    public void setTotalValueAfter(BigDecimal totalValueAfter) {
        this.totalValueAfter = totalValueAfter;
    }

    public BigDecimal getTotalChange() {
        return totalChange;
    }

    public void setTotalChange(BigDecimal totalChange) {
        this.totalChange = totalChange;
    }

    public BigDecimal getTotalChangePercent() {
        return totalChangePercent;
    }

    public void setTotalChangePercent(BigDecimal totalChangePercent) {
        this.totalChangePercent = totalChangePercent;
    }
}
