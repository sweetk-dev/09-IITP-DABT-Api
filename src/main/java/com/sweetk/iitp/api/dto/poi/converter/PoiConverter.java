package com.sweetk.iitp.api.dto.poi.converter;

import com.sweetk.iitp.api.dto.poi.BasicInfo;
import com.sweetk.iitp.api.dto.poi.Location;
import com.sweetk.iitp.api.dto.poi.PoiRequest;
import com.sweetk.iitp.api.dto.poi.PoiResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PoiConverter {
    
    public static PoiResponse toResponse(Poi poi) {
        BasicInfo basicInfo = BasicInfo.builder()
                .name(poi.getName())
                .description(poi.getDescription())
                .address(poi.getAddress())
                .contactInfo(poi.getContactInfo())
                .website(poi.getWebsite())
                .build();

        Location location = Location.builder()
                .latitude(poi.getLatitude())
                .longitude(poi.getLongitude())
                .build();

        return PoiResponse.builder()
                .id(poi.getId())
                .basicInfo(basicInfo)
                .location(location)
                .type(poi.getType())
                .operatingHours(poi.getOperatingHours())
                .active(poi.isActive())
                .createdAt(poi.getCreatedAt())
                .updatedAt(poi.getUpdatedAt())
                .build();
    }

    public static Poi toEntity(PoiRequest request) {
        Poi poi = new Poi();
        poi.setName(request.getName());
        poi.setType(request.getType());
        poi.setLatitude(request.getLatitude());
        poi.setLongitude(request.getLongitude());
        poi.setDescription(request.getDescription());
        poi.setAddress(request.getAddress());
        poi.setContactInfo(request.getContactInfo());
        poi.setOperatingHours(request.getOperatingHours());
        poi.setWebsite(request.getWebsite());
        return poi;
    }
} 