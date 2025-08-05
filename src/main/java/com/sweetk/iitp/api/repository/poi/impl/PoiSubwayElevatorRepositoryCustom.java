package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.entity.poi.PoiSubwayElevatorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PoiSubwayElevatorRepositoryCustom {
    
    /**
     * 시도 코드로 지하철 엘리베이터 조회
     */
    List<PoiSubwayElevatorEntity> findBySidoCode(String sidoCode);
    
    /**
     * 지하철역 코드로 지하철 엘리베이터 조회
     */
    List<PoiSubwayElevatorEntity> findByStationCode(String stationCode);
    
    /**
     * 위치 기반 지하철 엘리베이터 조회 (위도, 경도 범위)
     */
    List<PoiSubwayElevatorEntity> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng);
    
    /**
     * 노드 유형 코드로 지하철 엘리베이터 조회
     */
    List<PoiSubwayElevatorEntity> findByNodeTypeCode(Integer nodeTypeCode);
    
    /**
     * 페이지네이션을 포함한 지하철 엘리베이터 조회
     */
    Page<PoiSubwayElevatorEntity> findAllWithPagination(Pageable pageable);

    /**
     * 카테고리 조건으로 지하철 엘리베이터 검색 (페이징)
     */
    List<PoiSubwayElevator> findByCategoryConditions(String stationName, String sidoCode, 
                                                     Integer nodeTypeCode, 
                                                     int offset, int size);

    /**
     * 카테고리 조건으로 지하철 엘리베이터 개수 조회
     */
    long countByCategoryConditions(String stationName, String sidoCode, 
                                  Integer nodeTypeCode);

    /**
     * 시군구 조건으로 지하철 엘리베이터 검색 (페이징)
     */
    List<PoiSubwayElevator> findBySigunguConditions(String sidoCode, String sigunguCode, 
                                                     int offset, int size);

    /**
     * 시군구 조건으로 지하철 엘리베이터 개수 조회
     */
    long countBySigunguConditions(String sidoCode, String sigunguCode);

    /**
     * 위치 기반 지하철 엘리베이터 검색 (페이징)
     */
    List<PoiSubwayElevator> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                      BigDecimal radius, int offset, int size);

    /**
     * 위치 기반 지하철 엘리베이터 개수 조회
     */
    long countByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius);
    
    /**
     * 시도 코드로 지하철 엘리베이터 조회 (페이징)
     */
    List<PoiSubwayElevator> findBySidoCodeWithPaging(String sidoCode, int offset, int size);
    
    /**
     * 시도 코드로 지하철 엘리베이터 개수 조회
     */
    long countBySidoCode(String sidoCode);
    
    /**
     * 전체 지하철 엘리베이터 조회 (페이징)
     */
    List<PoiSubwayElevator> findAllWithPaging(int offset, int size);
    
    /**
     * 전체 지하철 엘리베이터 개수 조회
     */
    long countAll();
} 