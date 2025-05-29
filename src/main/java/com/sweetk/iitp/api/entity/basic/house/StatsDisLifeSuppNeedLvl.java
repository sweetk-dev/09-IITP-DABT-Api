package com.sweetk.iitp.api.entity.basic.house;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-일상생활 필요 지원 정도
 */
@Entity
@Table(name = "stats_dis_life_supp_need_lvl")
@NoArgsConstructor
public class StatsDisLifeSuppNeedLvl extends BaseStatsEntity {
}
