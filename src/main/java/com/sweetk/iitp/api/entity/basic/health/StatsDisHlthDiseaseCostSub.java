package com.sweetk.iitp.api.entity.basic.health;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-건강 관리 현황-장애인 장애유형별 다빈도질환별 진료비현황: 소분류 (maj(대)/mid(중)/sub(소))
 */
@Entity
@Table(name = "stats_dis_hlth_disease_cost_sub")
@NoArgsConstructor
public class StatsDisHlthDiseaseCostSub extends BaseStatsEntity {
}
