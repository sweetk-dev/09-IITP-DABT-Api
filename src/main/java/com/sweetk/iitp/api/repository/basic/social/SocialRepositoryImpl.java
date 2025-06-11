package com.sweetk.iitp.api.repository.basic.social;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SocialRepositoryImpl implements SocialRepository {
    private final StatsDisSocContactCntfreqRepository socContactCntfreqRepos;


    @Override
    public List<StatDataItemDB> findSocContactCntfreqLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return socContactCntfreqRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findSocContactCntfreqByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return socContactCntfreqRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 