package com.far.server.core.dto;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class AssetDto {

    @NotNull
    String assetName;

    @NotNull
    String documentName;

    @NotNull
    String categoryId;

    @NotNull
    Instant purchaseDate;

    @NotNull
    Double purchaseAmountPLN;

    @NotNull
    Instant entryDate;

    @NotNull
    String amortizationType;

    Double digressiveAmortizationCoefficient;

    Instant invalidationDate;

    public AssetDto() {}

    public AssetDto(String assetName,
        String documentName,
        String categoryId,
        Instant purchaseDate,
        Double purchaseAmountPLN,
        Instant entryDate,
        String amortizationType,
        Double digressiveAmortizationCoefficient,
        Instant invalidationDate) {
        this.assetName = assetName;
        this.documentName = documentName;
        this.categoryId = categoryId;
        this.purchaseDate = purchaseDate;
        this.purchaseAmountPLN = purchaseAmountPLN;
        this.entryDate = entryDate;
        this.amortizationType = amortizationType;
        this.digressiveAmortizationCoefficient = digressiveAmortizationCoefficient;
        this.invalidationDate = invalidationDate;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getPurchaseAmountPLN() {
        return purchaseAmountPLN;
    }

    public void setPurchaseAmountPLN(Double purchaseAmountPLN) {
        this.purchaseAmountPLN = purchaseAmountPLN;
    }

    public Instant getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Instant entryDate) {
        this.entryDate = entryDate;
    }

    public String getAmortizationType() {
        return amortizationType;
    }

    public void setAmortizationType(String amortizationType) {
        this.amortizationType = amortizationType;
    }

    public Double getDigressiveAmortizationCoefficient() {
        return digressiveAmortizationCoefficient;
    }

    public void setDigressiveAmortizationCoefficient(Double digressiveAmortizationCoefficient) {
        this.digressiveAmortizationCoefficient = digressiveAmortizationCoefficient;
    }

    public Instant getInvalidationDate() {
        return invalidationDate;
    }

    public void setInvalidationDate(Instant invalidationDate) {
        this.invalidationDate = invalidationDate;
    }
}
