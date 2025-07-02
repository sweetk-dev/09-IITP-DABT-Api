package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import com.sweetk.iitp.api.exception.ErrorCode;

@Getter
@Schema(description = "Response 에러 상세 정보")
public class ErrInfoDto {
    @Schema(description = "에러 코드 목록:\nS000: SUCCESS\nC000: 요청한 parameter이 올바르지 않습니다.\nC001: 요청한 조건에 맞는 데이터가 없습니다.\nC002: 입력값이 올바르지 않습니다.\nC003: 지원하지 않는 HTTP 메소드입니다.\nC004: 요청한 리소스를 찾을 수 없습니다.\nC005: 필수 입력값이 누락되었습니다.\nC006: 입력 형식이 올바르지 않습니다.\nC007: 서버 내부 오류가 발생했습니다.\nC008: 잘못된 타입의 값이 입력되었습니다.\nC009: 접근이 거부되었습니다.\nC010: 인증이 필요합니다.\nC011: 요청이 너무 많습니다. 잠시 후 다시 시도해주세요.\nB001: 비즈니스 로직 처리 중 오류가 발생했습니다.\nB002: 이미 존재하는 데이터입니다.\nB003: 잘못된 상태입니다.\nB004: 잘못된 API 버전입니다.\nP001: POI를 찾을 수 없습니다.\nP002: POI 생성에 실패했습니다.\nP003: POI 수정에 실패했습니다.\nP004: POI 삭제에 실패했습니다.", example = "C000")
    private final ErrorCode code;

    @Schema(description = "에러 메시지", example = "요청한 parameter이 올바르지 않습니다.")
    private final String message;

    @Schema(description = "에러 상세 설명", example = "파라미터 'id'는 필수입니다.")
    private final String details;

    public ErrInfoDto(ErrorCode code, String details) {
        this.code = code;
        this.message = code.getMessage();
        this.details = details;
    }

    public static ErrInfoDto of(ErrorCode code, String details) {
        return new ErrInfoDto(code, details);
    }
}
