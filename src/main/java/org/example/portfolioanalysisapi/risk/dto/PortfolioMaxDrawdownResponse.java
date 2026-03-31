package org.example.portfolioanalysisapi.risk.dto;


import jakarta.persistence.Id;

import java.math.BigDecimal;

public class PortfolioMaxDrawdownResponse {

    private Long portfolioId;
    private String portfolioName;
    private BigDecimal maxDrawdown;

    public BigDecimal getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(BigDecimal maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }
}
