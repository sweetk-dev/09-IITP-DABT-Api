package com.sweetk.iitp.api.entity.basic.housing;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-시군구별,장애정도별,성별 등록장애인수 (KOSIS DT_11761_N009)
 */
@Entity
@Table(name = "stats_dis_reg_sgg_by_sev_gen")
@NoArgsConstructor
public class StatsDisRegSggBySevGenEntity extends BaseStatsEntity {
}
