package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * MvPoi 위치 기반 검색 결과 DTO (거리 정보 포함)
 */
@Getter
@Setter
@NoArgsConstructor
public class MvPoiLocation extends MvPoi {
    
    @JsonProperty("distance")
    private Integer distance;
    
    // ResultSet에서 직접 생성하는 생성자
    public MvPoiLocation(Long poiId, String languageCode, String title, String summary, String basicInfo,
                        String addressCode, String addressRoad, String addressDetail, Double latitude, 
                        Double longitude, String detailJson, String searchFilterJson, Integer distance) {
        super(poiId, languageCode, title, summary, basicInfo, addressCode, addressRoad, addressDetail, 
              latitude, longitude, detailJson, searchFilterJson);
        this.distance = distance;
    }
    
    // MvPoi 객체로부터 생성하는 생성자
    public MvPoiLocation(MvPoi mvPoi, Integer distance) {
        super(mvPoi.getPoiId(), mvPoi.getLanguageCode(), mvPoi.getTitle(), mvPoi.getSummary(), 
              mvPoi.getBasicInfo(), mvPoi.getAddressCode(), mvPoi.getAddressRoad(), mvPoi.getAddressDetail(),
              mvPoi.getLatitude(), mvPoi.getLongitude(), mvPoi.getDetailJson(), mvPoi.getSearchFilterJson());
        this.distance = distance;
    }
}
