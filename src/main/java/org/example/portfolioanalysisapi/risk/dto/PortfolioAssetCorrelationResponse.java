package org.example.portfolioanalysisapi.risk.dto;

import java.math.BigDecimal;

public class PortfolioAssetCorrelationResponse {

    private String tickerA;
    private String tickerB;
    private BigDecimal correlation;


    public String getTickerA() {
        return tickerA;
    }

    public void setTickerA(String tickerA) {
        this.tickerA = tickerA;
    }

    public String getTickerB() {
        return tickerB;
    }

    public void setTickerB(String tickerB) {
        this.tickerB = tickerB;
    }

    public BigDecimal getCorrelation() {
        return correlation;
    }

    public void setCorrelation(BigDecimal correlation) {
        this.correlation = correlation;
    }
}
