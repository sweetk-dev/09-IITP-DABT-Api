package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants.SysCodeGroup;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiPublicToilet;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletLocation;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletSearchLocReq;
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
public class PoiPublicToiletInfoReadService extends PoiService {

    private final PoiPublicToiletInfoRepository toiletRepository;
    private final SysCommonCodeService commonCodeService;




    /*******************************
     ** 공중 화장실 ID로 조회
     *******************************/
    public Optional<PoiPublicToilet> findById(Integer toiletId){
        log.debug("공중 화장실 조회 요청 - ID: {}", toiletId);
        return toiletRepository.findByIdToDto(toiletId);
    }



    /*******************************
     ** 전체 공중 화장실 조회
     *******************************/
    //전체 공중 화장실 조회 (전체 결과)
    public List<PoiPublicToilet> getAllPublicToilets() {
        log.debug("전체 공중 화장실 조회 (전체 결과)");

        // 전체 조회 전용 함수 사용 (성능 최적화)
        return toiletRepository.findAllToilets();
    }

    //전체 공중 화장실 조회 (페이징)
    public PageRes<PoiPublicToilet> getAllPublicToilets(PageReq pageReq) {
        log.debug("전체 공중 화장실 조회 (페이징)");

        DbOffSet dbOffSet = setDbOffset(pageReq);
        PoiPageResult<PoiPublicToilet> pageResult =
                toiletRepository.findAllWithPagingCount(dbOffSet.offset(), dbOffSet.size());

        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }


    /*******************************
     ** 시도별 공중 화장실 조회
     *******************************/

    //시도별 공중 화장실 조회 (전체 결과)
    public List<PoiPublicToilet> getPublicToiletsBySido(String sidoCode) {
        if(!commonCodeService.isValidCode(SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 공중 화장실 조회 요청 - 시도 코드: {}", sidoCode);

        // 시도별 조회 전용 함수 사용 (성능 최적화)
        return toiletRepository.findBySidoCodeToDto(sidoCode);
    }

    //시도별 공중 화장실 조회 (페이징)
    public PageRes<PoiPublicToilet> getPublicToiletsBySidoPaging(String sidoCode, PageReq pageReq) {
        if(!commonCodeService.isValidCode(SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        DbOffSet dbOffSet = setDbOffset(pageReq);
        PoiPageResult<PoiPublicToilet> pageResult =
            toiletRepository.findBySidoCodeWithPagingCount(sidoCode, dbOffSet.offset(), dbOffSet.size());
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }




    /*******************************
     **  카테고리 기반 검색 공중 화장실 조회
     *******************************/
    //카테고리 기반 공중 화장실 검색 (전체 결과)
    public List<PoiPublicToilet> getPublicToiletsByCategory(PoiPublicToiletSearchCatReq searchReq) {
        log.debug("공중 화장실 카테고리 검색 요청 - 검색조건: {}", searchReq);

        if(!hasSearchConditions(searchReq)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return toiletRepository.findByCategoryConditions(
                searchReq.getToiletName(),
                searchReq.getSidoCode(),
                searchReq.getToiletType(),
                searchReq.getOpen24hYn()
        );
    }


    //카테고리 기반 공중 화장실 검색 (페이징)
    public PageRes<PoiPublicToilet> getPublicToiletsByCategoryPaging(PoiPublicToiletSearchCatReq searchReq, PageReq pageReq) {
        log.debug("공중 화장실 카테고리 검색 요청 - 검색조건: {}, 페이지: {}", searchReq, pageReq);

        if(!hasSearchConditions(searchReq)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        DbOffSet dbOffSet = setDbOffset(pageReq);

        // 검색 조건이 있는 경우 조건 검색
        PoiPageResult<PoiPublicToilet> pageResult =  toiletRepository.findByCategoryConditionsWithPagingCount(
                                                                searchReq.getToiletName(),
                                                                searchReq.getSidoCode(),
                                                                searchReq.getToiletType(),
                                                                searchReq.getOpen24hYn(),
                                                                dbOffSet.offset(),
                                                                dbOffSet.size()
                                                            );

        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }


    /*******************************
     **  위치 기반 검색 공중 화장실 조회
     *******************************/

    //위치 기반 공중 화장실 검색 (전체 결과)
    public List<PoiPublicToiletLocation> getPublicToiletsByLocation(PoiPublicToiletSearchLocReq searchReq) {
        log.debug("공중 화장실 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m",
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius());

        // 단일 함수로 통합 - 조건이 있든 없든 동일한 함수 사용
        return toiletRepository.findByLocationWithConditions(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                searchReq.getToiletName(),
                searchReq.getToiletType(),
                searchReq.getOpen24hYn()
        );
    }


    //위치 기반 공중 화장실 검색 (페이징)
    public PageRes<PoiPublicToiletLocation> getPublicToiletsByLocationPaging(PoiPublicToiletSearchLocReq searchReq, PageReq pageReq) {
        log.debug("공중 화장실 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}",
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);

        DbOffSet dbOffSet = setDbOffset(pageReq);

        // 단일 함수로 통합 - 조건이 있든 없든 동일한 함수 사용
        PoiPageResult<PoiPublicToiletLocation> pageResult =
            toiletRepository.findByLocationWithConditionsAndPagingCount(
                    searchReq.getLatitude(),
                    searchReq.getLongitude(),
                    searchReq.getRadius(),
                    searchReq.getToiletName(),
                    searchReq.getToiletType(),
                    searchReq.getOpen24hYn(),
                    dbOffSet.offset(),
                    dbOffSet.size()
            );

        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }



    //검색 조건 존재 여부 확인
    private boolean hasSearchConditions(PoiPublicToiletSearchCatReq searchReq) {
        return (searchReq.getToiletName() != null && !searchReq.getToiletName().trim().isEmpty()) ||
                (searchReq.getSidoCode() != null && !searchReq.getSidoCode().trim().isEmpty()) ||
                (searchReq.getToiletType() != null) ||
                "Y".equals(searchReq.getOpen24hYn());
    }
} 