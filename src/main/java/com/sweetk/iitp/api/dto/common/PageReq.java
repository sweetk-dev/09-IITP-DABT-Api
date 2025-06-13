package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Data
@Schema(description = "페이징 요청 포맷")
public class PageReq {
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    @Schema(description = "페이지 번호, 0 이상.")
    private int page = 0;

    @Min(value = 10, message = "Page size must be greater than or equal to 10")
    @Schema(description = "페이지 사이즈 , 0 이상.")
    private int size;

//    @Schema(description = "정렬 기준 (id, name)등 ", example = "id")
//    private String sortBy;

//    @Pattern(regexp = "^(?i)(ASC|DESC)$", message = "Sort direction must be either 'ASC' or 'DESC'")
//    private String sortDirection;
}