package com.sweetk.iitp.api.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Data
@Schema(description = "페이징 요청 포맷")
public class PageReq {
    @NotNull
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    @Schema(description = "페이지 번호, 0 이상.", required = true)
    private int page = 0;

    @NotNull
    @Min(value = 20, message = "Page size must be greater than or equal to 10")
    @Schema(description = "페이지 사이즈 , 0 이상.", required = true)
    private int size;

//    @Schema(description = "정렬 기준 (id, name)등 ", example = "id")
//    private String sortBy;

//    @Pattern(regexp = "^(?i)(ASC|DESC)$", message = "Sort direction must be either 'ASC' or 'DESC'")
//    private String sortDirection;

    public Pageable toPageable() {
        return PageRequest.of(this.page, this.size);
    }
}