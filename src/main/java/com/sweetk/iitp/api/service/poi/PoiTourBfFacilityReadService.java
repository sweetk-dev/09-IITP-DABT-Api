package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilityLocation;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchLocReq;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiTourBfFacilityReadService extends PoiService{

    private final PoiTourBfFacilityRepository tourBfFacilityRepository;
    private final SysCommonCodeService commonCodeService;


    /*******************************
     ** 무장애 관광지 시설 ID로 조회
     *******************************/
     // 무장애 관광지 시설 ID로 조회
    public Optional<PoiTourBfFacility> findById(Integer fcltId) {
        log.debug("무장애 관광지 시설 조회 요청 - ID: {}", fcltId);
        return tourBfFacilityRepository.findByIdToDto(fcltId);
    }



    /*******************************
     ** 전체 무장애 관광지 시설 조회
     *******************************/
    // 전체 무장애 관광지 시설 조회 (전체 결과)
    public List<PoiTourBfFacility> getAllTourBfFacilities() {
        log.debug("전체 무장애 관광지 시설 조회 요청");
        return tourBfFacilityRepository.findAllToDto();
    }

    // 전체 무장애 관광지 시설 조회 (페이징)
    public PageRes<PoiTourBfFacility> getAllTourBfFacilitiesPaging(PageReq pageReq) {
        log.debug("전체 무장애 관광지 시설 조회 요청 (페이징) - 페이지: {}", pageReq);

        DbOffSet dbOffSet = setDbOffset(pageReq);

        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiTourBfFacility> pageResult =
                tourBfFacilityRepository.findAllWithPagingCount(dbOffSet.offset(), dbOffSet.size());

        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }



    /*******************************
     ** 시도별 무장애 관광지 시설 조회
     *******************************/
     // 시도별 무장애 관광지 시설 조회 (전체 결과)
    public List<PoiTourBfFacility> getTourBfFacilitiesBySido(String sidoCode) {
        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 무장애 관광지 시설 조회 요청 - 시도 코드: {}", sidoCode);
        
        return tourBfFacilityRepository.findBySidoCode(sidoCode);
    }

     // 시도별 무장애 관광지 시설 조회 (페이징)
    public PageRes<PoiTourBfFacility> getTourBfFacilitiesBySidoPaging(String sidoCode, PageReq pageReq) {
        if(!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"sidoCode(%s) not valid".formatted(sidoCode) );
        }

        log.debug("시도별 무장애 관광지 시설 조회 요청 (페이징) - 시도 코드: {}, 페이지: {}", sidoCode, pageReq);

        DbOffSet dbOffSet = setDbOffset(pageReq);
        
        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiTourBfFacility> pageResult =
            tourBfFacilityRepository.findBySidoCodeWithPagingCount(sidoCode, dbOffSet.offset(), dbOffSet.size());

        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }




    /*******************************
     ** 카테고리 기반 무장애 관광지 시설 조회
     *******************************/
     // 카테고리 기반 무장애 관광지 시설 검색 (전체 결과)
    public List<PoiTourBfFacility> getTourBfFacilitiesByCategory(PoiTourBfFacilitySearchCatReq searchReq) {
        log.debug("무장애 관광지 시설 카테고리 검색 요청 - 검색조건: {}", searchReq);

        // 검색 조건이 없는 경우 빈 결과 반환
        if (!hasSearchConditions(searchReq)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return tourBfFacilityRepository.findByCategoryConditions(
                searchReq.getFcltName(),
                searchReq.getSidoCode(),
                searchReq.getToiletYn(),
                searchReq.getElevatorYn(),
                searchReq.getParkingYn(),
                searchReq.getWheelchairRentYn(),
                searchReq.getTactileMapYn(),
                searchReq.getAudioGuideYn()
        );
    }

     // 카테고리 기반 무장애 관광지 시설 검색 (페이징)
    public PageRes<PoiTourBfFacility> getTourBfFacilitiesByCategoryPaging(PoiTourBfFacilitySearchCatReq searchReq, PageReq pageReq) {
        log.debug("무장애 관광지 시설 카테고리 검색 요청 (페이징) - 검색조건: {}, 페이지: {}", searchReq, pageReq);

        // 검색 조건이 없는 경우 빈 페이지 반환
        if ( !hasSearchConditions(searchReq)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        DbOffSet dbOffSet = setDbOffset(pageReq);

        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiTourBfFacility> pageResult =
                tourBfFacilityRepository.findByCategoryConditionsWithPagingCount(
                        searchReq.getFcltName(),
                        searchReq.getSidoCode(),
                        searchReq.getToiletYn(),
                        searchReq.getElevatorYn(),
                        searchReq.getParkingYn(),
                        searchReq.getWheelchairRentYn(),
                        searchReq.getTactileMapYn(),
                        searchReq.getAudioGuideYn(),
                        dbOffSet.offset(),
                        dbOffSet.size()
                );

        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }




    /*******************************
     ** 위치 기반 무장애 관광지 시설 조회
     *******************************/
     // 위치 기반 무장애 관광지 시설 검색 (전체 결과)
    public List<PoiTourBfFacilityLocation> getTourBfFacilitiesByLocation(PoiTourBfFacilitySearchLocReq searchReq) {
        log.debug("무장애 관광지 시설 위치 기반 검색 요청 - 위도: {}, 경도: {}, 반경: {}m",
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius());

        return tourBfFacilityRepository.findByLocationWithConditions(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                searchReq.getFcltName(),
                //searchReq.getSidoCode(),
                searchReq.getToiletYn(),
                searchReq.getElevatorYn(),
                searchReq.getParkingYn(),
                searchReq.getWheelchairRentYn(),
                searchReq.getTactileMapYn(),
                searchReq.getAudioGuideYn()
        );
    }

     //  위치 기반 무장애 관광지 시설 검색 (페이징)
    public PageRes<PoiTourBfFacilityLocation> getTourBfFacilitiesByLocationPaging(PoiTourBfFacilitySearchLocReq searchReq, PageReq pageReq) {
        log.debug("무장애 관광지 시설 위치 기반 검색 요청 (페이징) - 위도: {}, 경도: {}, 반경: {}m, 페이지: {}",
                searchReq.getLatitude(), searchReq.getLongitude(), searchReq.getRadius(), pageReq);

        DbOffSet dbOffSet = setDbOffset(pageReq);

        // COUNT(*) OVER() 사용하여 단일 쿼리로 데이터와 총 개수 조회
        PoiPageResult<PoiTourBfFacilityLocation> pageResult =
            tourBfFacilityRepository.findByLocationWithConditionsAndPagingCount(
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius(),
                searchReq.getFcltName(),
                //searchReq.getSidoCode(),
                searchReq.getToiletYn(),
                searchReq.getElevatorYn(),
                searchReq.getParkingYn(),
                searchReq.getWheelchairRentYn(),
                searchReq.getTactileMapYn(),
                searchReq.getAudioGuideYn(),
                dbOffSet.offset(),
                dbOffSet.size()
            );
        
        return new PageRes<>(pageResult.getContent(), pageReq.toPageable(), pageResult.getTotalCount());
    }



    // 검색 조건 존재 여부 확인
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

} 