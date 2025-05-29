package com.sweetk.iitp.api.entity.basic.house;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-주로 지원해주는 사람의 유형
 */
@Entity
@Table(name = "stats_dis_life_maincarer")
@NoArgsConstructor
public class StatsDisLifeMaincarer extends BaseStatsEntity {
}
