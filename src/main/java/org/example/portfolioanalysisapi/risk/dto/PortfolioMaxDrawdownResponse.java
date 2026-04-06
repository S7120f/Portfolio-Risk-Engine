package org.example.portfolioanalysisapi.risk.dto;


import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PortfolioMaxDrawdownResponse {

    private Long portfolioId;
    private String portfolioName;
    private BigDecimal maxDrawdown;
    private BigDecimal peakValue;
    private LocalDate peakDate;
    private BigDecimal troughValue;
    private LocalDate troughDate;


    public BigDecimal getPeakValue() {
        return peakValue;
    }

    public void setPeakValue(BigDecimal peakValue) {
        this.peakValue = peakValue;
    }

    public BigDecimal getTroughValue() {
        return troughValue;
    }

    public void setTroughValue(BigDecimal troughValue) {
        this.troughValue = troughValue;
    }

    public LocalDate getPeakDate() {
        return peakDate;
    }

    public void setPeakDate(LocalDate peakDate) {
        this.peakDate = peakDate;
    }

    public LocalDate getTroughDate() {
        return troughDate;
    }

    public void setTroughDate(LocalDate troughDate) {
        this.troughDate = troughDate;
    }

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
