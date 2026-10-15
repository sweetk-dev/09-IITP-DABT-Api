package com.sweetk.iitp.api.constant.poi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/** 장애인복지시설 종류 (poi_welfare_facility.facility_type) */
public enum PoiWelfareFacilityType {
    RESIDENCE("residence", "장애유형별 거주시설"),
    SHORT_RESIDENCE("short_residence", "장애인 단기거주시설"),
    PROTECT_WORKSHOP("protect_workshop", "장애인보호작업장"),
    IL_CENTER("il_center", "중증장애인자립생활지원센터"),
    SUPPORT_CENTER("support_center", "장애인 관련 시설(가족지원·인권·발달장애인지원·권익옹호)");

    private final String code;
    private final String displayName;

    PoiWelfareFacilityType(String code, String displayName) {
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
        for (PoiWelfareFacilityType t : values()) {
            if (t.code.equals(code)) {
                return t.displayName;
            }
        }
        return null;
    }

    @JsonCreator
    public static PoiWelfareFacilityType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (PoiWelfareFacilityType t : values()) {
            if (t.code.equalsIgnoreCase(value.trim()) || t.name().equalsIgnoreCase(value.trim())) {
                return t;
            }
        }
        String allowed = Arrays.stream(values()).map(t -> t.code + "(" + t.displayName + ")").collect(Collectors.joining(", "));
        throw new IllegalArgumentException("Invalid facility type: " + value + ". Allowed values are: " + allowed);
    }
}
