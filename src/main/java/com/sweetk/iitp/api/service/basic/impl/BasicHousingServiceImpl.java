package com.sweetk.iitp.api.service.basic.impl;

import com.sweetk.iitp.api.repository.basic.house.StatsDisLifeMaincarerRepository;
import com.sweetk.iitp.api.repository.basic.house.StatsDisLifePrimcarerRepository;
import com.sweetk.iitp.api.repository.basic.house.StatsDisLifeSuppFieldRepository;
import com.sweetk.iitp.api.repository.basic.house.StatsDisLifeSuppNeedLvlRepository;
import com.sweetk.iitp.api.repository.basic.house.StatsDisRegNatlByAgeTypeSevGenRepository;
import com.sweetk.iitp.api.repository.basic.house.StatsDisRegNatlByNewRepository;
import com.sweetk.iitp.api.repository.basic.house.StatsDisRegSidoByAgeTypeSevGenRepository;
import com.sweetk.iitp.api.service.basic.BasicBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BasicHousingServiceImpl extends BasicBaseService {

    private final StatsDisLifeMaincarerRepository LifeMaincarerRepos;
    private final StatsDisLifePrimcarerRepository LifePrimcarerRepos;
    private final StatsDisLifeSuppFieldRepository LifeSuppFieldRepos;
    private final StatsDisLifeSuppNeedLvlRepository LifeSuppNeedLvlRepos;
    private final StatsDisRegNatlByAgeTypeSevGenRepository RegNatlByAgeTypeSevGenRepos;
    private final StatsDisRegNatlByNewRepository RegNatlByNewRepos;
    private final StatsDisRegSidoByAgeTypeSevGenRepository RegSidoByAgeTypeSevGenRepos;

    public List<T> findAll(Integer Id, Date latestUpDate) {

    }

}
