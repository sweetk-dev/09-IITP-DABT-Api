package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthDiseaseCostSubRepository;
import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthExrcBestAidRepository;
import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthMedicalUsageRepository;
import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthSportExecTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기초-건강 관리 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicHealthReadService extends AbstractBasicService {

    private final StatsDisHlthDiseaseCostSubRepository diseaseCostSubRepos;
    private final StatsDisHlthExrcBestAidRepository dxrcBestAidRepos;
    private final StatsDisHlthMedicalUsageRepository medicalUsageRepos;
    private final StatsDisHlthSportExecTypeRepository sportExecTypeRepos;
}
