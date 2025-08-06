package com.sweetk.iitp.api.constant.poi;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PoiSubwayNodeType {

    STATION(0, "일반노드"),
    TRANSFER(1, "지하철 출입구"),
    TERMINAL(2, "버스 정류장"),
    INTERCHANGE(3, "지하보도 출입구");

    private final Integer code;
    private final String name;

    /**
     * JSON 직렬화 시 사용할 값 (숫자 코드)
     */
    @JsonValue
    public Integer getCode() {
        return this.code;
    }

    /**
     * 한글 명칭 반환
     */
    public String getName() {
        return this.name;
    }

    /**
     * 코드로 enum 찾기
     */
    public static PoiSubwayNodeType fromCode(Integer code) {
        for (PoiSubwayNodeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown node type code: " + code);
    }

    /**
     * 코드가 유효한지 검증
     */
    public static boolean isValidCode(Integer code) {
        for (PoiSubwayNodeType type : values()) {
            if (type.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
} 