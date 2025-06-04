package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceNeedRepository;
import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 기초-보조기기 사용 현황 API Service
 */

@Service
@RequiredArgsConstructor
public class BasicAidService extends BasicBaseService {

    private final StatsDisAidDeviceNeedRepository deviceNeedRepos;
    private final StatsDisAidDeviceUsageRepository deviceUsageRepos;
}
