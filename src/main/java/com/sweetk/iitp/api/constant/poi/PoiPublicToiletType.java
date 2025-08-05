package com.sweetk.iitp.api.constant.poi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PoiPublicToiletType {

    PUBLIC("PUBLIC", "공중화장실"),
    SIMPLE("SIMPLE", "간이화장실"),
    OPEN("OPEN", "개방화장실"),
    MOBILE("MOBILE", "이동화장실");

    private final String code;
    private final String name;

    /**
     * 한글 명칭 반환
     */
    public String getName() {
        return this.name;
    }

    /**
     * 코드로 enum 찾기
     */
    public static PoiPublicToiletType fromCode(String code) {
        for (PoiPublicToiletType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown toilet type code: " + code);
    }

    /**
     * 코드가 유효한지 검증
     */
    public static boolean isValidCode(String code) {
        for (PoiPublicToiletType type : values()) {
            if (type.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
} 