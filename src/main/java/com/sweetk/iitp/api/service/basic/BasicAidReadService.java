package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceNeedRepository;
import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기초-보조기기 사용 현황 API Service
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicAidReadService extends BasicCommLogic {

    private final StatsDisAidDeviceNeedRepository deviceNeedRepos;
    private final StatsDisAidDeviceUsageRepository deviceUsageRepos;
}
