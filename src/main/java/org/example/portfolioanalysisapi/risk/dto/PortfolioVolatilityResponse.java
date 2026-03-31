package org.example.portfolioanalysisapi.risk.dto;


import java.math.BigDecimal;
import java.util.List;

public class PortfolioVolatilityResponse {

    private Long portfolioId;
    private String portfolioName;
    private BigDecimal totalValue;
    private BigDecimal portfolioVolatility;
    private List<PortfolioHoldingVolatilityResponse> holdings;

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

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getPortfolioVolatility() {
        return portfolioVolatility;
    }

    public void setPortfolioVolatility(BigDecimal portfolioVolatility) {
        this.portfolioVolatility = portfolioVolatility;
    }

    public List<PortfolioHoldingVolatilityResponse> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<PortfolioHoldingVolatilityResponse> holdings) {
        this.holdings = holdings;
    }
}
