package com.sweetk.iitp.api.dto.poi;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PoiSearchRequest {
    private String name;
    private String type;
    
    @NotNull(message = "Latitude is required for radius search")
    private Double latitude;
    
    @NotNull(message = "Longitude is required for radius search")
    private Double longitude;
    
    @NotNull(message = "Radius is required for radius search")
    private Double radius;
} 