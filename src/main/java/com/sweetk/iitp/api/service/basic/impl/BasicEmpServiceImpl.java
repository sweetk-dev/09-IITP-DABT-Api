package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlDisTypeIndustRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlDisTypeSevRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlGovOrgRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlPrivateRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlPublicRepository;
import com.sweetk.iitp.api.repository.basic.emp.StatsDisEmpNatlRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicEmpServiceImpl extends BasicBaseService {

    private final StatsDisEmpNatlDisTypeIndustRepository natlDisTypeIndustRepos;
    private final StatsDisEmpNatlDisTypeSevRepository natlDisTypeSevRepos;
    private final StatsDisEmpNatlGovOrgRepository natlGovOrgRepos;
    private final StatsDisEmpNatlPrivateRepository natlPrivateRepos;
    private final StatsDisEmpNatlPublicRepository natlPublicRepos;
    private final StatsDisEmpNatlRepository natlRepos;
}
