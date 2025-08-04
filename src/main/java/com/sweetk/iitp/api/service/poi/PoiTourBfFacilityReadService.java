package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchLocReq;
import com.sweetk.iitp.api.dto.poi.converter.PoiTourBfFacilityConverter;
import com.sweetk.iitp.api.repository.poi.PoiTourBfFacilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiTourBfFacilityReadService {

    private final PoiTourBfFacilityRepository poiTourBfFacilityRepository;

    /**
     * 무장애 관광지 시설 ID로 조회
     */
    public Optional<PoiTourBfFacility> findById(Integer fcltId) {
        log.debug("무장애 관광지 시설 조회 요청 - ID: {}", fcltId);
        return poiTourBfFacilityRepository.findById(fcltId)
                .map(PoiTourBfFacilityConverter::toDto);
    }

    /**
     * 시도별 무장애 관광지 시설 조회 (페이징)
     */
    public PageRes<PoiTourBfFacility> getTourBfFacilitiesBySido(String sidoCode, PageReq pageReq) {
        log.debug("시도별 무장애 관광지 시설 조회 요청 - 시도 코드: {}, 페이지: {}", sidoCode, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findByCategoryConditions(
                null, // fcltName
                sidoCode, // sidoCode
                null, // toiletYn
                null, // elevatorYn
                null, // parkingYn
                null, // wheelchairRentYn
                null, // tactileMapYn
                null, // audioGuideYn
                offset,
                size
        );
        
        long totalCount = poiTourBfFacilityRepository.countByCategoryConditions(
                null, // fcltName
                sidoCode, // sidoCode
                null, // toiletYn
                null, // elevatorYn
                null, // parkingYn
                null, // wheelchairRentYn
                null, // tactileMapYn
                null  // audioGuideYn
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 카테고리 기반 무장애 관광지 시설 검색
     */
    public PageRes<PoiTourBfFacility> getTourBfFacilitiesByCategory(PoiTourBfFacilitySearchCatReq searchReq, PageReq pageReq) {
        log.debug("무장애 관광지 시설 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 검색 조건 확인
        boolean hasFcltName = searchReq != null && searchReq.getFcltName() != null && !searchReq.getFcltName().trim().isEmpty();
        boolean hasSidoCode = searchReq != null && searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty();
        boolean hasToiletYn = searchReq != null && searchReq.getToiletYn() != null && !searchReq.getToiletYn().trim().isEmpty();
        boolean hasElevatorYn = searchReq != null && searchReq.getElevatorYn() != null && !searchReq.getElevatorYn().trim().isEmpty();
        boolean hasParkingYn = searchReq != null && searchReq.getParkingYn() != null && !searchReq.getParkingYn().trim().isEmpty();
        boolean hasWheelchairRentYn = searchReq != null && searchReq.getWheelchairRentYn() != null && !searchReq.getWheelchairRentYn().trim().isEmpty();
        boolean hasTactileMapYn = searchReq != null && searchReq.getTactileMapYn() != null && !searchReq.getTactileMapYn().trim().isEmpty();
        boolean hasAudioGuideYn = searchReq != null && searchReq.getAudioGuideYn() != null && !searchReq.getAudioGuideYn().trim().isEmpty();
        
        // 검색 조건이 없는 경우 전체 조회
        if (!hasFcltName && !hasSidoCode && !hasToiletYn && !hasElevatorYn && !hasParkingYn && 
            !hasWheelchairRentYn && !hasTactileMapYn && !hasAudioGuideYn) {
            List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findAllWithPagination(pageReq.toPageable())
                    .map(PoiTourBfFacilityConverter::toDto)
                    .getContent();
            long totalCount = poiTourBfFacilityRepository.count();
            return new PageRes<>(results, pageReq.toPageable(), totalCount);
        }
        
        // 복합 조건 검색
        List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findByCategoryConditions(
                searchReq != null ? searchReq.getFcltName() : null,
                searchReq != null ? searchReq.getSidoCode() : null,
                searchReq != null ? searchReq.getToiletYn() : null,
                searchReq != null ? searchReq.getElevatorYn() : null,
                searchReq != null ? searchReq.getParkingYn() : null,
                searchReq != null ? searchReq.getWheelchairRentYn() : null,
                searchReq != null ? searchReq.getTactileMapYn() : null,
                searchReq != null ? searchReq.getAudioGuideYn() : null,
                offset,
                size
        );
        
        long totalCount = poiTourBfFacilityRepository.countByCategoryConditions(
                searchReq != null ? searchReq.getFcltName() : null,
                searchReq != null ? searchReq.getSidoCode() : null,
                searchReq != null ? searchReq.getToiletYn() : null,
                searchReq != null ? searchReq.getElevatorYn() : null,
                searchReq != null ? searchReq.getParkingYn() : null,
                searchReq != null ? searchReq.getWheelchairRentYn() : null,
                searchReq != null ? searchReq.getTactileMapYn() : null,
                searchReq != null ? searchReq.getAudioGuideYn() : null
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 위치 기반 무장애 관광지 시설 검색
     */
    public PageRes<PoiTourBfFacility> getTourBfFacilitiesByLocation(PoiTourBfFacilitySearchLocReq searchReq, PageReq pageReq) {
        log.debug("무장애 관광지 시설 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}", 
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findByLocationWithPaging(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                offset,
                size
        );
        
        long totalCount = poiTourBfFacilityRepository.countByLocation(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius()
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 시도 코드로 무장애 관광지 시설 조회 (기존 메서드)
     */
    public List<PoiTourBfFacility> findBySidoCode(String sidoCode) {
        log.debug("시도 코드로 무장애 관광지 시설 조회 요청 - 시도 코드: {}", sidoCode);
        return poiTourBfFacilityRepository.findBySidoCode(sidoCode)
                .stream()
                .map(PoiTourBfFacilityConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 시설명으로 무장애 관광지 시설 조회 (부분 일치) (기존 메서드)
     */
    public List<PoiTourBfFacility> findByFcltNameContaining(String fcltName) {
        log.debug("시설명으로 무장애 관광지 시설 조회 요청 - 시설명: {}", fcltName);
        return poiTourBfFacilityRepository.findByFcltNameContaining(fcltName)
                .stream()
                .map(PoiTourBfFacilityConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 위치 기반 무장애 관광지 시설 조회 (기존 메서드)
     */
    public List<PoiTourBfFacility> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        log.debug("위치 기반 무장애 관광지 시설 조회 요청 - 위도: {}~{}, 경도: {}~{}", minLat, maxLat, minLng, maxLng);
        return poiTourBfFacilityRepository.findByLocationRange(minLat, maxLat, minLng, maxLng)
                .stream()
                .map(PoiTourBfFacilityConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 편의시설이 있는 무장애 관광지 시설 조회 (기존 메서드)
     */
    public List<PoiTourBfFacility> findByFacilityType(String facilityType, String ynValue) {
        log.debug("편의시설별 무장애 관광지 시설 조회 요청 - 편의시설: {}, 값: {}", facilityType, ynValue);
        return poiTourBfFacilityRepository.findByFacilityType(facilityType, ynValue)
                .stream()
                .map(PoiTourBfFacilityConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 복합 조건으로 무장애 관광지 시설 검색 (기존 메서드)
     */
    public List<PoiTourBfFacility> findByMultipleConditions(String sidoCode, String fcltName, 
                                                           String toiletYn, String elevatorYn, 
                                                           String parkingYn, String slopeYn) {
        log.debug("복합 조건으로 무장애 관광지 시설 검색 요청 - 시도: {}, 시설명: {}, 화장실: {}, 엘리베이터: {}, 주차장: {}, 경사로: {}", 
                sidoCode, fcltName, toiletYn, elevatorYn, parkingYn, slopeYn);
        return poiTourBfFacilityRepository.findByMultipleConditions(sidoCode, fcltName, toiletYn, elevatorYn, parkingYn, slopeYn)
                .stream()
                .map(PoiTourBfFacilityConverter::toDto)
                .collect(Collectors.toList());
    }

    /*
     * 검색 조건으로 무장애 관광지 시설 조회 (기존 메서드 - 사용하지 않음)
     * 새로운 카테고리 검색과 위치 검색으로 대체됨
     */
    /*
    public Page<PoiTourBfFacility> search(PoiTourBfFacilitySearchReq searchReq) {
        log.debug("무장애 관광지 시설 검색 요청 - {}", searchReq);
        
        // 기본값 설정
        int page = searchReq.page() != null ? searchReq.page() : 0;
        int size = searchReq.size() != null ? searchReq.size() : 20;
        Pageable pageable = PageRequest.of(page, size);
        
        // 복합 조건 검색이 있는 경우
        if (searchReq.sidoCode() != null || searchReq.fcltName() != null || 
            searchReq.toiletYn() != null || searchReq.elevatorYn() != null || 
            searchReq.parkingYn() != null || searchReq.slopeYn() != null) {
            
            List<PoiTourBfFacility> results = findByMultipleConditions(
                searchReq.sidoCode(), searchReq.fcltName(), 
                searchReq.toiletYn(), searchReq.elevatorYn(), 
                searchReq.parkingYn(), searchReq.slopeYn()
            );
            return new org.springframework.data.domain.PageImpl<>(results, pageable, results.size());
        }
        
        // 위치 기반 검색
        if (searchReq.minLatitude() != null && searchReq.maxLatitude() != null && 
            searchReq.minLongitude() != null && searchReq.maxLongitude() != null) {
            List<PoiTourBfFacility> results = findByLocationRange(
                searchReq.minLatitude(), searchReq.maxLatitude(), 
                searchReq.minLongitude(), searchReq.maxLongitude()
            );
            return new org.springframework.data.domain.PageImpl<>(results, pageable, results.size());
        }
        
        // 기본 전체 조회
        return poiTourBfFacilityRepository.findAllWithPagination(pageable)
                .map(PoiTourBfFacilityConverter::toDto);
    }
    */
} 