package com.sweetk.iitp.api.dto.common;

import com.sweetk.iitp.api.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
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

    public ErrApiResDto(boolean success, Object data, HttpStatus status, ErrInfoDto error) {
        this.success = success;
        this.data = data;
        this.status = status;
        this.error = error;
    }

    public static ErrApiResDto of(ErrorCode errorCode) {
        return new ErrApiResDto(false, null, errorCode.getStatus(), ErrInfoDto.of(errorCode, null));
    }

    public static ErrApiResDto of(ErrorCode errorCode, String detailMessage) {
        return new ErrApiResDto(false, null, errorCode.getStatus(), ErrInfoDto.of(errorCode, detailMessage));
    }
}
