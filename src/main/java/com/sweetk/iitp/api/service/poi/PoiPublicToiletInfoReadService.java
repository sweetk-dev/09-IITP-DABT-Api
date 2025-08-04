package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchLocReq;
import com.sweetk.iitp.api.dto.poi.converter.PoiPublicToiletInfoConverter;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiPublicToiletInfoRepository;
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
public class PoiPublicToiletInfoReadService {

    private final PoiPublicToiletInfoRepository poiPublicToiletInfoRepository;

    /**
     * 시도별 공중 화장실 조회 (페이징)
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsBySido(String sidoCode, PageReq pageReq) {
        log.debug("시도별 공중 화장실 조회 요청 - 시도 코드: {}, 페이지: {}", sidoCode, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiPublicToiletInfo> results = poiPublicToiletInfoRepository.findByCategoryConditions(
                null, // toiletName
                sidoCode, // sidoCode
                null, // toiletType
                null, // disabilityFacilityYn
                null, // open24hYn
                offset,
                size
        );
        
        long totalCount = poiPublicToiletInfoRepository.countByCategoryConditions(
                null, // toiletName
                sidoCode, // sidoCode
                null, // toiletType
                null, // disabilityFacilityYn
                null  // open24hYn
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 공중 화장실 ID로 조회
     */
    public Optional<PoiPublicToiletInfo> findById(Integer toiletId) {
        log.debug("공중 화장실 조회 요청 - ID: {}", toiletId);
        try {
            return poiPublicToiletInfoRepository.findById(toiletId)
                    .map(PoiPublicToiletInfoConverter::toDto);
        } catch (Exception e) {
            log.error("공중 화장실 조회 중 오류 발생 - ID: {}, 오류: {}", toiletId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 시도 코드로 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findBySidoCode(String sidoCode) {
        log.debug("시도 코드로 공중 화장실 조회 요청 - 시도 코드: {}", sidoCode);
        return poiPublicToiletInfoRepository.findBySidoCode(sidoCode)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 화장실 유형으로 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByToiletType(String toiletType) {
        log.debug("화장실 유형으로 공중 화장실 조회 요청 - 유형: {}", toiletType);
        return poiPublicToiletInfoRepository.findByToiletType(toiletType)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 화장실명으로 공중 화장실 조회 (부분 일치)
     */
    public List<PoiPublicToiletInfo> findByToiletNameContaining(String toiletName) {
        log.debug("화장실명으로 공중 화장실 조회 요청 - 화장실명: {}", toiletName);
        return poiPublicToiletInfoRepository.findByToiletNameContaining(toiletName)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 위치 기반 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        log.debug("위치 기반 공중 화장실 조회 요청 - 위도: {}~{}, 경도: {}~{}", minLat, maxLat, minLng, maxLng);
        return poiPublicToiletInfoRepository.findByLocationRange(minLat, maxLat, minLng, maxLng)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 편의시설이 있는 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByFacilityType(String facilityType, String ynValue) {
        log.debug("편의시설별 공중 화장실 조회 요청 - 편의시설: {}, 값: {}", facilityType, ynValue);
        return poiPublicToiletInfoRepository.findByFacilityType(facilityType, ynValue)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 장애인 시설이 있는 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByDisabilityFacility() {
        log.debug("장애인 시설이 있는 공중 화장실 조회 요청");
        return poiPublicToiletInfoRepository.findByDisabilityFacility()
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 어린이 시설이 있는 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByChildFacility() {
        log.debug("어린이 시설이 있는 공중 화장실 조회 요청");
        return poiPublicToiletInfoRepository.findByChildFacility()
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 24시간 개방 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findBy24HourOpen() {
        log.debug("24시간 개방 공중 화장실 조회 요청");
        return poiPublicToiletInfoRepository.findBy24HourOpen()
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 복합 조건으로 공중 화장실 검색
     */
    public List<PoiPublicToiletInfo> findByMultipleConditions(String sidoCode, String toiletType, 
                                                             String toiletName, String safetyTargetYn, 
                                                             String emgBellYn, String cctvYn) {
        log.debug("복합 조건으로 공중 화장실 검색 요청 - 시도: {}, 유형: {}, 화장실명: {}, 안전관리: {}, 비상벨: {}, CCTV: {}", 
                sidoCode, toiletType, toiletName, safetyTargetYn, emgBellYn, cctvYn);
        return poiPublicToiletInfoRepository.findByMultipleConditions(sidoCode, toiletType, toiletName, safetyTargetYn, emgBellYn, cctvYn)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }


    /**
     * 카테고리 기반 공중 화장실 검색
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsByCategory(PoiPublicToiletInfoSearchCatReq searchReq, PageReq pageReq) {
        log.debug("공중 화장실 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 검색 조건 확인
        boolean hasToiletName = searchReq != null && searchReq.getToiletName() != null && !searchReq.getToiletName().trim().isEmpty();
        boolean hasSidoCode = searchReq != null && searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty();
        boolean hasToiletType = searchReq != null && searchReq.getToiletType() != null && !searchReq.getToiletType().trim().isEmpty();
        boolean hasDisabilityFacility = searchReq != null && "Y".equals(searchReq.getDisabilityFacilityYn());
        boolean hasOpen24h = searchReq != null && "Y".equals(searchReq.getOpen24hYn());
        
        // 검색 조건이 없는 경우 전체 조회
        if (!hasToiletName && !hasSidoCode && !hasToiletType && !hasDisabilityFacility && !hasOpen24h) {
            List<PoiPublicToiletInfo> results = poiPublicToiletInfoRepository.findAllWithPagination(pageReq.toPageable())
                    .map(PoiPublicToiletInfoConverter::toDto)
                    .getContent();
            long totalCount = poiPublicToiletInfoRepository.count();
            return new PageRes<>(results, pageReq.toPageable(), totalCount);
        }
        
        // 복합 조건 검색
        List<PoiPublicToiletInfo> results = poiPublicToiletInfoRepository.findByCategoryConditions(
                searchReq != null ? searchReq.getToiletName() : null,
                searchReq != null ? searchReq.getSidoCode() : null,
                searchReq != null ? searchReq.getToiletType() : null,
                searchReq != null ? searchReq.getDisabilityFacilityYn() : null,
                searchReq != null ? searchReq.getOpen24hYn() : null,
                offset,
                size
        );
        
        long totalCount = poiPublicToiletInfoRepository.countByCategoryConditions(
                searchReq != null ? searchReq.getToiletName() : null,
                searchReq != null ? searchReq.getSidoCode() : null,
                searchReq != null ? searchReq.getToiletType() : null,
                searchReq != null ? searchReq.getDisabilityFacilityYn() : null,
                searchReq != null ? searchReq.getOpen24hYn() : null
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 위치 기반 공중 화장실 검색
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsByLocation(PoiPublicToiletInfoSearchLocReq searchReq, PageReq pageReq) {
        log.debug("공중 화장실 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}", 
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiPublicToiletInfo> results = poiPublicToiletInfoRepository.findByLocationWithPaging(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                offset,
                size
        );
        
        long totalCount = poiPublicToiletInfoRepository.countByLocation(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius()
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }
} 