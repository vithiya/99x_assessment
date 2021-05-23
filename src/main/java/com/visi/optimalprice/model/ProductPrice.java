package com.visi.optimalprice.model;

import java.util.Map;

public class ProductPrice {
    private String productName;
    private long productId;
    private Map<Integer,Double> prices;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Map<Integer, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<Integer, Double> prices) {
        this.prices = prices;
    }
}
