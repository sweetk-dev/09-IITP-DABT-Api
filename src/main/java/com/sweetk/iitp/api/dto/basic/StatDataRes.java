package com.sweetk.iitp.api.dto.basic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "통계 데이터")
public class StatDataRes {
    @Schema(description = "통계 정보" , requiredMode = Schema.RequiredMode.REQUIRED)
    private StatInfo statInfo;

    @Schema(description = "통계 데이터 리스트")
    private List<StatDataItem> items;
}
