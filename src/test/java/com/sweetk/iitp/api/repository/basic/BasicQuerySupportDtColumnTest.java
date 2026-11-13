package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifeMaincarerEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 공통 조회 SQL 의 수치 칸 식 — 숫자형 표만 dt_symbol 을 함께 읽는다(문자형 표에는 그 칸이 없다). */
class BasicQuerySupportDtColumnTest {

    @Test
    void numericTableFallsBackToSourceSymbol() {
        assertEquals("COALESCE(CAST(dt AS text), dt_symbol) AS dt",
                BasicQuerySupport.dtColumn(StatsDisLifeMaincarerEntity.class));
    }

    @Test
    void textDtTableReadsDtAsIs() {
        assertEquals("dt", BasicQuerySupport.dtColumn(StatsDisHlthDiseaseCostSubEntity.class));
    }
}
