package com.far.server.core.service;

import com.far.server.core.dto.AssetAmortizationDto;
import com.far.server.core.entity.AmortizationType;
import com.far.server.core.entity.Asset;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmortizationService {

    public List<AssetAmortizationDto> calculateAmortization(List<Asset> assets, Instant amortizeForDate) {
        return assets.stream()
            .map(asset -> calculateAmortization(asset, amortizeForDate))
            .collect(Collectors.toList());
    }

    public AssetAmortizationDto calculateAmortization(Asset asset, Instant amortizeForDate) {
        AmortizationType amortizationType = asset.getAmortizationType();

        if (amortizationType == AmortizationType.LINEAR) {
            return calculateLinearAmortization(asset, amortizeForDate);
        } else if (amortizationType == AmortizationType.DIGRESSIVE) {
            return calculateDigressiveAmortization(asset, amortizeForDate);
        } else {
            throw new UnsupportedOperationException("Unsupported amortization type: " + amortizationType);
        }
    }

    private AssetAmortizationDto calculateDigressiveAmortization(Asset asset, Instant amortizeForDate) {
        LocalDateTime startDate = pickStartDate(asset);
        Instant amortizeForDatePicked = pickEndDateForAsset(asset, amortizeForDate);
        LocalDateTime endDate = pickEndDate(amortizeForDatePicked);

        double amortizedAmount = 0;
        boolean linearStarted = false;
        long months = ChronoUnit.MONTHS.between(startDate, endDate);

        for (int i = 0; i < months; i++) {
            double amortizationPerMonthLinear = asset.getPurchaseAmountPLN()
                * asset.getAssetCategory().getAmortizationPercentage()
                * 0.01
                / 12;
            double amortizationPerMonthDigressive = (asset.getPurchaseAmountPLN() - amortizedAmount)
                * asset.getAssetCategory().getAmortizationPercentage()
                * 0.01
                * asset.getDigressiveAmortizationCoefficient()
                / 12;

            if (linearStarted || amortizationPerMonthDigressive <= amortizationPerMonthLinear) {
                linearStarted = true;
                amortizedAmount += amortizationPerMonthLinear;
            } else {
                amortizedAmount += amortizationPerMonthDigressive;
            }
        }

        if (amortizedAmount > asset.getPurchaseAmountPLN()) {
            amortizedAmount = asset.getPurchaseAmountPLN();
        }

        return buildAmortizationDto(asset, amortizeForDatePicked, amortizedAmount);
    }

    private AssetAmortizationDto calculateLinearAmortization(Asset asset, Instant amortizeForDate) {
        LocalDateTime startDate = pickStartDate(asset);
        Instant amortizeForDatePicked = pickEndDateForAsset(asset, amortizeForDate);
        LocalDateTime endDate = pickEndDate(amortizeForDatePicked);

        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        double amortizationPerMonth = asset.getPurchaseAmountPLN()
            * asset.getAssetCategory().getAmortizationPercentage()
            * 0.01
            / 12;
        double amortizedAmount = amortizationPerMonth * months;

        if (amortizedAmount > asset.getPurchaseAmountPLN()) {
            amortizedAmount = asset.getPurchaseAmountPLN();
        }

        return buildAmortizationDto(asset, amortizeForDatePicked, amortizedAmount);
    }

    private LocalDateTime pickStartDate(Asset asset) {
        return LocalDateTime.ofInstant(asset.getEntryDate(), ZoneOffset.UTC.normalized())
            .plusMonths(1)
            .withDayOfMonth(1);
    }

    private LocalDateTime pickEndDate(Instant amortizeForDatePicked) {
        return LocalDateTime.ofInstant(amortizeForDatePicked, ZoneOffset.UTC.normalized())
            .withDayOfMonth(1);
    }

    private Instant pickEndDateForAsset(Asset asset, Instant amortizeForDate) {
        if (asset.getInvalidationDate() == null) {
            return amortizeForDate;
        } else {
            return amortizeForDate.isAfter(asset.getInvalidationDate()) ?
                asset.getInvalidationDate() :
                amortizeForDate;
        }
    }

    private AssetAmortizationDto buildAmortizationDto(Asset asset,
        Instant amortizeForDatePicked,
        double amortizedAmount) {
        return new AssetAmortizationDto(
            asset.getAssetName(),
            asset.getDocumentName(),
            asset.getAssetCategory().getCategoryId().toString(),
            asset.getPurchaseDate(),
            asset.getPurchaseAmountPLN(),
            asset.getEntryDate(),
            asset.getAmortizationType().name(),
            asset.getAssetCategory().getAmortizationPercentage(),
            amortizedAmount,
            amortizeForDatePicked,
            asset.getDigressiveAmortizationCoefficient(),
            asset.getInvalidationDate()
        );
    }
}
