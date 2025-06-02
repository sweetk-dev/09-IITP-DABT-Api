package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceNeedRepository;
import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceUsageRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAidServiceImpl extends BasicBaseService {

    private final StatsDisAidDeviceNeedRepository deviceNeedRepos;
    private final StatsDisAidDeviceUsageRepository deviceUsageRepos;
}
