package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.facility.StatsDisFcltyWelfareUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기초-편의 시설 제공 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicFacilityReadService extends AbstractBasicService {

    private final StatsDisFcltyWelfareUsageRepository welfareUsageRepos;
}
