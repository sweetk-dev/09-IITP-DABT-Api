package com.sweetk.iitp.api.entity.basic.edu;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-진로 교육 현황-장애인 진로 및 직업교육 실시 여부
 */
@Entity
@Table(name = "stats_dis_edu_voca_exec")
@NoArgsConstructor
public class StatsDisEduVocaExecEntity extends BaseStatsEntity {
}