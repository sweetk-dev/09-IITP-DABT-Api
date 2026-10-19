package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacility;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilityLocation;

import java.util.List;
import java.util.Optional;

public interface PoiCultureSportsFacilityRepositoryCustom {

    Optional<PoiCultureSportsFacility> findByIdToDto(Integer facilityId);

    /** 조건 검색. 값이 null 인 조건은 쓰지 않는다. size 가 null 이면 전체 결과. */
    PoiPageResult<PoiCultureSportsFacility> findByConditions(
            String facilityType, String name, String sigunNm, String sidoCode,
            Integer offset, Integer size);

    /** 위치 기반 검색(반경 단위: 미터, 가까운 순). size 가 null 이면 전체 결과. */
    PoiPageResult<PoiCultureSportsFacilityLocation> findByLocation(
            Double latitude, Double longitude, Double radiusMeters,
            String facilityType, String name,
            Integer offset, Integer size);
}
