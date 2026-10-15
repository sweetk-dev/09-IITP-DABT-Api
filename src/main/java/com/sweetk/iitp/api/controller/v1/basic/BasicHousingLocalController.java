package com.sweetk.iitp.api.controller.v1.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.local.GgDisRegEdition;
import com.sweetk.iitp.api.dto.basic.local.GgDisRegEmd;
import com.sweetk.iitp.api.dto.basic.local.GgDisRegEmdSearchReq;
import com.sweetk.iitp.api.dto.basic.local.LocalDisRegSummary;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.service.basic.LocalDisRegReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 기초-주거 자립 현황 중 지역(시군·구·읍면동) 단위 등록장애인.
 */
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_HOUSING)
@RequiredArgsConstructor
@Tag(name = "기초-주거 자립 현황 API V1", description = "기초-주거 자립 현황 OpenApi V1")
public class BasicHousingLocalController {

    private final LocalDisRegReadService service;

    @GetMapping("/reg/ggEmd/latest")
    @Operation(summary = "경기도 읍면동별·장애유형별·장애정도별 등록장애인수 조회 (페이징)",
            description = "원천: 경기데이터드림 「경기도 내 등록장애인 집계현황」. 시군마다 원천 기준일(base_dt)이 다르며, 기본은 " +
                    "시군마다 최신 판입니다. baseDt 로 지난 판을 고를 수 있습니다(판 목록은 /reg/ggEmd/editions). " +
                    "합계 행(소계·전체)은 들어 있지 않으므로 합산해서 씁니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<GgDisRegEmd>>> searchEmd(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject GgDisRegEmdSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchEmd(searchKeys, page)));
    }

    @GetMapping("/reg/ggEmd/editions")
    @Operation(summary = "경기도 읍면동 등록장애인 판(원천 기준일) 목록",
            description = "시군별로 쌓아 둔 판의 기준일·행 수·합계와 최신 판 여부를 줍니다.")
    public ResponseEntity<ApiResDto<List<GgDisRegEdition>>> editions(
            @Parameter(description = "시군명 (정확히 일치, 비우면 전체)", example = "안양시")
            @RequestParam(required = false) String sigunName) {
        return ResponseEntity.ok(ApiResDto.success(service.editions(sigunName)));
    }

    @GetMapping("/reg/local/summary")
    @Operation(summary = "시군 단위 등록장애인 요약 (두 원천 병기)",
            description = "경기 읍면동 집계(시군 최신 판 합산)와 KOSIS 시군구별 등록장애인수(최신 연도)를 원천·기준 시점과 함께 " +
                    "나란히 줍니다. 구가 있는 시(예: 안양시 = 만안구 + 동안구)는 구별 값과 합계를 함께 줍니다. 경기도 시군만 대상입니다.")
    public ResponseEntity<ApiResDto<LocalDisRegSummary>> summary(
            @Parameter(description = "시군명 (예: 안양시, 광주시)", required = true, example = "안양시")
            @RequestParam String sigunName) {
        return ResponseEntity.ok(ApiResDto.success(service.summary(sigunName)));
    }
}
