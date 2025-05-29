package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "에러 응답" 전용)
public class ErrApiResDto {
    @Schema(example = "false")
    private final boolean success;



    @Schema(description = "HTTP 상태")
    private final int status;

    @Schema(description = "에러 정보", implementation = ErrorInfo.class)
    private final ErrorInfo error;
}
