package com.sweetk.iitp.api.entity.basic.facility;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-편의 시설 제공 현황-사회복지시설 이용 현황
 */
@Entity
@Table(name = "stats_dis_fclty_welfare_usage")
@NoArgsConstructor
public class StatsDisFcltyWelfareUsage extends BaseStatsEntity {
}