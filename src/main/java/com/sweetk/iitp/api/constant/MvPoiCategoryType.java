package com.sweetk.iitp.api.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum MvPoiCategoryType {
    RESTAURANT("restaurant", "음식점"),
    TOURIST_SPOT("tourist_spot", "관광지"),
    SHOPPING("shopping", "쇼핑"),
    ACCOMMODATION("accommodation", "숙박");

    private final String code;
    private final String displayName;

    MvPoiCategoryType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static MvPoiCategoryType fromString(String value) {
        if (value == null) {
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