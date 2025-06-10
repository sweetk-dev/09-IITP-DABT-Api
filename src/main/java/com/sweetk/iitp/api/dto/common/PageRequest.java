package com.sweetk.iitp.api.dto.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PageRequest {
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private int page = 0;

    @Min(value = 10, message = "Page size must be greater than or equal to 1")
    private int size = 30;


    private String sortBy;
    
    @Pattern(regexp = "^(?i)(ASC|DESC)$", message = "Sort direction must be either 'ASC' or 'DESC'")
    private String sortDirection;
} 