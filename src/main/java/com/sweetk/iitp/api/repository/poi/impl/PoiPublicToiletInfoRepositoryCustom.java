package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;

import java.util.List;

public interface PoiPublicToiletInfoRepositoryCustom {
    
    /**
     * ID로 공중 화장실 조회 (DTO 반환, del_yn = 'N' 조건 포함)
     */
    java.util.Optional<PoiPublicToiletInfo> findByIdToDto(Integer toiletId);
    
    /**
     * 시도 코드로 공중 화장실 조회 (DTO 반환)
     */
    List<PoiPublicToiletInfo> findBySidoCodeToDto(String sidoCode);
    
    /**
     * 카테고리 조건으로 공중 화장실 검색 (페이징)
     */
    List<PoiPublicToiletInfo> findByCategoryConditions(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType, 
            String open24hYn, int offset, int size);

    /**
     * 카테고리 조건으로 공중 화장실 검색 (전체 결과)
     */
    List<PoiPublicToiletInfo> findByCategoryConditions(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType, 
            String open24hYn);
    
    /**
     * 카테고리 조건으로 공중 화장실 검색 (페이징 + 총 개수)
     */
    MvPoiPageResult<PoiPublicToiletInfo> findByCategoryConditionsWithPaging(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType,
            String open24hYn, int offset, int size);

    /**
     * 위치 기반 공중 화장실 검색 (기본 - 위치만)
     */
    List<PoiPublicToiletInfo> findByLocation(
            java.math.BigDecimal latitude, java.math.BigDecimal longitude,
            java.math.BigDecimal radius);

    /**
     * 위치 기반 공중 화장실 검색 (추가 조건 포함)
     */
    List<PoiPublicToiletInfo> findByLocationWithConditions(
            java.math.BigDecimal latitude, java.math.BigDecimal longitude,
            java.math.BigDecimal radius, String toiletName, PoiPublicToiletType toiletType, String open24hYn);

    /**
     * 위치 기반 공중 화장실 검색 (페이징 - 통합)
     */
    MvPoiPageResult<PoiPublicToiletInfo> findByLocationWithPaging(
            java.math.BigDecimal latitude, java.math.BigDecimal longitude, 
            java.math.BigDecimal radius, String toiletName, PoiPublicToiletType toiletType, 
            String open24hYn, int offset, int size);
    

    
    /**
     * 시도 코드로 공중 화장실 조회 (페이징)
     */
    MvPoiPageResult<PoiPublicToiletInfo> findBySidoCodeWithPaging(String sidoCode, int offset, int size);
    
    /**
     * 전체 공중 화장실 조회 (페이징)
     */
    MvPoiPageResult<PoiPublicToiletInfo> findAllWithPaging(int offset, int size);
    
    /**
     * 전체 공중 화장실 조회 (전체 결과)
     */
    List<PoiPublicToiletInfo> findAllToilets();

    

} 