package com.sweetk.iitp.api.entity.basic.social;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-사회망 현황-장애인의 사회 참여
 */
@Entity
@Table(name = "stats_dis_soc_partic_freq")
@NoArgsConstructor
public class StatsDisSocParticFreqEntity extends BaseStatsEntity {
}