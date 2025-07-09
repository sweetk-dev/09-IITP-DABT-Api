package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.emp.EmpDisRegionalStatusDto;
import com.sweetk.iitp.api.service.emp.EmpDisRegionalStatusReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;

/**
 * 지역별 장애인 고용 현황 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping(API_V1_EMP + "/regional-status")
@RequiredArgsConstructor
@Tag(name = "지역별 장애인 고용 현황", description = "지역별 장애인 고용 현황 API")
public class EmpDisRegionalStatusController {

    private final EmpDisRegionalStatusReadService readService;

    /**
     * 전체 조회
     */
    @GetMapping
    @Operation(summary = "전체 지역별 장애인 고용 현황 조회", description = "모든 지역의 장애인 고용 현황을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findAll() {
        List<EmpDisRegionalStatusDto> data = readService.findAll();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * ID로 조회
     */
    @GetMapping("/{id}")
    @Operation(summary = "ID로 지역별 장애인 고용 현황 조회", description = "특정 ID의 지역별 장애인 고용 현황을 조회합니다.")
    public ResponseEntity<ApiResDto<EmpDisRegionalStatusDto>> findById(
            @Parameter(description = "조회할 ID") @PathVariable Integer id) {
        return readService.findById(id)
                .map(data -> ResponseEntity.ok(ApiResDto.success(data)))
                .orElse(ResponseEntity.ok(ApiResDto.success(null)));
    }

    /**
     * 지역별 조회
     */
    @GetMapping("/region")
    @Operation(summary = "지역별 장애인 고용 현황 조회", description = "특정 지역의 장애인 고용 현황을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findByRegion(
            @Parameter(description = "지역명 (부분 검색 가능)") @RequestParam String region) {
        List<EmpDisRegionalStatusDto> data = readService.findByRegion(region);
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 고용률 범위별 조회
     */
    @GetMapping("/rate-range")
    @Operation(summary = "고용률 범위별 조회", description = "특정 고용률 범위의 지역별 장애인 고용 현황을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findBySevereRateBetween(
            @Parameter(description = "최소 고용률") @RequestParam BigDecimal minRate,
            @Parameter(description = "최대 고용률") @RequestParam BigDecimal maxRate) {
        List<EmpDisRegionalStatusDto> data = readService.findBySevereRateBetween(minRate, maxRate);
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 고용률 기준 상위 10개 조회
     */
    @GetMapping("/top10/rate")
    @Operation(summary = "고용률 기준 상위 10개 조회", description = "고용률이 높은 상위 10개 지역을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findTop10BySevereRate() {
        List<EmpDisRegionalStatusDto> data = readService.findTop10BySevereRate();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 사업체수 기준 상위 10개 조회
     */
    @GetMapping("/top10/company")
    @Operation(summary = "사업체수 기준 상위 10개 조회", description = "사업체수가 많은 상위 10개 지역을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findTop10ByCompanyCount() {
        List<EmpDisRegionalStatusDto> data = readService.findTop10ByCompanyCount();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 근로자수 기준 상위 10개 조회
     */
    @GetMapping("/top10/worker")
    @Operation(summary = "근로자수 기준 상위 10개 조회", description = "근로자수가 많은 상위 10개 지역을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findTop10ByWorkerCount() {
        List<EmpDisRegionalStatusDto> data = readService.findTop10ByWorkerCount();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 의무고용 인원 기준 상위 10개 조회
     */
    @GetMapping("/top10/obligation")
    @Operation(summary = "의무고용 인원 기준 상위 10개 조회", description = "의무고용 인원이 많은 상위 10개 지역을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findTop10ByObligationCount() {
        List<EmpDisRegionalStatusDto> data = readService.findTop10ByObligationCount();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 중증 장애인 고용인원 기준 상위 10개 조회
     */
    @GetMapping("/top10/severe")
    @Operation(summary = "중증 장애인 고용인원 기준 상위 10개 조회", description = "중증 장애인 고용인원이 많은 상위 10개 지역을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findTop10BySevereCount() {
        List<EmpDisRegionalStatusDto> data = readService.findTop10BySevereCount();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 평균 고용률 조회
     */
    @GetMapping("/average-rate")
    @Operation(summary = "평균 고용률 조회", description = "전체 지역의 평균 고용률을 조회합니다.")
    public ResponseEntity<ApiResDto<BigDecimal>> findAverageSevereRate() {
        BigDecimal data = readService.findAverageSevereRate();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 고용률이 평균 이상인 지역 조회
     */
    @GetMapping("/above-average")
    @Operation(summary = "평균 이상 고용률 지역 조회", description = "평균 고용률 이상인 지역들을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findBySevereRateAboveAverage() {
        List<EmpDisRegionalStatusDto> data = readService.findBySevereRateAboveAverage();
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 특정 고용률 이상인 지역 조회
     */
    @GetMapping("/rate-above")
    @Operation(summary = "특정 고용률 이상 지역 조회", description = "특정 고용률 이상인 지역들을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findBySevereRateGreaterThanEqual(
            @Parameter(description = "기준 고용률") @RequestParam BigDecimal rate) {
        List<EmpDisRegionalStatusDto> data = readService.findBySevereRateGreaterThanEqual(rate);
        return ResponseEntity.ok(ApiResDto.success(data));
    }

    /**
     * 특정 고용률 이하인 지역 조회
     */
    @GetMapping("/rate-below")
    @Operation(summary = "특정 고용률 이하 지역 조회", description = "특정 고용률 이하인 지역들을 조회합니다.")
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> findBySevereRateLessThanEqual(
            @Parameter(description = "기준 고용률") @RequestParam BigDecimal rate) {
        List<EmpDisRegionalStatusDto> data = readService.findBySevereRateLessThanEqual(rate);
        return ResponseEntity.ok(ApiResDto.success(data));
    }
} 