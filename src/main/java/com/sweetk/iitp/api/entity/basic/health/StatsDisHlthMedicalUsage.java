package com.sweetk.iitp.api.entity.basic.health;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-건강 관리 현황-장애인 의료이용 현황
 */
@Entity
@Table(name = "stats_dis_hlth_medical_usage")
@NoArgsConstructor
public class StatsDisHlthMedicalUsage extends BaseStatsEntity {
}
