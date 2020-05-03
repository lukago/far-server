package com.far.server.core.api;

import com.far.server.core.dto.AssetDto;
import com.far.server.core.entity.AmortizationType;
import com.far.server.core.entity.Asset;
import com.far.server.core.entity.AssetCategory;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public Asset mapDto(AssetDto assetDto) {
        Asset asset = new Asset();
        asset.setAmortizationType(AmortizationType.valueOf(assetDto.getAmortizationType().toUpperCase()));
        asset.setAssetCategory(AssetCategory.valueOfById(assetDto.getCategoryId()));
        asset.setAssetName(assetDto.getAssetName());
        asset.setDocumentName(assetDto.getDocumentName());
        asset.setPurchaseAmountPLN(assetDto.getPurchaseAmountPLN());
        asset.setPurchaseDate(assetDto.getPurchaseDate());
        asset.setEntryDate(assetDto.getEntryDate());
        asset.setInvalidationDate(assetDto.getInvalidationDate());
        return asset;
    }

    public AssetDto mapEntity(Asset asset) {
        AssetDto assetDto = new AssetDto();
        assetDto.setAmortizationType(asset.getAmortizationType().name());
        assetDto.setCategoryId(asset.getAssetCategory().getCategoryId().toString());
        assetDto.setAssetName(asset.getAssetName());
        assetDto.setDocumentName(asset.getDocumentName());
        assetDto.setPurchaseAmountPLN(asset.getPurchaseAmountPLN());
        assetDto.setPurchaseDate(asset.getPurchaseDate());
        assetDto.setEntryDate(asset.getEntryDate());
        assetDto.setInvalidationDate(asset.getInvalidationDate());
        return assetDto;
    }

}
