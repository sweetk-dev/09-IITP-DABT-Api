package com.sweetk.iitp.api.dto.basic.local;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "시군 단위 등록장애인 요약 — 두 원천(경기 읍면동 집계, KOSIS 시군구별)을 기준 시점과 함께 나란히 준다")
public class LocalDisRegSummary {

    @Schema(description = "시군명", example = "안양시")
    private String sigunName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "경기도 읍면동 집계 합산 (시군의 최신 판). 시군에 자료가 없으면 null")
    private Source ggEmd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "KOSIS 시군구별 장애정도별 성별 등록장애인수 (최신 연도). 시군에 자료가 없으면 null")
    private Source kosis;

    @Schema(description = "안내 — 원천·기준 시점이 다르면 값이 다를 수 있다. 구가 있는 시는 구 값을 더한 값이다")
    private List<String> notes = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Schema(description = "원천 1개의 합산 결과")
    public static class Source {

        @Schema(description = "원천", example = "경기도 내 등록장애인 집계현황(읍면동별·유형별·정도별, 경기데이터드림)")
        private String sourceName;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "원천 기준일 (경기 읍면동 집계)", example = "2025-08-07")
        private LocalDate baseDt;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "통계 연도 (KOSIS, 연말 기준)", example = "2025")
        private Integer year;

        @Schema(description = "등록장애인 수", example = "21744")
        private Long totalCnt;

        @Schema(description = "심한 장애", example = "7388")
        private Long sevCnt;

        @Schema(description = "심하지 않은 장애", example = "14356")
        private Long nsevCnt;

        @Schema(description = "남", example = "12800")
        private Long maleCnt;

        @Schema(description = "여", example = "8944")
        private Long femaleCnt;

        @Schema(description = "구별 값 (구가 없는 시군은 빈 목록)")
        private List<Part> byGu = new ArrayList<>();

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @Schema(description = "장애유형별 값 (경기 읍면동 집계만)")
        private List<Part> byType = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Schema(description = "구 또는 장애유형 1개의 값")
    public static class Part {

        @Schema(description = "구명 또는 장애유형", example = "만안구")
        private String name;

        @Schema(description = "등록장애인 수", example = "11206")
        private Long totalCnt;

        @Schema(description = "심한 장애", example = "3701")
        private Long sevCnt;

        @Schema(description = "심하지 않은 장애", example = "7505")
        private Long nsevCnt;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "남", example = "6500")
        private Long maleCnt;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "여", example = "4706")
        private Long femaleCnt;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "읍면동 수 (경기 읍면동 집계 구별 값만)", example = "14")
        private Integer emdCount;
    }
}
