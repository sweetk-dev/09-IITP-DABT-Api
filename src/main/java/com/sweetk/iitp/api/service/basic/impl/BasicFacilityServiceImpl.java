package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.facility.StatsDisFcltyWelfareUsageRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicFacilityServiceImpl extends BasicBaseService {

    private final StatsDisFcltyWelfareUsageRepository welfareUsageRepos;
}
