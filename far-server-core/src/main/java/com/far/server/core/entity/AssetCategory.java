package com.far.server.core.entity;

import java.util.Arrays;

public enum AssetCategory {
    RESIDENCE(11, 1.5),
    GARAGE(102, 4.5),
    SPORT_BUILDING(290, 2.5),
    POWER_BOILER(3, 7.0),
    GAS_ENGINE(324, 14.0),
    GAS_PUMP(44, 14.0),
    CRYSTALIZER(507, 7.0),
    TECHNICAL_EQUIPMENT(6, 10.0),
    CONTAINER(681, 18.0),
    CAR(741, 20.0),
    BUS(744, 20.0),
    TRACTOR(746, 14.0),
    MOBILE_PHONE(629, 20.0);

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
