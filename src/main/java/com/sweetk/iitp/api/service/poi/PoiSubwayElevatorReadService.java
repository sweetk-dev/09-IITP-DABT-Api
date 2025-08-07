package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiSubwayElevatorRepository;
import com.sweetk.iitp.api.service.sys.SysCommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiSubwayElevatorReadService extends PoiService{

    private final PoiSubwayElevatorRepository poiSubwayElevatorRepository;
    private final SysCommonCodeService commonCodeService;

    /**
     * 지하철 엘리베이터 ID로 조회
     */
    public Optional<PoiSubwayElevator> findById(Integer subwayId) {
        log.debug("지하철 엘리베이터 조회 요청 - ID: {}", subwayId);
        return poiSubwayElevatorRepository.findByIdToDto(subwayId);
    }

    /**
     * 시도별 지하철 엘리베이터 조회 (전체 결과)
     */
    public List<PoiSubwayElevator> getSubwayElevatorsBySido(String sidoCode) {
        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 지하철 엘리베이터 조회 요청 - 시도 코드: {}", sidoCode);
        
        return poiSubwayElevatorRepository.findBySidoCodeToDto(sidoCode);
    }

    /**
     * 시도별 지하철 엘리베이터 조회 (페이징)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsBySidoPaging(String sidoCode, PageReq pageReq) {
        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 지하철 엘리베이터 조회 요청 - 시도 코드: {}, 페이지: {}", sidoCode, pageReq);

        PoiService.DbOffSet dbOffSet = setDbOffset(pageReq);
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiSubwayElevator> pageResult =
            poiSubwayElevatorRepository.findBySidoCodeWithPagingCount(sidoCode, dbOffSet.offset(), dbOffSet.size());
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 시군구별 지하철 엘리베이터 조회 (전체 결과)
     */
    public List<PoiSubwayElevator> getSubwayElevatorsBySigungu(String sidoCode, String sigunguCode) {
        log.debug("시군구별 지하철 엘리베이터 조회 요청 - 시도 코드: {}, 시군구 코드: {}", sidoCode, sigunguCode);
        
        return poiSubwayElevatorRepository.findBySigunguConditions(sidoCode, sigunguCode);
    }

    /**
     * 시군구별 지하철 엘리베이터 조회 (페이징)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsBySigunguPaging(String sidoCode, String sigunguCode, PageReq pageReq) {
        log.debug("시군구별 지하철 엘리베이터 조회 요청 - 시도 코드: {}, 시군구 코드: {}, 페이지: {}", sidoCode, sigunguCode, pageReq);

        PoiService.DbOffSet dbOffSet = setDbOffset(pageReq);
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiSubwayElevator> pageResult =
            poiSubwayElevatorRepository.findBySigunguConditionsWithPagingCount(sidoCode, sigunguCode, dbOffSet.offset(), dbOffSet.size());
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 카테고리 기반 지하철 엘리베이터 검색 (전체 결과)
     */
    public List<PoiSubwayElevator> getSubwayElevatorsByCategory(PoiSubwayElevatorSearchCatReq searchReq) {
        log.debug("지하철 엘리베이터 카테고리 검색 요청 - 검색조건: {}", searchReq);
        
        // 검색 조건이 없는 경우 빈 결과 반환 (카테고리 검색은 조건이 있어야 함)
        if (searchReq == null || !hasSearchConditions(searchReq)) {
            log.debug("카테고리 검색 조건이 없어 빈 결과 반환");
            return new ArrayList<>();
        }
        
        // 검색 조건이 있는 경우 조건 검색
        return searchSubwayElevatorsByConditions(searchReq);
    }

    /**
     * 카테고리 기반 지하철 엘리베이터 검색 (페이징)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsByCategoryPaging(PoiSubwayElevatorSearchCatReq searchReq, PageReq pageReq) {
        log.debug("지하철 엘리베이터 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);
        // 검색 조건이 있는 경우 조건 검색
        return searchSubwayElevatorsByConditionsPaging(searchReq, pageReq);
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
     * 조건 검색으로 지하철 엘리베이터 조회 (전체 결과)
     */
    private List<PoiSubwayElevator> searchSubwayElevatorsByConditions(PoiSubwayElevatorSearchCatReq searchReq) {
        log.debug("조건 검색으로 지하철 엘리베이터 조회 (전체 결과)");
        
        return poiSubwayElevatorRepository.findByCategoryConditions(
                searchReq.getStationName(),
                searchReq.getSidoCode(),
                searchReq.getNodeTypeCodeValue() // Integer 값으로 변환
        );
    }

    /**
     * 조건 검색으로 지하철 엘리베이터 조회 (페이징)
     */
    private PageRes<PoiSubwayElevator> searchSubwayElevatorsByConditionsPaging(PoiSubwayElevatorSearchCatReq searchReq, PageReq pageReq) {
        log.debug("조건 검색으로 지하철 엘리베이터 조회 (페이징)");

        PoiService.DbOffSet dbOffSet = setDbOffset(pageReq);
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiSubwayElevator> pageResult =
            poiSubwayElevatorRepository.findByCategoryConditionsWithPagingCount(
                searchReq.getStationName(),
                searchReq.getSidoCode(),
                searchReq.getNodeTypeCodeValue(), // Integer 값으로 변환
                dbOffSet.offset(),
                dbOffSet.size()
            );
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 위치 기반 지하철 엘리베이터 검색 (전체 결과)
     */
    public List<PoiSubwayElevator> getSubwayElevatorByLocation(PoiSubwayElevatorSearchLocReq searchReq) {
        log.debug("지하철 엘리베이터 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m", 
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius());
        
        return poiSubwayElevatorRepository.findByLocation(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius()
        );
    }

    /**
     * 위치 기반 지하철 엘리베이터 검색 (페이징)
     */
    public PageRes<PoiSubwayElevator> getSubwayElevatorsByLocationPaging(PoiSubwayElevatorSearchLocReq searchReq, PageReq pageReq) {
        log.debug("지하철 엘리베이터 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}", 
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);

        PoiService.DbOffSet dbOffSet = setDbOffset(pageReq);
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiSubwayElevator> pageResult =
            poiSubwayElevatorRepository.findByLocationWithPagingCount(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                dbOffSet.offset(),
                dbOffSet.size()
            );
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 전체 지하철 엘리베이터 조회 (전체 결과)
     */
    public List<PoiSubwayElevator> getAllSubwayElevators() {
        log.debug("전체 지하철 엘리베이터 조회 (전체 결과)");
        
        return poiSubwayElevatorRepository.findAllToDto();
    }

    /**
     * 전체 지하철 엘리베이터 조회 (페이징)
     */
    public PageRes<PoiSubwayElevator> getAllSubwayElevators(PageReq pageReq) {
        log.debug("전체 지하철 엘리베이터 조회 (페이징)");

        PoiService.DbOffSet dbOffSet = setDbOffset(pageReq);
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiSubwayElevator> pageResult =
            poiSubwayElevatorRepository.findAllWithPagingCount(dbOffSet.offset(), dbOffSet.size());
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }
} 