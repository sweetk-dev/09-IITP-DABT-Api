package com.sweetk.iitp.api.dto.poi;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PoiResponse {
    private Long id;
    private BasicInfo basicInfo;
    private Location location;
    private String type;
    private String operatingHours;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 