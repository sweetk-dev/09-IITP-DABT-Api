package com.sweetk.iitp.api.dto.poi;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Location {
    private Double latitude;
    private Double longitude;
} 