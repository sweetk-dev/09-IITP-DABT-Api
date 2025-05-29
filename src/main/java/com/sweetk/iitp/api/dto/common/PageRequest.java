package com.sweetk.iitp.api.dto.common;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PageRequest {
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private int page = 0;

    @Min(value = 1, message = "Page size must be greater than or equal to 1")
    private int size = 10;

    private String sortBy;
    private String sortDirection;
} 