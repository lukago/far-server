package com.far.server.core.api;

import com.far.server.core.dto.AssetDto;
import com.far.server.core.entity.Asset;
import com.far.server.core.service.AssetService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AssetApi {

    private final ModelMapper mapper;
    private final AssetService assetService;

    public AssetApi(ModelMapper mapper, AssetService assetService) {
        this.mapper = mapper;
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public ResponseEntity<List<AssetDto>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        List<AssetDto> dtoList = mapper.map(assets, new TypeToken<List<AssetDto>>() {}.getType());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/assets")
    public ResponseEntity<Void> addAsset(@RequestBody @Valid AssetDto assetDto) {
        assetService.addAsset(mapper.map(assetDto, Asset.class));
        return ResponseEntity.ok().build();
    }
}
