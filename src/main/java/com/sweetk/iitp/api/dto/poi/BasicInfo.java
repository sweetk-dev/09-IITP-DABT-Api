package com.sweetk.iitp.api.dto.poi;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BasicInfo {
    private String name;
    private String description;
    private String address;
    private String contactInfo;
    private String website;
} 