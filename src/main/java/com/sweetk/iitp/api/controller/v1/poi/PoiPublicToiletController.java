package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiPublicToilet;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletLocation;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletSearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiPublicToiletReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "이동형 - 편의시설 - 공중 화장실 POI API V1", description = "이동형 - 편의시설 - 공중 화장실 위치 및 시설 정보 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_PUBLIC_PUBLIC_TOILET)
@RequiredArgsConstructor
public class PoiPublicToiletController {

    private final PoiPublicToiletReadService toiletReadService;

    @GetMapping("/{toiletId}")
    @Operation(
            summary = "공중 화장실 상세 조회",
            description = "공중 화장실 ID로 상세 정보 조회."
    )
    public ResponseEntity<ApiResDto<PoiPublicToilet>> getPublicToiletById(
            @Parameter(description = "공중 화장실 ID", required = true)
            @PathVariable Integer toiletId,
            HttpServletRequest request) {

        log.info("공중 화장실 상세 조회 요청 - ID: {}", toiletId);

        try {
            Optional<PoiPublicToilet> toiletInfo = toiletReadService.findById(toiletId);
            if (toiletInfo.isPresent()) {
                return ResponseEntity.ok(ApiResDto.success(toiletInfo.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*******************************
     ** 전체 공중 화장실 조회
     *******************************/
    @GetMapping("/all")
    @Operation(
            summary = "전체 공중 화장실 조회 (전체 결과)",
            description = "모든 공중 화장실 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToilet>>> getAllPublicToilets(
            HttpServletRequest request) {
        try {
            List<PoiPublicToilet> result = toiletReadService.getAllPublicToilets();
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/paging")
    @Operation(
            summary = "전체 공중 화장실 조회 (페이징)",
            description = "모든 공중 화장실 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToilet>>> getAllPublicToiletsPaging(
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        try {
            PageRes<PoiPublicToilet> result = toiletReadService.getAllPublicToilets(page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     ** 시도별 공중 화장실 조회
     *******************************/
    @GetMapping("/sido/{sidoCode}")
    @Operation(
            summary = "시도별 공중 화장실 조회 (전체 결과)",
            description = "시도 코드로 공중 화장실 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToilet>>> getPublicToiletsBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9110000")
            @PathVariable String sidoCode,
            HttpServletRequest request) {
        try {
            List<PoiPublicToilet> result = toiletReadService.getPublicToiletsBySido(sidoCode);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}/paging")
    @Operation(
            summary = "시도별 공중 화장실 조회 (페이징)",
            description = "시도 코드로 공중 화장실 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToilet>>> getPublicToiletsBySidoPaging(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9110000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        try {
            PageRes<PoiPublicToilet> result = toiletReadService.getPublicToiletsBySidoPaging(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     **  카테고리 기반 검색 공중 화장실 조회
     *******************************/
    @GetMapping("/search")
    @Operation(
            summary = "공중 화장실 카테고리 검색 (전체 결과)",
            description = "이름, 시도코드, 유형, 24시간개방으로 공중 화장실을 검색합니다. 검색 조건이 있어야 합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToilet>>> searchByCategory(
            @Valid @ParameterObject PoiPublicToiletSearchCatReq searchKeys,
            HttpServletRequest request) {

        try {
            if (searchKeys == null) {
                log.error("[{}] : no input parameter", request.getRequestURI());
                throw new BusinessException(ErrorCode.INVALID_PARAMETER);
            }
            List<PoiPublicToilet> searchRet = toiletReadService.getPublicToiletsByCategory(searchKeys);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/paging")
    @Operation(
            summary = "공중 화장실 카테고리 검색 (페이징)",
            description = "이름, 시도코드, 유형, 24시간개방으로 공중 화장실을 검색합니다. 검색 조건이 있어야 합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToilet>>> searchByCategoryPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiPublicToiletSearchCatReq searchKeys,
            HttpServletRequest request) {

        try {
            if (searchKeys == null) {
                log.error("[{}] : no input parameter", request.getRequestURI());
                throw new BusinessException(ErrorCode.INVALID_PARAMETER);
            }

            PageRes<PoiPublicToilet> searchRet = toiletReadService.getPublicToiletsByCategoryPaging(searchKeys, page);

            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     **  위치 기반 검색 공중 화장실 조회
     *******************************/

    @GetMapping("/search/location")
    @Operation(
        summary = "공중 화장실 위치 기반 검색 (전체 결과)",
        description = "위치 기준 반경 내 공중 화장실을 검색합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToiletLocation>>> searchByLocation(
            @Valid @ParameterObject PoiPublicToiletSearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        List<PoiPublicToiletLocation> searchRet = toiletReadService.getPublicToiletsByLocation(searchKeys);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }

    @GetMapping("/search/location/paging")
    @Operation(
            summary = "공중 화장실 위치 기반 검색 (페이징)",
            description = "위치 기준 반경 내 공중 화장실을 검색합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletLocation>>> searchByLocationPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiPublicToiletSearchLocReq searchKeys,
            HttpServletRequest request) {

        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());

        PageRes<PoiPublicToiletLocation> searchRet = toiletReadService.getPublicToiletsByLocationPaging(searchKeys, page);

        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 