package com.sweetk.iitp.api.repository.poi.impl;


import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorLocation;

import java.util.List;
import java.util.Optional;

public interface PoiSubwayElevatorRepositoryCustom {


    /*******************************
     ** 지하철 엘리베이터 ID로 조회
     *******************************/
     // ID로 지하철 엘리베이터 조회 (DTO 반환, del_yn = 'N' 조건 포함)
    Optional<PoiSubwayElevator> findByIdToDto(Integer subwayId);


    /*******************************
     * 전체 지하철 엘리베이터 조회
     *******************************/
    // 전체 지하철 엘리베이터 조회 (전체 결과)
    List<PoiSubwayElevator> findAllToDto();

    // 전체 지하철 엘리베이터 조회 (페이징)
    PoiPageResult<PoiSubwayElevator> findAllWithPagingCount(int offset, int size);




    /*******************************
     ** 시도별 지하철 엘리베이터 조회
     *******************************/
     // 시도 코드로 지하철 엘리베이터 조회 (DTO 반환)
    List<PoiSubwayElevator> findBySidoCode(String sidoCode);

     // 시도 코드로 지하철 엘리베이터 조회 (페이징)
    PoiPageResult<PoiSubwayElevator> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size);



    /*******************************
     ** 시군구별 지하철 엘리베이터 조회
     *******************************/
    // 시군구 조건으로 지하철 엘리베이터 검색 (전체 결과)
    List<PoiSubwayElevator> findBySigungu(String sidoCode, String sigunguCode);

    // 시군구 조건으로 지하철 엘리베이터 검색 (페이징)
    PoiPageResult<PoiSubwayElevator> findBySigunguWithPagingCount(
            String sidoCode, String sigunguCode, int offset, int size);



    /*******************************
     ** 카테고리 기반 지하철 엘리베이터 조회
     *******************************/
     // 카테고리 조건으로 지하철 엘리베이터 검색 (전체 결과)
    List<PoiSubwayElevator> findByCategoryConditions(String stationName, String sidoCode, 
                                                    Integer nodeTypeCode);

     // 카테고리 조건으로 지하철 엘리베이터 검색 (페이징 + 총 개수)
    PoiPageResult<PoiSubwayElevator> findByCategoryConditionsWithPagingCount(String stationName, String sidoCode,
                                                                             Integer nodeTypeCode, int offset, int size);



    /*******************************
     ** 위치 기반 기반 지하철 엘리베이터 조회
     *******************************/
     // 거리 정보 포함 위치 기반 지하철 엘리베이터 검색 (전체 결과)
    List<PoiSubwayElevatorLocation> findByLocationWithConditions(Double latitude, Double longitude,
                                                              Double radius,
                                                              String stationName, Integer nodeTypeCode);

     // 거리 정보 포함 위치 기반 지하철 엘리베이터 검색 (페이징)
    PoiPageResult<PoiSubwayElevatorLocation> findByLocationWithConditionsAndPagingCount(Double latitude, Double longitude,
                                                                                       Double radius,
                                                                                       String stationName, Integer nodeTypeCode,
                                                                                       int offset, int size);
} 