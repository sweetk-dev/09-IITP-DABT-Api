package com.sweetk.iitp.api.constant.poi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/** 긴급대응 지원 거점 종류 (poi_emergency_support.support_type) */
public enum PoiEmergencySupportType {
    CHARGE("charge", "전동보장구 급속충전기"),
    REPAIR("repair", "보조기기 수리·지원 센터"),
    CALLTAXI("calltaxi", "교통약자 이동지원센터");

    private final String code;
    private final String displayName;

    PoiEmergencySupportType(String code, String displayName) {
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

    public static String displayNameOf(String code) {
        for (PoiEmergencySupportType t : values()) {
            if (t.code.equals(code)) {
                return t.displayName;
            }
        }
        return null;
    }

    @JsonCreator
    public static PoiEmergencySupportType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (PoiEmergencySupportType t : values()) {
            if (t.code.equalsIgnoreCase(value.trim()) || t.name().equalsIgnoreCase(value.trim())) {
                return t;
            }
        }
        String allowed = Arrays.stream(values()).map(t -> t.code + "(" + t.displayName + ")").collect(Collectors.joining(", "));
        throw new IllegalArgumentException("Invalid support type: " + value + ". Allowed values are: " + allowed);
    }
}
