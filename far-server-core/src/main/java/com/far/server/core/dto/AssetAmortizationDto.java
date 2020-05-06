package com.far.server.core.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AssetAmortizationDto extends AssetDto {
    Double amortizationRateInPercentages;
    Double amountAmortized;
    Instant amortizationCalculationDate;
    AmortizationMeta amortizationMeta;

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
        Instant invalidationDate,
        AmortizationMeta amortizationMeta) {
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
        this.amortizationMeta = amortizationMeta;
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

    public static class AmortizationMeta {
        public AmortizationMeta() {
            this.amortizationMonthEntries = new ArrayList<>();
        }

        public List<AmortizationMonthEntry> amortizationMonthEntries;
    }

    public static class AmortizationMonthEntry {
        public Instant monthStartDate;
        public String amortizationType;
        public Double amortizedAmountPLN;
        public Double amortizationBasePLN;
    }
}
