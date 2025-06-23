package com.sweetk.iitp.api.entity.basic.housing;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-주거 자립 현황-시도별,장애유형별,장애정도별,성별 등록장애인수
 */
@Entity
@Table(name = "stats_dis_reg_sido_by_type_sev_gen")
@NoArgsConstructor
public class StatsDisRegSidoByTypeSevGenEntity extends BaseStatsEntity {
}