package com.sweetk.iitp.api.entity.basic.health;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-건강 관리 현황-운동 시 가장 도움이 되는 지원 사항
 */
@Entity
@Table(name = "stats_dis_hlth_exrc_best_aid")
@NoArgsConstructor
public class StatsDisHlthExrcBestAid extends BaseStatsEntity {
}
