package org.example.portfolioanalysisapi.risk.dto;

import java.math.BigDecimal;

public class ScenarioShockRequest {

    private BigDecimal stockShock;
    private BigDecimal cryptoShock;
    private BigDecimal etfShock;

    public BigDecimal getStockShock() {
        return stockShock;
    }

    public void setStockShock(BigDecimal stockShock) {
        this.stockShock = stockShock;
    }

    public BigDecimal getCryptoShock() {
        return cryptoShock;
    }

    public void setCryptoShock(BigDecimal cryptoShock) {
        this.cryptoShock = cryptoShock;
    }

    public BigDecimal getEtfShock() {
        return etfShock;
    }

    public void setEtfShock(BigDecimal etfShock) {
        this.etfShock = etfShock;
    }
}
