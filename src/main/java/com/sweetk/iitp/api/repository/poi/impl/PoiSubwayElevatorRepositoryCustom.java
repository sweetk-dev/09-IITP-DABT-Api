package com.sweetk.iitp.api.repository.poi.impl;


import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;

import java.math.BigDecimal;
import java.util.List;

public interface PoiSubwayElevatorRepositoryCustom {
    
    /**
     * ID로 지하철 엘리베이터 조회 (DTO 반환, del_yn = 'N' 조건 포함)
     */
    java.util.Optional<PoiSubwayElevator> findByIdToDto(Integer subwayId);
    
    /**
     * 시도 코드로 지하철 엘리베이터 조회 (DTO 반환)
     */
    List<PoiSubwayElevator> findBySidoCodeToDto(String sidoCode);
    
    /**
     * 카테고리 조건으로 지하철 엘리베이터 검색 (전체 결과)
     */
    List<PoiSubwayElevator> findByCategoryConditions(String stationName, String sidoCode, 
                                                    Integer nodeTypeCode);

    /**
     * 카테고리 조건으로 지하철 엘리베이터 검색 (페이징 + 총 개수)
     */
    MvPoiPageResult<PoiSubwayElevator> findByCategoryConditionsWithPaging(String stationName, String sidoCode, 
                                                                          Integer nodeTypeCode, int offset, int size);

    /**
     * 시군구 조건으로 지하철 엘리베이터 검색 (전체 결과)
     */
    List<PoiSubwayElevator> findBySigunguConditions(String sidoCode, String sigunguCode);

    /**
     * 시군구 조건으로 지하철 엘리베이터 검색 (페이징)
     */
    MvPoiPageResult<PoiSubwayElevator> findBySigunguConditionsWithPaging(
            String sidoCode, String sigunguCode, int offset, int size);

    /**
     * 위치 기반 지하철 엘리베이터 검색 (전체 결과)
     */
    List<PoiSubwayElevator> findByLocation(BigDecimal latitude, BigDecimal longitude, 
                                           BigDecimal radius);

    /**
     * 위치 기반 지하철 엘리베이터 검색 (페이징)
     */
    MvPoiPageResult<PoiSubwayElevator> findByLocationWithPaging(
            BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size);
    
    /**
     * 시도 코드로 지하철 엘리베이터 조회 (페이징)
     */
    MvPoiPageResult<PoiSubwayElevator> findBySidoCodeWithPaging(
            String sidoCode, int offset, int size);
    
    /**
     * 전체 지하철 엘리베이터 조회 (전체 결과)
     */
    List<PoiSubwayElevator> findAllToDto();
    
    /**
     * 전체 지하철 엘리베이터 조회 (페이징)
     */
    MvPoiPageResult<PoiSubwayElevator> findAllWithPaging(int offset, int size);
} 