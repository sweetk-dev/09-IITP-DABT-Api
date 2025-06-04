package com.sweetk.iitp.api.dto.basic;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Schema(description = "통계 데이터 상세 Item")
public class StatDataItem {
    @Schema(description = "통계 년도", requiredMode = Schema.RequiredMode.REQUIRED,  example = "2021")
    @NonNull
    private Short prdDe;

    @Schema(description = "분류값 1", requiredMode = Schema.RequiredMode.REQUIRED,  example = "전체")
    @NonNull
    private String c1Nm;

    @Schema(description = "분류값 2", example = "추정수")
    private String c2Nm;

    @Schema(description = "분류값 3")
    private String c3Nm;

    @Schema(description = "분류명 1", requiredMode = Schema.RequiredMode.REQUIRED,  example = "특성별")
    @NonNull
    private String c1ObjNm;

    @Schema(description = "분류명 2", example = "도와주는사람별")
    private String c2ObjNm;

    @Schema(description = "분류명 3")
    private String c3ObjNm;

    @Schema(description = "항목명", requiredMode = Schema.RequiredMode.REQUIRED,  example = "일상생활 도와주는 사람(1순위)")
    @NonNull
    private String itmNm;

    @Schema(description = "단위명", requiredMode = Schema.RequiredMode.REQUIRED,  example = "명")
    @NonNull
    private String unitNm;

    @Schema(description = "수치 값", requiredMode = Schema.RequiredMode.REQUIRED,  example = "251277")
    @NonNull
    private String data;

    @Schema(description = "데이터의 최종수정일", example = "2023-01-02")
    private LocalDate lstChnDe;


    public static StatDataItem fromEntity(BaseStatsEntity entity) {
        if (entity == null) return null;

        return StatDataItem.builder()
                .prdDe(entity.getPrdDe())
                .c1Nm(entity.getC1())
                .c2Nm(entity.getC2())
                .c3Nm(entity.getC3())
                .c1ObjNm(entity.getC1ObjNm())
                .c2ObjNm(entity.getC2ObjNm())
                .c3ObjNm(entity.getC3ObjNm())
                .itmNm(entity.getItmNm()) // itmId → itmNm 사용 중이면 실제 이름 매핑 필요
                .unitNm(entity.getUnitNm())
                .data(entity.getDt() != null ? entity.getDt().toPlainString() : null)
                .lstChnDe(entity.getLstChnDe())
                .build();
    }
}
