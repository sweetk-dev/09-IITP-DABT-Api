package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilityLocation;

import java.util.List;
import java.util.Optional;

public interface PoiTourBfFacilityRepositoryCustom {

    /*******************************
     ** 무장애 관광지 시설 ID로 조회
     *******************************/
     // ID로 무장애 관광지 시설 조회 (DTO 반환, del_yn = 'N' 조건 포함)
     Optional<PoiTourBfFacility> findByIdToDto(Integer fcltId);




    /*******************************
     ** 전체 무장애 관광지 시설 조회
     *******************************/
    // 전체 무장애 관광지 시설 조회 (전체 결과)
    List<PoiTourBfFacility> findAllToDto();

    // 전체 무장애 관광지 시설 조회 (페이징)
    PoiPageResult<PoiTourBfFacility> findAllWithPagingCount(int offset, int size);



    /*******************************
     ** 시도별 무장애 관광지 시설 조회
     *******************************/
     // 시도 코드로 무장애 관광지 시설 조회 (DTO 반환)
    List<PoiTourBfFacility> findBySidoCode(String sidoCode);

    // 시도 코드로 무장애 관광지 시설 조회 (페이징)
    PoiPageResult<PoiTourBfFacility> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size);




    /*******************************
     ** 카테고리 기반 무장애 관광지 시설 조회
     *******************************/
     // 카테고리 조건으로 무장애 관광지 시설 검색 (전체 결과)
    List<PoiTourBfFacility> findByCategoryConditions(String fcltName, String sidoCode, 
                                                     String toiletYn, String elevatorYn, 
                                                     String parkingYn, String wheelchairRentYn, 
                                                     String tactileMapYn, String audioGuideYn);


     // 카테고리 조건으로 무장애 관광지 시설 검색 (페이징 + 총 개수)
    PoiPageResult<PoiTourBfFacility> findByCategoryConditionsWithPagingCount(
            String fcltName, String sidoCode, String toiletYn, String elevatorYn, 
            String parkingYn, String wheelchairRentYn, String tactileMapYn, String audioGuideYn, 
            int offset, int size);



    /*******************************
     ** 위치 기반 무장애 관광지 시설 조회
     *******************************/
     // 거리 정보 포함 위치 기반 무장애 관광지 시설 검색 (전체 결과)
    List<PoiTourBfFacilityLocation> findByLocationWithConditions(Double latitude, Double longitude,
                                                               Double radius,
                                                               String fcltName, //String sidoCode,
                                                               String toiletYn, String elevatorYn,
                                                               String parkingYn, String wheelchairRentYn,
                                                               String tactileMapYn, String audioGuideYn);


     // 거리 정보 포함 위치 기반 무장애 관광지 시설 검색 (페이징)
    PoiPageResult<PoiTourBfFacilityLocation> findByLocationWithConditionsAndPagingCount( Double latitude, Double longitude,
                                                                                       Double radius,
                                                                                       String fcltName, //String sidoCode,
                                                                                       String toiletYn, String elevatorYn,
                                                                                       String parkingYn, String wheelchairRentYn,
                                                                                       String tactileMapYn, String audioGuideYn,
                                                                                       int offset, int size);



}