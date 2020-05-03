package com.far.server.core.entity;

import java.util.Arrays;

public enum AssetCategory {
    CAR(1, 20.0),
    PHONE(2, 7.0);

    private final int categoryId;
    private final double amortizationPercentage;

    AssetCategory(Integer categoryId, Double amortizationPercentage) {
        this.categoryId = categoryId;
        this.amortizationPercentage = amortizationPercentage;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Double getAmortizationPercentage() {
        return amortizationPercentage;
    }

    public static AssetCategory valueOfById(String id) {
        return Arrays.stream(AssetCategory.values())
            .filter(cat -> cat.getCategoryId().toString().equals(id))
            .findFirst()
            .orElseThrow();
    }
}
