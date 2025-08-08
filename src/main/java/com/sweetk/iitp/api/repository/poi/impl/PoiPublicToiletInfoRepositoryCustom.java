package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiPublicToilet;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletLocation;

import java.util.List;
import java.util.Optional;

public interface PoiPublicToiletInfoRepositoryCustom {

    /*******************************
     ** ID로 공중 화장실 조회 (DTO 반환, del_yn = 'N' 조건 포함)
     *******************************/
    Optional<PoiPublicToilet> findByIdToDto(Integer toiletId);


    /*******************************
     ** 전체 공중 화장실 조회
     *******************************/
    //전체 공중 화장실 조회 (전체 결과)
    List<PoiPublicToilet> findAllToilets();

     // 전체 공중 화장실 조회 (페이징)
    PoiPageResult<PoiPublicToilet> findAllWithPagingCount(int offset, int size);



    /*******************************
     ** 시도별 공중 화장실 조회
     *******************************/
    //시도 코드로 공중 화장실 조회 (DTO 반환)
    List<PoiPublicToilet> findBySidoCodeToDto(String sidoCode);

    //시도 코드로 공중 화장실 조회 (페이징)
    PoiPageResult<PoiPublicToilet> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size);


    /*******************************
     **  카테고리 기반 검색 공중 화장실 조회
     *******************************/
    //카테고리 조건으로 공중 화장실 검색 (전체 결과)
    List<PoiPublicToilet> findByCategoryConditions(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType,
            String open24hYn);


    //카테고리 조건으로 공중 화장실 검색 (페이징 + 총 개수)
    PoiPageResult<PoiPublicToilet> findByCategoryConditionsWithPagingCount(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType,
            String open24hYn, int offset, int size);

    
    /*******************************
     **  거리 정보 포함 위치 기반 검색 공중 화장실 조회
     *******************************/
    //거리 정보 포함 위치 기반 공중 화장실 검색 (추가 조건 포함)
    List<PoiPublicToiletLocation> findByLocationWithDistanceAndConditions(
            java.math.BigDecimal latitude, java.math.BigDecimal longitude,
            java.math.BigDecimal radius, String toiletName, PoiPublicToiletType toiletType, String open24hYn);

    //거리 정보 포함 위치 기반 공중 화장실 검색 (페이징)
    PoiPageResult<PoiPublicToiletLocation> findByLocationWithDistanceAndPagingCount(
            java.math.BigDecimal latitude, java.math.BigDecimal longitude, 
            java.math.BigDecimal radius, String toiletName, PoiPublicToiletType toiletType, 
            String open24hYn, int offset, int size);

} 