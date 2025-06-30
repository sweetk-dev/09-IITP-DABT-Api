package com.sweetk.iitp.api.dto.common;

import com.sweetk.iitp.api.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@Schema(description = "에러 응답 전용" )
public class ErrApiResDto {
    @Schema(example = "false")
    private final boolean success;

    @Schema(example = "null")
    private final Object data;

    @Schema(example = "BAD_REQUEST")
    private final HttpStatus status;

    @Schema(description = "에러 정보", implementation = ErrInfoDto.class)
    private final ErrInfoDto error;


    public static ErrApiResDto of(ErrorCode errorCode) {
        return ErrApiResDto.builder()
                .success(false)
                .data(null)
                .status(errorCode.getStatus())
                .error(ErrInfoDto.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .details(null)
                        .build())
                .build();
    }

    public static ErrApiResDto of(ErrorCode errorCode, String detailMessage) {
        return ErrApiResDto.builder()
                .success(false)
                .data(null)
                .status(errorCode.getStatus())
                .error(ErrInfoDto.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .details(detailMessage)
                        .build())
                .build();
    }
}
