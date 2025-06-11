package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlDisTypeIndustRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlDisTypeSevRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlGovOrgRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlPrivateRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlPublicRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기초-고용 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicEmpReadService extends BasicCommLogic {

    private final StatsDisEmpNatlDisTypeIndustRepository natlDisTypeIndustRepos;
    private final StatsDisEmpNatlDisTypeSevRepository natlDisTypeSevRepos;
    private final StatsDisEmpNatlGovOrgRepository natlGovOrgRepos;
    private final StatsDisEmpNatlPrivateRepository natlPrivateRepos;
    private final StatsDisEmpNatlPublicRepository natlPublicRepos;
    private final StatsDisEmpNatlRepository natlRepos;
}
