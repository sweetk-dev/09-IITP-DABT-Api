package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants.SysCodeGroup;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchLocReq;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiPublicToiletInfoReadService {

    private final PoiPublicToiletInfoRepository publicToiletInfoRepository;
    private final SysCommonCodeService commonCodeService;

    /**
     * 공중 화장실 ID로 조회
     */
    public Optional<PoiPublicToiletInfo> findById(Integer toiletId) {
        log.debug("공중 화장실 조회 요청 - ID: {}", toiletId);
        try {
            return publicToiletInfoRepository.findByIdToDto(toiletId);
        } catch (Exception e) {
            log.error("공중 화장실 조회 중 오류 발생 - ID: {}, 오류: {}", toiletId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 시도별 공중 화장실 조회 (페이징)
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsBySidoPaging(String sidoCode, PageReq pageReq) {
        if(!commonCodeService.isValidCode(SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();

        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> pageResult = 
            publicToiletInfoRepository.findBySidoCodeWithPaging(sidoCode, offset, size);
        
        return new PageRes<>(pageResult.getResults(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 시도별 공중 화장실 조회 (전체 결과)
     */
    public List<PoiPublicToiletInfo> getPublicToiletsBySido(String sidoCode) {
        if(!commonCodeService.isValidCode(SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 공중 화장실 조회 요청 - 시도 코드: {}", sidoCode);
        
        // 시도별 조회 전용 함수 사용 (성능 최적화)
        return publicToiletInfoRepository.findBySidoCodeToDto(sidoCode);
    }

    /**
     * 전체 공중 화장실 조회 (페이징)
     */
    public PageRes<PoiPublicToiletInfo> getAllPublicToilets(PageReq pageReq) {
        log.debug("전체 공중 화장실 조회 (페이징)");
        
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> pageResult =
            publicToiletInfoRepository.findAllWithPaging(offset, size);
        
        return new PageRes<>(pageResult.getResults(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 전체 공중 화장실 조회 (전체 결과)
     */
    public List<PoiPublicToiletInfo> getAllPublicToilets() {
        log.debug("전체 공중 화장실 조회 (전체 결과)");
        
        // 전체 조회 전용 함수 사용 (성능 최적화)
        return publicToiletInfoRepository.findAllToilets();
    }

    /**
     * 카테고리 기반 공중 화장실 검색 (페이징)
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsByCategoryPaging(PoiPublicToiletInfoSearchCatReq searchReq, PageReq pageReq) {
        log.debug("공중 화장실 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);

        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();

        // 검색 조건이 있는 경우 조건 검색
        MvPoiPageResult<PoiPublicToiletInfo> pageResult =  publicToiletInfoRepository.findByCategoryConditionsWithPaging(
                                                                searchReq.getToiletName(),
                                                                searchReq.getSidoCode(),
                                                                searchReq.getToiletType(),
                                                                searchReq.getOpen24hYn(),
                                                                offset,
                                                                size
                                                            );

        return new PageRes<>(pageResult.getResults(), pageReq.toPageable(), pageResult.getTotalCount());
    }

    /**
     * 카테고리 기반 공중 화장실 검색 (전체 결과)
     */
    public List<PoiPublicToiletInfo> getPublicToiletsByCategory(PoiPublicToiletInfoSearchCatReq searchReq) {
        log.debug("공중 화장실 카테고리 검색 요청 - 검색조건: {}", searchReq);

        // 검색 조건이 없는 경우 빈 결과 반환 (카테고리 검색은 조건이 있어야 함)
        if (searchReq == null || !hasSearchConditions(searchReq)) {
            return getAllPublicToilets();
        }

        // 검색 조건이 있는 경우 조건 검색
        return searchPublicToiletsByConditions(searchReq);
    }

    /**
     * 검색 조건 존재 여부 확인
     */
    private boolean hasSearchConditions(PoiPublicToiletInfoSearchCatReq searchReq) {
        return (searchReq.getToiletName() != null && !searchReq.getToiletName().trim().isEmpty()) ||
               (searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty()) ||
               (searchReq.getToiletType() != null) ||
               "Y".equals(searchReq.getOpen24hYn());
    }

    /**
     * 조건 검색으로 공중 화장실 조회 (전체 결과)
     */
    private List<PoiPublicToiletInfo> searchPublicToiletsByConditions(PoiPublicToiletInfoSearchCatReq searchReq) {
        
        return publicToiletInfoRepository.findByCategoryConditions(
                searchReq.getToiletName(),
                searchReq.getSidoCode(),
                searchReq.getToiletType(),
                searchReq.getOpen24hYn()
        );
    }

    /**
     * 위치 기반 공중 화장실 검색 (전체 결과)
     */
    public List<PoiPublicToiletInfo> getPublicToiletsByLocation(PoiPublicToiletInfoSearchLocReq searchReq) {
        log.debug("공중 화장실 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m",
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius());
        
        // 단일 함수로 통합 - 조건이 있든 없든 동일한 함수 사용
        return publicToiletInfoRepository.findByLocationWithConditions(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                searchReq.getToiletName(),
                searchReq.getToiletType(),
                searchReq.getOpen24hYn()
        );
    }

    /**
     * 위치 기반 공중 화장실 검색 (페이징)
     */
    public PageRes<PoiPublicToiletInfo> getPublicToiletsByLocationPaging(PoiPublicToiletInfoSearchLocReq searchReq, PageReq pageReq) {
        log.debug("공중 화장실 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}",
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);

        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();

        // 단일 함수로 통합 - 조건이 있든 없든 동일한 함수 사용
        com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> pageResult = 
            publicToiletInfoRepository.findByLocationWithPaging(
                    searchReq.getLatitude(),
                    searchReq.getLongitude(),
                    searchReq.getRadius(),
                    searchReq.getToiletName(),
                    searchReq.getToiletType(),
                    searchReq.getOpen24hYn(),
                    offset,
                    size
            );

        return new PageRes<>(pageResult.getResults(), pageReq.toPageable(), pageResult.getTotalCount());
    }


} 