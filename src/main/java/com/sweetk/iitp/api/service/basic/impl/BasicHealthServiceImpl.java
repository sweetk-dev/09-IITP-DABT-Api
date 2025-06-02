package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthDiseaseCostSubRepository;
import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthExrcBestAidRepository;
import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthMedicalUsageRepository;
import com.sweetk.iitp.api.repository.basic.health.StatsDisHlthSportExecTypeRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicHealthServiceImpl extends BasicBaseService {

    private final StatsDisHlthDiseaseCostSubRepository diseaseCostSubRepos;
    private final StatsDisHlthExrcBestAidRepository dxrcBestAidRepos;
    private final StatsDisHlthMedicalUsageRepository medicalUsageRepos;
    private final StatsDisHlthSportExecTypeRepository sportExecTypeRepos;
}
