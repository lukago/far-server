package com.far.server.core.dto;

import java.time.Instant;

public class AssetAmortizationDto extends AssetDto {
    Double amortizationRateInPercentages;
    Double amountAmortized;
    Instant amortizationCalculationDate;

    private AssetAmortizationDto() {}

    public AssetAmortizationDto(String assetName,
        String documentName,
        String categoryId,
        Instant purchaseDate,
        Double purchaseAmountPLN,
        Instant entryDate,
        String amortizationType,
        Double amortizationRateInPercentages,
        Double amountAmortized,
        Instant amortizationCalculationDate,
        Double digressiveAmortizationCoefficient,
        Instant invalidationDate) {
        super(assetName,
            documentName,
            categoryId,
            purchaseDate,
            purchaseAmountPLN,
            entryDate,
            amortizationType,
            digressiveAmortizationCoefficient,
            invalidationDate);
        this.amortizationRateInPercentages = amortizationRateInPercentages;
        this.amountAmortized = amountAmortized;
        this.amortizationCalculationDate = amortizationCalculationDate;
    }

    public Double getAmortizationRateInPercentages() {
        return amortizationRateInPercentages;
    }

    public void setAmortizationRateInPercentages(Double amortizationRateInPercentages) {
        this.amortizationRateInPercentages = amortizationRateInPercentages;
    }

    public Double getAmountAmortized() {
        return amountAmortized;
    }

    public void setAmountAmortized(Double amountAmortized) {
        this.amountAmortized = amountAmortized;
    }

    public Instant getAmortizationCalculationDate() {
        return amortizationCalculationDate;
    }

    public void setAmortizationCalculationDate(Instant amortizationCalculationDate) {
        this.amortizationCalculationDate = amortizationCalculationDate;
    }
}
