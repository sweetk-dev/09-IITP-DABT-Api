package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.entity.poi.PoiTourBfFacilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PoiTourBfFacilityRepositoryCustom {
    
    /**
     * 시도 코드로 무장애 관광지 시설 조회
     */
    List<PoiTourBfFacilityEntity> findBySidoCode(String sidoCode);
    
    /**
     * 시설명으로 무장애 관광지 시설 조회 (부분 일치)
     */
    List<PoiTourBfFacilityEntity> findByFcltNameContaining(String fcltName);
    
    /**
     * 위치 기반 무장애 관광지 시설 조회 (위도, 경도 범위)
     */
    List<PoiTourBfFacilityEntity> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng);
    
    /**
     * 특정 편의시설이 있는 무장애 관광지 시설 조회
     */
    List<PoiTourBfFacilityEntity> findByFacilityType(String facilityType, String ynValue);
    
    /**
     * 페이지네이션을 포함한 무장애 관광지 시설 조회
     */
    Page<PoiTourBfFacilityEntity> findAllWithPagination(Pageable pageable);
    
    /**
     * 복합 조건으로 무장애 관광지 시설 검색
     */
    List<PoiTourBfFacilityEntity> findByMultipleConditions(String sidoCode, String fcltName, 
                                                          String toiletYn, String elevatorYn, 
                                                          String parkingYn, String slopeYn);

    /**
     * 카테고리 조건으로 무장애 관광지 시설 검색 (페이징)
     */
    List<PoiTourBfFacility> findByCategoryConditions(String fcltName, String sidoCode, 
                                                     String toiletYn, String elevatorYn, 
                                                     String parkingYn, String wheelchairRentYn, 
                                                     String tactileMapYn, String audioGuideYn, 
                                                     int offset, int size);

    /**
     * 카테고리 조건으로 무장애 관광지 시설 개수 조회
     */
    long countByCategoryConditions(String fcltName, String sidoCode, 
                                  String toiletYn, String elevatorYn, 
                                  String parkingYn, String wheelchairRentYn, 
                                  String tactileMapYn, String audioGuideYn);

    /**
     * 위치 기반 무장애 관광지 시설 검색 (페이징)
     */
    List<PoiTourBfFacility> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                      BigDecimal radius, int offset, int size);

    /**
     * 위치 기반 무장애 관광지 시설 개수 조회
     */
    long countByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius);
} 