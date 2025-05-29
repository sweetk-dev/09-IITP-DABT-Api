package com.sweetk.iitp.api.entity.basic.health;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-건강 관리 현황-장애인 생활체육 실행 유형
 */
@Entity
@Table(name = "stats_dis_hlth_sport_exec_type")
@NoArgsConstructor
public class StatsDisHlthSportExecType extends BaseStatsEntity {
}
