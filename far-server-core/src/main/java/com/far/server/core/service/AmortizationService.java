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

import static com.far.server.core.entity.AmortizationType.DIGRESSIVE;
import static com.far.server.core.entity.AmortizationType.LINEAR;

@Service
public class AmortizationService {

    public List<AssetAmortizationDto> calculateAmortization(List<Asset> assets, Instant amortizeForDate) {
        return assets.stream()
            .map(asset -> calculateAmortization(asset, amortizeForDate))
            .collect(Collectors.toList());
    }

    public AssetAmortizationDto calculateAmortization(Asset asset, Instant amortizeForDate) {
        AmortizationType amortizationType = asset.getAmortizationType();

        if (amortizationType == LINEAR) {
            return calculateLinearAmortization(asset, amortizeForDate);
        } else if (amortizationType == DIGRESSIVE) {
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
        var amortizationMeta = new AssetAmortizationDto.AmortizationMeta();
        long months = ChronoUnit.MONTHS.between(startDate, endDate);

        for (int i = 0; i < months; i++) {
            var entry = new AssetAmortizationDto.AmortizationMonthEntry();
            entry.monthStartDate = startDate.plusMonths(i).toInstant(ZoneOffset.UTC);

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
                double prevAmortizedAmount = amortizedAmount;
                amortizedAmount = calcAmortizedAmount(asset, amortizedAmount, amortizationPerMonthLinear);
                entry.amortizationBasePLN = asset.getPurchaseAmountPLN();
                entry.amortizationType = LINEAR.name();
                entry.amortizedAmountPLN = amortizedAmount - prevAmortizedAmount;
            } else {
                double prevAmortizedAmount = amortizedAmount;
                amortizedAmount = calcAmortizedAmount(asset, amortizedAmount, amortizationPerMonthDigressive);
                entry.amortizationBasePLN = asset.getPurchaseAmountPLN() - amortizedAmount;
                entry.amortizationType = DIGRESSIVE.name();
                entry.amortizedAmountPLN = amortizedAmount - prevAmortizedAmount;
            }

            amortizationMeta.amortizationMonthEntries.add(entry);
        }

        return buildAmortizationDto(
            asset,
            amortizeForDatePicked,
            amortizedAmount,
            amortizationMeta);
    }

    private double calcAmortizedAmount(Asset asset, double amortizedAmount, double amortizationPerMonthDigressive) {
        amortizedAmount += amortizationPerMonthDigressive;
        if (amortizedAmount > asset.getPurchaseAmountPLN()) {
            amortizedAmount = asset.getPurchaseAmountPLN();
        }
        return amortizedAmount;
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

        double amortizedAmount = 0;
        var amortizationMeta = new AssetAmortizationDto.AmortizationMeta();
        for (int i = 0; i < months; i++) {
            double prevAmortizedAmount = amortizedAmount;
            amortizedAmount = calcAmortizedAmount(asset, amortizedAmount, amortizationPerMonth);

            var entry = new AssetAmortizationDto.AmortizationMonthEntry();
            entry.amortizationBasePLN = asset.getPurchaseAmountPLN();
            entry.amortizationType = LINEAR.name();
            entry.amortizedAmountPLN = amortizedAmount - prevAmortizedAmount;
            entry.monthStartDate = startDate.plusMonths(i).toInstant(ZoneOffset.UTC);
            amortizationMeta.amortizationMonthEntries.add(entry);
        }

        return buildAmortizationDto(
            asset,
            amortizeForDatePicked,
            amortizedAmount,
            amortizationMeta);
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
        double amortizedAmount,
        AssetAmortizationDto.AmortizationMeta amortizationMeta) {
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
            asset.getInvalidationDate(),
            amortizationMeta);
    }
}
