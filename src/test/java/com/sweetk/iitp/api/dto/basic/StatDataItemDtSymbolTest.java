package com.sweetk.iitp.api.dto.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 수치 칸이 원천 기호인 행(dt = NULL, dt_symbol = '-')의 응답 항목.
 * 실제 응답은 공통 조회 SQL(BasicQuerySupport·EmotionStatsRepository) → StatDataItemDB.data → BasicService 의
 * StatDataItem.builder() 로 만든다. 그 SQL 이 기호 행의 수치 칸에 원래 표기를 싣는다(BasicQuerySupportDtColumnTest).
 */
class StatDataItemDtSymbolTest {

    private static StatDataItem.StatDataItemBuilder base() {
        return StatDataItem.builder()
                .prdDe((short) 2023).c1Nm("전체").c1ObjId("A").c1ObjNm("특성별")
                .itmNm("비율").itmObjId("ITEM").unitNm("%");
    }

    @Test
    void symbolRowCarriesSourceSymbol() {
        assertEquals("-", base().data("-").build().getData());
    }

    @Test
    void missingValueNoLongerFailsWholeResponse() {
        // 종전에는 data 가 null 금지라 여기서 NullPointerException → 응답 전체 500 이었다
        StatDataItem item = assertDoesNotThrow(() -> base().data(null).build());
        assertNull(item.getData());
    }
}
