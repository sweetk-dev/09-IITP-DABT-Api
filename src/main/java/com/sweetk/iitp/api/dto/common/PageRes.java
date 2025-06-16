package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "페이징 처리된 응답 데이터")
public class PageRes<T> {
    @Schema(description = "현재 페이지의 데이터 목록")
    private List<T> content;

    @Schema(description = "현재 페이지 번호", example = "1")
    private int page;

    @Schema(description = "페이지당 항목 수", example = "10")
    private int size;

    @Schema(description = "전체 항목 수", example = "100")
    private long totalElements;
}