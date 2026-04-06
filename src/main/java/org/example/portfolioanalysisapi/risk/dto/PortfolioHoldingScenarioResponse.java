package org.example.portfolioanalysisapi.risk.dto;

import org.example.portfolioanalysisapi.asset.AssetType;

import java.math.BigDecimal;

public class PortfolioHoldingScenarioResponse {

    private AssetType assetType;
    private String ticker;
    private BigDecimal quantity;
    private BigDecimal latestPrice;
    private BigDecimal valueBefore;
    private BigDecimal appliedShock;
    private BigDecimal valueAfter;
    private BigDecimal change;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(BigDecimal valueBefore) {
        this.valueBefore = valueBefore;
    }

    public BigDecimal getAppliedShock() {
        return appliedShock;
    }

    public void setAppliedShock(BigDecimal appliedShock) {
        this.appliedShock = appliedShock;
    }

    public BigDecimal getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(BigDecimal valueAfter) {
        this.valueAfter = valueAfter;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}
