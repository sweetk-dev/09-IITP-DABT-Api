package com.sweetk.iitp.api.entity.basic.social;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-사회망 현황-가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도
 */
@Entity
@Table(name = "stats_dis_soc_contact_cntfreq")
@NoArgsConstructor
public class StatsDisSocContactCntfreq extends BaseStatsEntity {
}