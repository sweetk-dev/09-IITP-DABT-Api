package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** 분류 코드가 두 분류에 겹칠 때 칸별 이름 찾기 */
class BasicServiceCatMetaTest {

    private static StatMetaCodeDB meta(String obj, String objNm, String id, String nm) {
        return new StatMetaCodeDB(id, nm, obj, objNm);
    }

    private static StatDataItemDB row(String c1, String c2) {
        return new StatDataItemDB((short) 2024, c1, c2, null, "T001", "%", "1", null, 1);
    }

    @Test
    void stressTable_c1AndC2ResolveToTheirOwnObjects() {
        // 「스트레스 인지 정도」: 특성별(A)과 인지정도별(B)이 모두 0001~ 코드를 쓴다
        List<StatMetaCodeDB> metas = List.of(
                meta("A", "특성별", "0001", "전체"), meta("A", "특성별", "0002", "성별"),
                meta("A", "특성별", "00020001", "남자"),
                meta("B", "인지정도별", "0001", "추정수"), meta("B", "인지정도별", "0002", "대단히 많이 느낀다"),
                meta("B", "인지정도별", "0003", "많이 느끼는 편이다"));
        List<StatDataItemDB> rows = new ArrayList<>();
        for (String c1 : List.of("0001", "00020001")) {
            for (String c2 : List.of("0001", "0002", "0003")) {
                rows.add(row(c1, c2));
            }
        }
        List<Map<String, StatMetaCodeDB>> cols = BasicService.resolveColumnMetaMaps(rows, metas);
        assertEquals("전체", BasicService.findCatMeta(cols, 0, Map.of(), "0001").getItemNm());
        assertEquals("특성별", BasicService.findCatMeta(cols, 0, Map.of(), "0001").getObjNm());
        assertEquals("추정수", BasicService.findCatMeta(cols, 1, Map.of(), "0001").getItemNm());
        assertNull(cols.get(2));
    }

    @Test
    void ambiguousFirstColumn_isResolvedAfterSecondColumn() {
        // 정부부문 장애인고용 현황: c1 코드 {01,02} 는 두 분류 모두에 있고, c2 {01..05} 는 정부부문별(02)에만 있다
        List<StatMetaCodeDB> metas = List.of(
                meta("01", "고용현황별", "01", "공무원"), meta("01", "고용현황별", "02", "근로자"),
                meta("02", "정부부문별", "01", "계"), meta("02", "정부부문별", "02", "중앙행정기관"),
                meta("02", "정부부문별", "03", "헌법기관"), meta("02", "정부부문별", "04", "지방자치단체"),
                meta("02", "정부부문별", "05", "교육청"));
        List<StatDataItemDB> rows = new ArrayList<>();
        for (String c1 : List.of("01", "02")) {
            for (String c2 : List.of("01", "02", "03", "04", "05")) {
                rows.add(row(c1, c2));
            }
        }
        List<Map<String, StatMetaCodeDB>> cols = BasicService.resolveColumnMetaMaps(rows, metas);
        assertEquals("공무원", BasicService.findCatMeta(cols, 0, Map.of(), "01").getItemNm());
        assertEquals("계", BasicService.findCatMeta(cols, 1, Map.of(), "01").getItemNm());
    }

    @Test
    void unresolvedColumn_fallsBackToCodeLookup() {
        StatMetaCodeDB fallback = meta("X", "분류", "01", "기존");
        assertSame(fallback, BasicService.findCatMeta(null, 0, Map.of("01", fallback), "01"));
    }
}
