package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Response 에러 정보")
public class ErrorInfo {
    @Schema(description = "에러 코드", example = "C001")
    private final String code;

    @Schema(description = "에러 메시지", example = "유효하지 않은 요청입니다.")
    private final String message;

    @Schema(description = "에러 상세 설명", example = "파라미터 'id'는 필수입니다.")
    private final String details;
}
