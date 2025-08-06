package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchLocReq;
import com.sweetk.iitp.api.dto.poi.converter.PoiSubwayElevatorConverter;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiSubwayElevatorRepository;
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
public class PoiSubwayElevatorReadService {

    private final PoiSubwayElevatorRepository poiSubwayElevatorRepository;
    private final SysCommonCodeService commonCodeService;

    /**
     * 지하철 엘리베이터 ID로 조회
     */
    public Optional<PoiSubwayElevator> findById(Integer subwayId) {
        log.debug("지하철 엘리베이터 조회 요청 - ID: {}", subwayId);
        return poiSubwayElevatorRepository.findById(subwayId)
                .map(PoiSubwayElevatorConverter::toDto);
    }

    /**
     * 시도별 지하철 엘리베이터 조회 (페이징)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsBySido(String sidoCode, PageReq pageReq) {

        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 지하철 엘리베이터 조회 요청 - 시도 코드: {}, 페이지: {}", sidoCode, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 시도별 조회 전용 함수 사용 (성능 최적화)
        List<PoiSubwayElevator> results = poiSubwayElevatorRepository.findBySidoCodeWithPaging(
                sidoCode,
                offset,
                size
        );
        
        long totalCount = poiSubwayElevatorRepository.countBySidoCode(sidoCode);
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 시군구별 지하철 엘리베이터 조회 (페이징)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsBySigungu(String sidoCode, String sigunguCode, PageReq pageReq) {
        log.debug("시군구별 지하철 엘리베이터 조회 요청 - 시도 코드: {}, 시군구 코드: {}, 페이지: {}", sidoCode, sigunguCode, pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiSubwayElevator> results = poiSubwayElevatorRepository.findBySigunguConditions(
                sidoCode,
                sigunguCode,
                offset,
                size
        );
        
        long totalCount = poiSubwayElevatorRepository.countBySigunguConditions(
                sidoCode,
                sigunguCode
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 카테고리 기반 지하철 엘리베이터 검색 (통합 처리)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsByCategory(PoiSubwayElevatorSearchCatReq searchReq, PageReq pageReq) {
        log.debug("지하철 엘리베이터 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);
        
        // 검색 조건이 없는 경우 전체 조회 (성능 최적화)
        if (searchReq == null || !hasSearchConditions(searchReq)) {
            return getAllSubwayElevators(pageReq);
        }
        
        // 검색 조건이 있는 경우 조건 검색
        return searchSubwayElevatorsByConditions(searchReq, pageReq);
    }

    /**
     * 검색 조건 존재 여부 확인
     */
    private boolean hasSearchConditions(PoiSubwayElevatorSearchCatReq searchReq) {
        return (searchReq.getStationName() != null && !searchReq.getStationName().trim().isEmpty()) ||
               (searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty()) ||
               (searchReq.getNodeTypeCode() != null);
    }

    /**
     * 전체 지하철 엘리베이터 조회 (검색 조건 없음)
     */
    private PageRes<PoiSubwayElevator> getAllSubwayElevators(PageReq pageReq) {
        log.debug("전체 지하철 엘리베이터 조회");
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // 전체 조회 전용 함수 사용 (성능 최적화)
        List<PoiSubwayElevator> results = poiSubwayElevatorRepository.findAllWithPaging(offset, size);
        long totalCount = poiSubwayElevatorRepository.countAll();
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 조건 검색으로 지하철 엘리베이터 조회
     */
    private PageRes<PoiSubwayElevator> searchSubwayElevatorsByConditions(PoiSubwayElevatorSearchCatReq searchReq, PageReq pageReq) {
        log.debug("조건 검색으로 지하철 엘리베이터 조회");
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiSubwayElevator> results = poiSubwayElevatorRepository.findByCategoryConditions(
                searchReq.getStationName(),
                searchReq.getSidoCode(),
                searchReq.getNodeTypeCodeValue(), // Integer 값으로 변환
                offset,
                size
        );
        
        long totalCount = poiSubwayElevatorRepository.countByCategoryConditions(
                searchReq.getStationName(),
                searchReq.getSidoCode(),
                searchReq.getNodeTypeCodeValue() // Integer 값으로 변환
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 위치 기반 지하철 엘리베이터 검색
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsByLocation(PoiSubwayElevatorSearchLocReq searchReq, PageReq pageReq) {
        log.debug("지하철 엘리베이터 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}", 
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        List<PoiSubwayElevator> results = poiSubwayElevatorRepository.findByLocationWithPaging(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                offset,
                size
        );
        
        long totalCount = poiSubwayElevatorRepository.countByLocation(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius()
        );
        
        return new PageRes<>(results, pageReq.toPageable(), totalCount);
    }

    /**
     * 시도 코드로 지하철 엘리베이터 조회 (기존 메서드)
     */
    public List<PoiSubwayElevator> findBySidoCode(String sidoCode) {
        log.debug("시도 코드로 지하철 엘리베이터 조회 요청 - 시도 코드: {}", sidoCode);
        return poiSubwayElevatorRepository.findBySidoCode(sidoCode)
                .stream()
                .map(PoiSubwayElevatorConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 지하철역 코드로 지하철 엘리베이터 조회 (기존 메서드)
     */
    public List<PoiSubwayElevator> findByStationCode(String stationCode) {
        log.debug("지하철역 코드로 지하철 엘리베이터 조회 요청 - 역 코드: {}", stationCode);
        return poiSubwayElevatorRepository.findByStationCode(stationCode)
                .stream()
                .map(PoiSubwayElevatorConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 위치 기반 지하철 엘리베이터 조회 (기존 메서드)
     */
    public List<PoiSubwayElevator> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        log.debug("위치 기반 지하철 엘리베이터 조회 요청 - 위도: {}~{}, 경도: {}~{}", minLat, maxLat, minLng, maxLng);
        return poiSubwayElevatorRepository.findByLocationRange(minLat, maxLat, minLng, maxLng)
                .stream()
                .map(PoiSubwayElevatorConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 노드 유형 코드로 지하철 엘리베이터 조회 (기존 메서드)
     */
    public List<PoiSubwayElevator> findByNodeTypeCode(Integer nodeTypeCode) {
        log.debug("노드 유형 코드로 지하철 엘리베이터 조회 요청 - 노드 유형: {}", nodeTypeCode);
        return poiSubwayElevatorRepository.findByNodeTypeCode(nodeTypeCode)
                .stream()
                .map(PoiSubwayElevatorConverter::toDto)
                .collect(Collectors.toList());
    }
} 