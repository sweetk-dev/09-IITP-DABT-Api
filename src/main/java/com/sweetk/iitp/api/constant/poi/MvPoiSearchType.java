package com.sweetk.iitp.api.constant.poi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MvPoiSearchType {
    @Schema(description = "카테고리 기반 검색 (레스토랑, 유적지, 쇼핑 등)")
    CATEGORY("category"),
    
    @Schema(description = "위치 기반 검색 (위도, 경도 좌표)")
    LOCATION("location");

    private final String type;

    MvPoiSearchType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static MvPoiSearchType fromString(String value) {
        if (value == null) {
            return null;
        }
        
        for (MvPoiSearchType type : MvPoiSearchType.values()) {
            if (type.getType().equalsIgnoreCase(value)) {
                return type;
            }
        }
        
        String allowedValues = Arrays.stream(MvPoiSearchType.values())
            .map(MvPoiSearchType::getType)
            .collect(Collectors.joining(", "));
            
        throw new IllegalArgumentException(
            String.format("Invalid search type: %s. Allowed values are: %s", value, allowedValues)
        );
    }
}
