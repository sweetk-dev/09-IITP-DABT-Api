package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchLocReq;
import com.sweetk.iitp.api.dto.poi.converter.PoiTourBfFacilityConverter;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiTourBfFacilityRepository;
import com.sweetk.iitp.api.service.sys.SysCommonCodeService;
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
    private final SysCommonCodeService commonCodeService;

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
        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 무장애 관광지 시설 조회 요청 - 시도 코드: {}, 페이지: {}", sidoCode, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 시도별 조회 전용 함수 사용 (성능 최적화)
        List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findBySidoCodeWithPaging(
                sidoCode,
                offset,
                size
        );
        
        long totalCount = poiTourBfFacilityRepository.countBySidoCode(sidoCode);
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 카테고리 기반 무장애 관광지 시설 검색 (통합 처리)
     */
    public PageRes<PoiTourBfFacility> getTourBfFacilitiesByCategory(PoiTourBfFacilitySearchCatReq searchReq, PageReq pageReq) {
        log.debug("무장애 관광지 시설 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);
        
        // 검색 조건이 없는 경우 전체 조회 (성능 최적화)
        if (searchReq == null || !hasSearchConditions(searchReq)) {
            return getAllTourBfFacilities(pageReq);
        }
        
        // 검색 조건이 있는 경우 조건 검색
        return searchTourBfFacilitiesByConditions(searchReq, pageReq);
    }

    /**
     * 검색 조건 존재 여부 확인
     */
    private boolean hasSearchConditions(PoiTourBfFacilitySearchCatReq searchReq) {
        return (searchReq.getFcltName() != null && !searchReq.getFcltName().trim().isEmpty()) ||
               (searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty()) ||
               (searchReq.getToiletYn() != null && !searchReq.getToiletYn().trim().isEmpty()) ||
               (searchReq.getElevatorYn() != null && !searchReq.getElevatorYn().trim().isEmpty()) ||
               (searchReq.getParkingYn() != null && !searchReq.getParkingYn().trim().isEmpty()) ||
               (searchReq.getWheelchairRentYn() != null && !searchReq.getWheelchairRentYn().trim().isEmpty()) ||
               (searchReq.getTactileMapYn() != null && !searchReq.getTactileMapYn().trim().isEmpty()) ||
               (searchReq.getAudioGuideYn() != null && !searchReq.getAudioGuideYn().trim().isEmpty());
    }

    /**
     * 전체 무장애 관광지 시설 조회 (검색 조건 없음)
     */
    private PageRes<PoiTourBfFacility> getAllTourBfFacilities(PageReq pageReq) {
        log.debug("전체 무장애 관광지 시설 조회");
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 전체 조회 전용 함수 사용 (성능 최적화)
        List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findAllWithPaging(offset, size);
        long totalCount = poiTourBfFacilityRepository.countAll();
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 조건 검색으로 무장애 관광지 시설 조회
     */
    private PageRes<PoiTourBfFacility> searchTourBfFacilitiesByConditions(PoiTourBfFacilitySearchCatReq searchReq, PageReq pageReq) {
        log.debug("조건 검색으로 무장애 관광지 시설 조회");
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiTourBfFacility> results = poiTourBfFacilityRepository.findByCategoryConditions(
                searchReq.getFcltName(),
                searchReq.getSidoCode(),
                searchReq.getToiletYn(),
                searchReq.getElevatorYn(),
                searchReq.getParkingYn(),
                searchReq.getWheelchairRentYn(),
                searchReq.getTactileMapYn(),
                searchReq.getAudioGuideYn(),
                offset,
                size
        );
        
        long totalCount = poiTourBfFacilityRepository.countByCategoryConditions(
                searchReq.getFcltName(),
                searchReq.getSidoCode(),
                searchReq.getToiletYn(),
                searchReq.getElevatorYn(),
                searchReq.getParkingYn(),
                searchReq.getWheelchairRentYn(),
                searchReq.getTactileMapYn(),
                searchReq.getAudioGuideYn()
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
        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

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

} 