package com.far.server.core.repository;

import com.far.server.core.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByAssetName(String assetName);
}
