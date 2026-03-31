package org.example.portfolioanalysisapi.risk.dto;

import org.example.portfolioanalysisapi.asset.AssetType;

import java.math.BigDecimal;

public class PortfolioHoldingVolatilityResponse {

    private String ticker;
    private BigDecimal quantity;
    private AssetType assetType;
    private BigDecimal latestPrice;
    private BigDecimal marketValue;
    private BigDecimal weight;
    private BigDecimal assetVolatility;

    public BigDecimal getAssetVolatility() {
        return assetVolatility;
    }

    public void setAssetVolatility(BigDecimal assetVolatility) {
        this.assetVolatility = assetVolatility;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
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

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
