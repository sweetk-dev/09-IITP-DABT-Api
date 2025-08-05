package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants.SysCodeGroup;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchLocReq;
import com.sweetk.iitp.api.dto.poi.converter.PoiPublicToiletInfoConverter;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiPublicToiletInfoRepository;
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
public class PoiPublicToiletInfoReadService {

    private final PoiPublicToiletInfoRepository publicToiletInfoRepository;
    private final SysCommonCodeService commonCodeService;

    /**
     * 시도별 공중 화장실 조회 (페이징)
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsBySido(String sidoCode, PageReq pageReq) {
        if(!commonCodeService.isValidCode(SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();

        // 시도별 조회 전용 함수 사용 (성능 최적화)
        List<PoiPublicToiletInfo> results = publicToiletInfoRepository.findBySidoCodeWithPaging(
                sidoCode,
                offset,
                size
        );
        
        long totalCount = publicToiletInfoRepository.countBySidoCode(sidoCode);
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 공중 화장실 ID로 조회
     */
    public Optional<PoiPublicToiletInfo> findById(Integer toiletId) {
        log.debug("공중 화장실 조회 요청 - ID: {}", toiletId);
        try {
            return publicToiletInfoRepository.findById(toiletId)
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
        return publicToiletInfoRepository.findBySidoCode(sidoCode)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 화장실 유형으로 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByToiletType(String toiletType) {
        log.debug("화장실 유형으로 공중 화장실 조회 요청 - 유형: {}", toiletType);
        return publicToiletInfoRepository.findByToiletType(toiletType)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 화장실명으로 공중 화장실 조회 (부분 일치)
     */
    public List<PoiPublicToiletInfo> findByToiletNameContaining(String toiletName) {
        log.debug("화장실명으로 공중 화장실 조회 요청 - 화장실명: {}", toiletName);
        return publicToiletInfoRepository.findByToiletNameContaining(toiletName)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 위치 기반 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        log.debug("위치 기반 공중 화장실 조회 요청 - 위도: {}~{}, 경도: {}~{}", minLat, maxLat, minLng, maxLng);
        return publicToiletInfoRepository.findByLocationRange(minLat, maxLat, minLng, maxLng)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 편의시설이 있는 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByFacilityType(String facilityType, String ynValue) {
        log.debug("편의시설별 공중 화장실 조회 요청 - 편의시설: {}, 값: {}", facilityType, ynValue);
        return publicToiletInfoRepository.findByFacilityType(facilityType, ynValue)
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 장애인 시설이 있는 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByDisabilityFacility() {
        log.debug("장애인 시설이 있는 공중 화장실 조회 요청");
        return publicToiletInfoRepository.findByDisabilityFacility()
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 어린이 시설이 있는 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findByChildFacility() {
        log.debug("어린이 시설이 있는 공중 화장실 조회 요청");
        return publicToiletInfoRepository.findByChildFacility()
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 24시간 개방 공중 화장실 조회
     */
    public List<PoiPublicToiletInfo> findBy24HourOpen() {
        log.debug("24시간 개방 공중 화장실 조회 요청");
        return publicToiletInfoRepository.findBy24HourOpen()
                .stream()
                .map(PoiPublicToiletInfoConverter::toDto)
                .collect(Collectors.toList());
    }

//    /**
//     * 복합 조건으로 공중 화장실 검색
//     */
//    public List<PoiPublicToiletInfo> findByMultipleConditions(String sidoCode, String toiletType,
//                                                             String toiletName, String safetyTargetYn,
//                                                             String emgBellYn, String cctvYn) {
//        log.debug("복합 조건으로 공중 화장실 검색 요청 - 시도: {}, 유형: {}, 화장실명: {}, 안전관리: {}, 비상벨: {}, CCTV: {}",
//                sidoCode, toiletType, toiletName, safetyTargetYn, emgBellYn, cctvYn);
//        return publicToiletInfoRepository.findByMultipleConditions(sidoCode, toiletType, toiletName, safetyTargetYn, emgBellYn, cctvYn)
//                .stream()
//                .map(PoiPublicToiletInfoConverter::toDto)
//                .collect(Collectors.toList());
//    }


    /**
     * 카테고리 기반 공중 화장실 검색 (통합 처리)
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsByCategory(PoiPublicToiletInfoSearchCatReq searchReq, PageReq pageReq) {
        
        // 검색 조건이 없는 경우 전체 조회 (성능 최적화)
        if (searchReq == null || !hasSearchConditions(searchReq)) {
            return getAllPublicToilets(pageReq);
        }
        
        // 검색 조건이 있는 경우 조건 검색
        return searchPublicToiletsByConditions(searchReq, pageReq);
    }

    /**
     * 검색 조건 존재 여부 확인
     */
    private boolean hasSearchConditions(PoiPublicToiletInfoSearchCatReq searchReq) {
        return (searchReq.getToiletName() != null && !searchReq.getToiletName().trim().isEmpty()) ||
               (searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty()) ||
               (searchReq.getToiletType() != null) ||
               "Y".equals(searchReq.getDisabilityFacilityYn()) ||
               "Y".equals(searchReq.getOpen24hYn());
    }

    /**
     * 전체 공중 화장실 조회 (검색 조건 없음)
     */
    private PageRes<PoiPublicToiletInfo> getAllPublicToilets(PageReq pageReq) {
        log.debug("전체 공중 화장실 조회");
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 전체 조회 전용 함수 사용 (성능 최적화)
        List<PoiPublicToiletInfo> results = publicToiletInfoRepository.findAllWithPaging(offset, size);
        long totalCount = publicToiletInfoRepository.countAll();
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 조건 검색으로 공중 화장실 조회
     */
    private PageRes<PoiPublicToiletInfo> searchPublicToiletsByConditions(PoiPublicToiletInfoSearchCatReq searchReq, PageReq pageReq) {
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiPublicToiletInfo> results = publicToiletInfoRepository.findByCategoryConditions(
                searchReq.getToiletName(),
                searchReq.getSidoCode(),
                searchReq.getToiletType(),
                searchReq.getDisabilityFacilityYn(),
                searchReq.getOpen24hYn(),
                offset,
                size
        );
        
        long totalCount = publicToiletInfoRepository.countByCategoryConditions(
                searchReq.getToiletName(),
                searchReq.getSidoCode(),
                searchReq.getToiletType(),
                searchReq.getDisabilityFacilityYn(),
                searchReq.getOpen24hYn()
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
        
        List<PoiPublicToiletInfo> results = publicToiletInfoRepository.findByLocationWithPaging(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                offset,
                size
        );
        
        long totalCount = publicToiletInfoRepository.countByLocation(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius()
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }
} 