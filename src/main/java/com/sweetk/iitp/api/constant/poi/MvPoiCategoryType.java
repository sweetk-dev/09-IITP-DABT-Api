package com.sweetk.iitp.api.constant.poi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum MvPoiCategoryType {
    RESTAURANT("restaurant", "음식점", false, ""),
    TOURIST_SPOT("tourist_spot", "관광지", true, ","),
    SHOPPING("shopping", "쇼핑", false, ""),
    ACCOMMODATION("accommodation", "숙박", false, "");

    private final String code;
    private final String displayName;
    private final boolean isArray;
    private final String delimiter;


    MvPoiCategoryType(String code, String displayName, boolean isArray, String delimiter) {
        this.code = code;
        this.displayName = displayName;
        this.isArray = isArray;
        this.delimiter = delimiter;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isArray() {
        return isArray;
    }

    public String getDelimiter() {
        return delimiter;
    }

    @JsonCreator
    public static MvPoiCategoryType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        for (MvPoiCategoryType type : MvPoiCategoryType.values()) {
            if (type.getCode().equalsIgnoreCase(value)) {
                return type;
            }
        }
        
        String allowedValues = Arrays.stream(MvPoiCategoryType.values())
            .map(type -> String.format("%s(%s)", type.getCode(), type.getDisplayName()))
            .collect(Collectors.joining(", "));
            
        throw new IllegalArgumentException(
            String.format("Invalid category type: %s. Allowed values are: %s", value, allowedValues)
        );
    }
} 