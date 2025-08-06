package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.entity.poi.PoiPublicToiletInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PoiPublicToiletInfoRepositoryCustom {
    
    /**
     * 시도 코드로 공중 화장실 조회
     */
    List<PoiPublicToiletInfoEntity> findBySidoCode(String sidoCode);
    
    /**
     * 화장실 유형으로 공중 화장실 조회
     */
    List<PoiPublicToiletInfoEntity> findByToiletType(String toiletType);
    
    /**
     * 화장실명으로 공중 화장실 조회 (부분 일치)
     */
    List<PoiPublicToiletInfoEntity> findByToiletNameContaining(String toiletName);
    
    /**
     * 위치 기반 공중 화장실 조회 (위도, 경도 범위)
     */
    List<PoiPublicToiletInfoEntity> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng);
    
    /**
     * 특정 편의시설이 있는 공중 화장실 조회
     */
    List<PoiPublicToiletInfoEntity> findByFacilityType(String facilityType, String ynValue);
    
    /**
     * 장애인 시설이 있는 공중 화장실 조회
     */
    List<PoiPublicToiletInfoEntity> findByDisabilityFacility();
    
    /**
     * 어린이 시설이 있는 공중 화장실 조회
     */
    List<PoiPublicToiletInfoEntity> findByChildFacility();
    
    /**
     * 24시간 개방 공중 화장실 조회
     */
    List<PoiPublicToiletInfoEntity> findBy24HourOpen();
    
    /**
     * 페이지네이션을 포함한 공중 화장실 조회
     */
    Page<PoiPublicToiletInfoEntity> findAllWithPagination(Pageable pageable);
    
    /**
     * 복합 조건으로 공중 화장실 검색
     */
    List<PoiPublicToiletInfoEntity> findByMultipleConditions(String sidoCode, String toiletType, 
                                                            String toiletName, String safetyTargetYn, 
                                                            String emgBellYn, String cctvYn);
    
    /**
     * 카테고리 조건으로 공중 화장실 검색 (페이징)
     */
    List<PoiPublicToiletInfo> findByCategoryConditions(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType, 
            //String disabilityFacilityYn,
            String open24hYn, int offset, int size);
    
    /**
     * 카테고리 조건으로 공중 화장실 개수 조회
     */
    long countByCategoryConditions(String toiletName, String sidoCode, PoiPublicToiletType toiletType,
                                 //String disabilityFacilityYn,
                                   String open24hYn);
    
    /**
     * 위치 기반 공중 화장실 검색 (페이징)
     */
    List<PoiPublicToiletInfo> findByLocationWithPaging(
            java.math.BigDecimal latitude, java.math.BigDecimal longitude, 
            java.math.BigDecimal radius, int offset, int size);
    
    /**
     * 위치 기반 공중 화장실 개수 조회
     */
    long countByLocation(java.math.BigDecimal latitude, java.math.BigDecimal longitude, 
                        java.math.BigDecimal radius);
    
    /**
     * 시도 코드로 공중 화장실 조회 (페이징)
     */
    List<PoiPublicToiletInfo> findBySidoCodeWithPaging(String sidoCode, int offset, int size);
    
    /**
     * 시도 코드로 공중 화장실 개수 조회
     */
    long countBySidoCode(String sidoCode);
    
    /**
     * 전체 공중 화장실 조회 (페이징)
     */
    List<PoiPublicToiletInfo> findAllWithPaging(int offset, int size);
    
    /**
     * 전체 공중 화장실 개수 조회
     */
    long countAll();
} 