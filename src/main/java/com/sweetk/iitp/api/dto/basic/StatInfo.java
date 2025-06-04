package com.sweetk.iitp.api.dto.basic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "통계 정보 ")
public class StatInfo {
    @Schema(description = "통계표명", requiredMode = Schema.RequiredMode.REQUIRED, example = "장애인현황")
    @NonNull
    private String tblName;

    @Schema(description = "수집 주기", requiredMode = Schema.RequiredMode.REQUIRED, example = "년,2년")
    @NonNull
    private String period;

    @Schema(description = "수록기간", requiredMode = Schema.RequiredMode.REQUIRED, example = "2019~2024")
    @NonNull
    private String collectDate;

    @Schema(description = "작성기관명", requiredMode = Schema.RequiredMode.REQUIRED, example = "보건복지부")
    @NonNull
    private String orgName;

    @Schema(description = "조사명", requiredMode = Schema.RequiredMode.REQUIRED, example = "장애인현황")
    @NonNull
    private String surveyName;

    @Schema(description = "최종 자료갱신일", requiredMode = Schema.RequiredMode.REQUIRED, example = " 2025-04-18")
    @NonNull
    private String latestChnDate;
}
