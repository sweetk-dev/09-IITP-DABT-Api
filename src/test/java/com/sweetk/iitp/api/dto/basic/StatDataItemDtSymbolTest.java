package com.sweetk.iitp.api.dto.basic;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifeMaincarerEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 수치 칸이 원천 기호인 행(dt = NULL, dt_symbol = '-')의 응답 값.
 * 01 v1.19.0 부터 숫자형 통계 표는 원천 수치가 숫자가 아니면 dt 를 비우고 원래 표기를 dt_symbol 에 둔다.
 */
class StatDataItemDtSymbolTest {

    private static StatsDisLifeMaincarerEntity row(BigDecimal dt, String symbol) {
        StatsDisLifeMaincarerEntity e = new StatsDisLifeMaincarerEntity();
        e.setPrdDe((short) 2023);
        e.setC1("A01");
        e.setItmId("T001");
        e.setUnitNm("%");
        e.setDt(dt);
        e.setDtSymbol(symbol);
        return e;
    }

    @Test
    void numericRowKeepsPlainNumber() {
        assertEquals("616.000", StatDataItem.fromEntity(row(new BigDecimal("616.000"), null)).getData());
    }

    @Test
    void symbolRowCarriesSourceSymbolInsteadOfFailing() {
        // 종전에는 data 가 null 금지라 여기서 NullPointerException → 응답 500 이었다
        assertEquals("-", StatDataItem.fromEntity(row(null, "-")).getData());
    }
}
