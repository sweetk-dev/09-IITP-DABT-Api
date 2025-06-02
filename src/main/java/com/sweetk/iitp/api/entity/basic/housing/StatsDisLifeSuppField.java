package com.sweetk.iitp.api.entity.basic.housing;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-도움받는 분야
 */
@Entity
@Table(name = "stats_dis_life_supp_field")
@NoArgsConstructor
public class StatsDisLifeSuppField extends BaseStatsEntity {
}
