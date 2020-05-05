package com.far.server.core.service;

import com.far.server.core.entity.AmortizationType;
import com.far.server.core.entity.Asset;
import com.far.server.core.repository.AssetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Transactional(isolation = READ_COMMITTED)
    public void addAsset(@Valid Asset asset) {
        validateAsset(asset);
        assetRepository.save(asset);
    }

    @Transactional(isolation = READ_COMMITTED)
    public void updateAsset(@Valid Asset asset) {
        validateAsset(asset);
        Asset oldAsset = getAssetByName(asset.getAssetName());
        assetRepository.save(replaceOldAssetWithNewData(oldAsset, asset));
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset getAssetByName(String assetName) {
        return assetRepository.findByAssetName(assetName)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Asset not found by name: " + assetName));
    }

    private Asset replaceOldAssetWithNewData(Asset oldAsset, Asset newData) {
        oldAsset.setAmortizationType(newData.getAmortizationType());
        oldAsset.setAssetCategory(newData.getAssetCategory());
        oldAsset.setDocumentName(newData.getDocumentName());
        oldAsset.setPurchaseAmountPLN(newData.getPurchaseAmountPLN());
        oldAsset.setPurchaseDate(newData.getPurchaseDate());
        oldAsset.setEntryDate(newData.getEntryDate());
        oldAsset.setInvalidationDate(newData.getInvalidationDate());
        return oldAsset;
    }

    private void validateAsset(Asset asset) {
        if ((asset.getAmortizationType() == AmortizationType.DIGRESSIVE
            && asset.getDigressiveAmortizationCoefficient() == null)
            || asset.getDigressiveAmortizationCoefficient() > 2.0
            || asset.getDigressiveAmortizationCoefficient() <= 0.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Invalid digressiveAmortizationCoefficient. Must be between: (0.0, 2.0>");
        }
    }
}
