package org.example.portfolioanalysisapi.repository;

import org.example.portfolioanalysisapi.model.Asset;
import org.example.portfolioanalysisapi.model.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository <Asset, Long> {
    Optional<Asset> findByTickerAndAssetType(String ticker, AssetType assetType);
}
