package com.sweetk.iitp.api.constant.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 기초-정서 자립 현황 통계 종류.
 * <p>
 * 통계키(code) → KOSIS 통합 테이블(stats_dis_emo_*). 테이블 이름은 이 목록에서만 가져와 SQL 에 넣는다
 * (요청 값이 그대로 SQL 에 들어가지 않게 한다). 장애인삶패널조사(KOSIS 438) 14종,
 * 장애인고용패널조사·발달장애인 일과 삶 실태조사(KOSIS 383) 6종.
 */
public enum EmotionStatType {
    DEPRESSION("depression", "stats_dis_emo_depression", "DT_438001_AC004", "우울수준"),
    SELF_ESTEEM("selfEsteem", "stats_dis_emo_self_esteem", "DT_438001_AC005", "자아존중감"),
    SUICIDE_IDEATION("suicideIdeation", "stats_dis_emo_suicide_ideation", "DT_438001_AC006", "자살 생각, 이유, 충동 행동 시도 경험 및 횟수"),
    STRESS("stress", "stats_dis_emo_stress", "DT_438001_AD0171", "스트레스 인지 정도"),
    RESILIENCE("resilience", "stats_dis_emo_resilience", "DT_438001_AC0061", "회복탄력성"),
    HAPPINESS("happiness", "stats_dis_emo_happiness", "DT_438001_AC018", "주관적 행복감"),
    LIFE_SATISFACTION("lifeSatisfaction", "stats_dis_emo_life_satis", "DT_438001_AF035", "현재 삶의 만족도"),
    DISABILITY_ACCEPTANCE("disabilityAcceptance", "stats_dis_emo_dis_accept", "DT_438001_AC003", "장애수용"),
    DISABILITY_EXPERIENCE("disabilityExperience", "stats_dis_emo_dis_experience", "DT_438001_AC002", "장애와 관련한 경험"),
    DECISION_MAKER("decisionMaker", "stats_dis_emo_decision_maker", "DT_438001_AC007", "의사결정 주체(1순위)"),
    EMOTIONAL_SUPPORT("emotionalSupport", "stats_dis_emo_emo_support", "DT_438001_AC012", "정서적 도움 및 지지"),
    SOCIAL_SUPPORT("socialSupport", "stats_dis_emo_social_support", "DT_438001_AC0162", "사회적 지지"),
    COUNSEL_PARTNER("counselPartner", "stats_dis_emo_counsel_partner", "DT_438001_AC013", "어려움 상담 상대"),
    DISCRIMINATION("discrimination", "stats_dis_emo_discrim", "DT_438001_CE001", "차별 경험 및 대처방법"),
    EMP_SELF_ESTEEM("empSelfEsteem", "stats_dis_emo_emp_self_esteem", "DT_383003_P141", "자아존중 및 장애수용"),
    EMP_DISCRIMINATION("empDiscrimination", "stats_dis_emo_emp_discrim", "DT_383003_P161", "장애로 인한 차별 경험 정도 및 사회경제적 지위"),
    EMP_DISCRIM_RESPECT("empDiscrimRespect", "stats_dis_emo_emp_discrim_respect", "DT_383003_C03085", "일상생활에서 장애로 인한 차별 경험 및 존중 받는 수준"),
    EMP_SOCIAL_SUPPORT("empSocialSupport", "stats_dis_emo_emp_social_support", "DT_383003_C030831", "사회활동 참여 수준, 주변 사람 교류 정도, 사회적 지지체계"),
    EMP_LIFE_SATISFACTION("empLifeSatisfaction", "stats_dis_emo_emp_life_satis", "DT_383003_C03084", "일상생활 만족도"),
    DEV_DISCRIMINATION("devDiscrimination", "stats_dis_emo_dev_discrim", "DT_383005_G0012", "평소 장애로 인한 차별 경험 정도");

    private final String code;
    private final String intgTblId;
    private final String kosisTblId;
    private final String displayName;

    EmotionStatType(String code, String intgTblId, String kosisTblId, String displayName) {
        this.code = code;
        this.intgTblId = intgTblId;
        this.kosisTblId = kosisTblId;
        this.displayName = displayName;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getIntgTblId() {
        return intgTblId;
    }

    public String getKosisTblId() {
        return kosisTblId;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static EmotionStatType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (EmotionStatType t : values()) {
            if (t.code.equalsIgnoreCase(value.trim()) || t.name().equalsIgnoreCase(value.trim())) {
                return t;
            }
        }
        String allowed = Arrays.stream(values()).map(t -> t.code).collect(Collectors.joining(", "));
        throw new IllegalArgumentException("Invalid emotion stat: " + value + ". Allowed values are: " + allowed);
    }
}
