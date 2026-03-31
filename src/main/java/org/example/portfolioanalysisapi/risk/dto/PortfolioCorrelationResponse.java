package org.example.portfolioanalysisapi.risk.dto;

import java.util.List;

public class PortfolioCorrelationResponse {

    private long portfolioId;
    private String portfolioName;
    List<PortfolioAssetCorrelationResponse> correlations;


    public long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public List<PortfolioAssetCorrelationResponse> getCorrelations() {
        return correlations;
    }

    public void setCorrelations(List<PortfolioAssetCorrelationResponse> correlations) {
        this.correlations = correlations;
    }
}
