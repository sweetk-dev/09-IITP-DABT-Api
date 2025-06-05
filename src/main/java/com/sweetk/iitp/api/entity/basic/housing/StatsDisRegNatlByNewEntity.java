package com.sweetk.iitp.api.entity.basic.housing;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황- 전국 장애인 신규등록 장애인 현황
 */
@Entity
@Table(name = "stats_dis_reg_natl_by_new")
@NoArgsConstructor
public class StatsDisRegNatlByNewEntity extends BaseStatsEntity {
}