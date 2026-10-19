package com.sweetk.iitp.api.constant.poi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 장애인 문화·체육 시설 종류 (poi_culture_sports_facility.facility_type)
 */
public enum PoiCultureSportsType {
    BRAILLE_LIB("braille_lib", "점자도서관"),
    BOOKNARAE_LIB("booknarae_lib", "책나래서비스 제공도서관"),
    SPORTS_FAC("sports_fac", "장애인체육시설"),
    SPORTS_ORG("sports_org", "장애인체육회");

    private final String code;
    private final String displayName;

    PoiCultureSportsType(String code, String displayName) {
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

    /** DB 값으로 종류명을 찾는다. 모르는 값이면 null. */
    public static String displayNameOf(String code) {
        for (PoiCultureSportsType type : values()) {
            if (type.code.equals(code)) {
                return type.displayName;
            }
        }
        return null;
    }

    @JsonCreator
    public static PoiCultureSportsType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (PoiCultureSportsType type : values()) {
            if (type.code.equalsIgnoreCase(value.trim()) || type.name().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        String allowed = Arrays.stream(values())
                .map(t -> String.format("%s(%s)", t.code, t.displayName))
                .collect(Collectors.joining(", "));
        throw new IllegalArgumentException(
                String.format("Invalid facility type: %s. Allowed values are: %s", value, allowed));
    }
}
