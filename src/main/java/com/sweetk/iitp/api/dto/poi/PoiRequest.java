package com.sweetk.iitp.api.dto.poi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PoiRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Type is required")
    private String type;
    
    @NotNull(message = "Latitude is required")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    private Double longitude;
    
    private String description;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    private String contactInfo;
    private String operatingHours;
    private String website;
} 