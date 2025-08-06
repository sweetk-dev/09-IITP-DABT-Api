package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PoiTourBfFacilityRepositoryCustom {
    
    /**
     * ID로 무장애 관광지 시설 조회 (DTO 반환, del_yn = 'N' 조건 포함)
     */
     Optional<PoiTourBfFacility> findByIdToDto(Integer fcltId);
    
    /**
     * 시도 코드로 무장애 관광지 시설 조회 (DTO 반환)
     */
    List<PoiTourBfFacility> findBySidoCodeToDto(String sidoCode);

    /**
     * 카테고리 조건으로 무장애 관광지 시설 검색 (페이징)
     */
    List<PoiTourBfFacility> findByCategoryConditions(String fcltName, String sidoCode, 
                                                     String toiletYn, String elevatorYn, 
                                                     String parkingYn, String wheelchairRentYn, 
                                                     String tactileMapYn, String audioGuideYn, 
                                                     Integer offset, Integer size);

    /**
     * 카테고리 조건으로 무장애 관광지 시설 검색 (페이징 + 총 개수)
     */
    com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findByCategoryConditionsWithCount(
            String fcltName, String sidoCode, String toiletYn, String elevatorYn, 
            String parkingYn, String wheelchairRentYn, String tactileMapYn, String audioGuideYn, 
            int offset, int size);

    /**
     * 위치 기반 무장애 관광지 시설 검색 (페이징)
     */
    com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findByLocationWithPaging(
            BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size);

    /**
     * 위치 기반 무장애 관광지 시설 검색 (전체 결과)
     */
    List<PoiTourBfFacility> findByLocation(BigDecimal latitude, BigDecimal longitude, 
                                           BigDecimal radius);

    /**
     * 시도 코드로 무장애 관광지 시설 조회 (페이징)
     */
    com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findBySidoCodeWithPaging(
            String sidoCode, int offset, int size);

    /**
     * 전체 무장애 관광지 시설 조회 (전체 결과)
     */
    List<PoiTourBfFacility> findAllToDto();

    /**
     * 전체 무장애 관광지 시설 조회 (페이징)
     */
    com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findAllWithPaging(int offset, int size);


} 