package com.far.server.core.service;

import com.far.server.core.entity.AmortizationType;
import com.far.server.core.entity.Asset;
import com.far.server.core.entity.AssetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AmortizationServiceTest {

    private AmortizationService objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new AmortizationService();
    }

    @Test
    void amortizationLinearShouldReturnNotNullResult() {
        //given
        Asset asset = sampleAsset(AmortizationType.LINEAR);

        //when
        var result = objectUnderTest.calculateAmortization(List.of(asset), Instant.now());

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void amortizationDigressiveShouldReturnNotNullResult() {
        //given
        Asset asset = sampleAsset(AmortizationType.DIGRESSIVE);
        asset.setDigressiveAmortizationCoefficient(2.0);

        //when
        var result = objectUnderTest.calculateAmortization(List.of(asset), Instant.now());

        //then
        assertThat(result).isNotNull();
    }

    private Asset sampleAsset(AmortizationType amortizationType) {
        Asset asset = new Asset();
        asset.setAmortizationType(amortizationType);
        asset.setAssetCategory(AssetCategory.MOBILE_PHONE);
        asset.setAssetName("name");
        asset.setDocumentName("test doc");
        asset.setPurchaseAmountPLN(10000.0);
        asset.setPurchaseDate(Instant.now().minus(800, ChronoUnit.DAYS));
        asset.setEntryDate(Instant.now().minus(800, ChronoUnit.DAYS));
        asset.setInvalidationDate(null);
        return asset;
    }
}
