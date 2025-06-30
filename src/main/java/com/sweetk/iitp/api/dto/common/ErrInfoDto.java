package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import com.sweetk.iitp.api.exception.ErrorCode;

@Getter
@Builder
@Schema(description = "Response 에러 상세 정보")
public class ErrInfoDto {
    @Schema(description = "에러 코드", example = "C000")
    private final String code;

    @Schema(description = "에러 메시지", example = "요청한 parameter이 올바르지 않습니다.")
    private final String message;

    @Schema(description = "에러 상세 설명", example = "파라미터 'id'는 필수입니다.")
    private final String details;
}
