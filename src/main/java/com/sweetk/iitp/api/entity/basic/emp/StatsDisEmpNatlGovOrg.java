package com.sweetk.iitp.api.entity.basic.emp;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-고용 현황-정부부문 장애인고용 현황
 */
@Entity
@Table(name = "stats_dis_emp_natl_gov_org")
@NoArgsConstructor
public class StatsDisEmpNatlGovOrg extends BaseStatsEntity {
}
