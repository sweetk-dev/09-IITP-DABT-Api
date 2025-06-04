package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.facility.StatsDisFcltyWelfareUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 기초-편의 시설 제공 현황 API Service
 */
@Service
@RequiredArgsConstructor
public class BasicFacilityService extends BasicBaseService {

    private final StatsDisFcltyWelfareUsageRepository welfareUsageRepos;
}
