package com.sweetk.iitp.api.entity.basic.house;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-일상생활 도와주는 사람(1순위)
 */
@Entity
@Table(name = "stats_dis_life_primcarer")
@NoArgsConstructor
public class StatsDisLifePrimcarer extends BaseStatsEntity {
}
