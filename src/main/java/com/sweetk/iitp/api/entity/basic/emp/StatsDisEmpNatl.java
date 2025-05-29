package com.sweetk.iitp.api.entity.basic.emp;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-고용 현황-장애인 근로자 고용현황
 */
@Entity
@Table(name = "stats_dis_emp_natl")
@NoArgsConstructor
public class StatsDisEmpNatl extends BaseStatsEntity {
}
