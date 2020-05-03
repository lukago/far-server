package com.far.server.core.api;

import com.far.server.core.dto.AssetAmortizationDto;
import com.far.server.core.dto.AssetDto;
import com.far.server.core.entity.Asset;
import com.far.server.core.service.AmortizationService;
import com.far.server.core.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AssetApi {

    private final AssetService assetService;
    private final AmortizationService amortizationService;
    private final DtoMapper dtoMapper;

    public AssetApi(AssetService assetService,
        AmortizationService amortizationService,
        DtoMapper dtoMapper) {
        this.assetService = assetService;
        this.amortizationService = amortizationService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/assets/amortize")
    public ResponseEntity<List<AssetAmortizationDto>> getAllAmortizedAssetsForDate(@RequestParam String date) {
        List<Asset> assets = assetService.getAllAssets();
        List<AssetAmortizationDto> assetAmortizationDtoList = amortizationService
            .calculateAmortization(assets, Instant.parse(date));
        return ResponseEntity.ok(assetAmortizationDtoList);
    }

    @GetMapping("/assets")
    public ResponseEntity<List<AssetDto>> getAllAssets() {
        List<AssetDto> dtoList = assetService.getAllAssets().stream()
            .map(dtoMapper::mapEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/assets")
    public ResponseEntity<Void> addAsset(@RequestBody @Valid AssetDto assetDto) {
        assetService.addAsset(dtoMapper.mapDto(assetDto));
        return ResponseEntity.ok().build();
    }
}
