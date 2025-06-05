package com.sweetk.iitp.api.entity.basic.aid;

import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * 기초-보조기기 사용 현황-장애인보조기기 필요여부
 */
@Entity
@Table(name = "stats_dis_aid_device_need")
@NoArgsConstructor
public class StatsDisAidDeviceNeedEntity extends BaseStatsEntity {
}