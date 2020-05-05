package com.far.server.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Asset {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(unique = true, nullable = false)
    private String assetName;

    @Column(nullable = false)
    private String documentName;

    @Column(nullable = false)
    private AssetCategory assetCategory;

    @Column(nullable = false)
    private Instant purchaseDate;

    @Column(nullable = false)
    private Double purchaseAmountPLN;

    @Column(nullable = false)
    Instant entryDate;

    @Column(nullable = false)
    AmortizationType amortizationType;

    @Column(nullable = true)
    Double digressiveAmortizationCoefficient;

    @Column(nullable = true)
    Instant invalidationDate;

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
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

    public AssetCategory getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(AssetCategory assetCategory) {
        this.assetCategory = assetCategory;
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

    public AmortizationType getAmortizationType() {
        return amortizationType;
    }

    public void setAmortizationType(AmortizationType amortizationType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Asset asset = (Asset) o;
        return Objects.equals(id, asset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Asset{" +
            "id=" + id +
            ", version=" + version +
            ", assetName='" + assetName + '\'' +
            ", documentName='" + documentName + '\'' +
            ", assetCategory=" + assetCategory +
            ", purchaseDate=" + purchaseDate +
            ", purchaseAmountPLN=" + purchaseAmountPLN +
            ", entryDate=" + entryDate +
            ", amortizationType=" + amortizationType +
            ", invalidationDate=" + invalidationDate +
            '}';
    }
}
