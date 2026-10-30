package com.sweetk.iitp.api.controller.v1.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.constant.basic.EmotionStatType;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.service.basic.BasicEmotionReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-정서 자립 현황 API Controller
 * <p>
 * KOSIS 장애인삶패널조사·장애인고용패널조사 등의 정서 영역 통계 20종과
 * 2023년 장애인실태조사 보고서 장애유형별 표 4종을 통계키({statKey})로 조회한다.
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_EMOTION)
@RequiredArgsConstructor
@Tag(name = "기초-정서 자립 현황 API V1", description = "기초-정서 자립 현황 OpenApi V1 — 우울·자아존중감·스트레스·삶의 만족도·사회적 지지·차별 경험 등 정서 영역 통계")
public class BasicEmotionController extends BasicBaseController {

    private static final String STAT_KEY_DESC =
            "통계키 — depression(우울수준), selfEsteem(자아존중감), suicideIdeation(자살 생각·이유·충동 행동 시도 경험), "
            + "stress(스트레스 인지 정도), resilience(회복탄력성), happiness(주관적 행복감), lifeSatisfaction(현재 삶의 만족도), "
            + "disabilityAcceptance(장애수용), disabilityExperience(장애와 관련한 경험), decisionMaker(의사결정 주체), "
            + "emotionalSupport(정서적 도움 및 지지), socialSupport(사회적 지지), counselPartner(어려움 상담 상대), "
            + "discrimination(차별 경험 및 대처방법) — 장애인삶패널조사 / "
            + "empSelfEsteem(자아존중 및 장애수용), empDiscrimination(장애로 인한 차별 경험 정도 및 사회경제적 지위), "
            + "empDiscrimRespect(일상생활 차별 경험 및 존중받는 수준), empSocialSupport(사회활동 참여·교류·사회적 지지체계), "
            + "empLifeSatisfaction(일상생활 만족도) — 장애인고용패널조사 / devDiscrimination(평소 장애로 인한 차별 경험 정도) — 발달장애인 일과 삶 실태조사 / "
            + "rptMentalHealth(정신건강 지표), rptLifeSatisfaction(생활 영역별 만족도·행복감), rptSupport(장애로 인한 어려움·도움받을 사람), "
            + "rptDiscrimination(차별 인식·장애인차별금지법 인지) — 2023년 장애인실태조사 보고서 장애유형별 표";

    private final BasicEmotionReadService emotionReadService;

    @GetMapping("/{statKey}/info")
    @Operation(
            summary = "정서 자립 현황 통계 정보 조회",
            description = "통계키에 해당하는 통계의 출처·수집 기간·최종 갱신일 등 정보 조회"
    )
    public ResponseEntity<ApiResDto<StatInfo>> getEmotionInfo(
            @Parameter(name = "statKey", description = STAT_KEY_DESC, required = true, example = "depression")
            @PathVariable
            EmotionStatType statKey,
            HttpServletRequest request) {

        return ResponseEntity.ok(ApiResDto.success(emotionReadService.getInfo(statKey)));
    }

    @GetMapping("/{statKey}/latest")
    @Operation(
            summary = "정서 자립 현황 통계 조회(최신)",
            description = "통계키에 해당하는 통계 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmotionLatest(
            @Parameter(name = "statKey", description = STAT_KEY_DESC, required = true, example = "depression")
            @PathVariable
            EmotionStatType statKey,
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2024")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

        return ResponseEntity.ok(ApiResDto.success(emotionReadService.getLatest(statKey, from, to)));
    }

    @GetMapping("/{statKey}/{statYear}")
    @Operation(
            summary = "해당 연도의 정서 자립 현황 통계 조회(연도)",
            description = "통계키에 해당하는 통계의 해당 연도 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmotionYear(
            @Parameter(name = "statKey", description = STAT_KEY_DESC, required = true, example = "depression")
            @PathVariable
            EmotionStatType statKey,
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        return ResponseEntity.ok(ApiResDto.success(emotionReadService.getYear(statKey, statYear)));
    }
}
