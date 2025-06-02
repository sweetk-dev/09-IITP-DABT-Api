package com.sweetk.iitp.api.entity.basic.housing;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-전국 연령별,장애등급별,성별 등록장애인수
 */
@Entity
@Table(name = "stats_dis_reg_natl_by_age_type_sev_gen")
@NoArgsConstructor
public class StatsDisRegNatlByAgeTypeSevGen extends BaseStatsEntity {
}
