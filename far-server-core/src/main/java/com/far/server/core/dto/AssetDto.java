package com.far.server.core.dto;

import java.time.Instant;

public class AssetDto {

    String assetId;
    String serialNumber;
    String categoryId;
    Integer quantity;
    Instant purchaseDate;

    public AssetDto() {}

    public AssetDto(String assetId,
        String serialNumber,
        String categoryId,
        Integer quantity,
        Instant purchaseDate) {
        this.assetId = assetId;
        this.serialNumber = serialNumber;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
