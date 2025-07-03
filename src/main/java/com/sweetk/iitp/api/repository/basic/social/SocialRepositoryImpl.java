package com.sweetk.iitp.api.repository.basic.social;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.social.impl.StatsDisSocContactCntfreqRepository;
import com.sweetk.iitp.api.repository.basic.social.impl.StatsDisSocParticFreqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SocialRepositoryImpl implements SocialRepository {
    private final StatsDisSocParticFreqRepository socParticFreqRepos;
    private final StatsDisSocContactCntfreqRepository socContactCntfreqRepos;


    //SocParticFreq
    @Override
    public Integer getSocParticFreqLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return socParticFreqRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findSocParticFreqLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return socParticFreqRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findSocParticFreqByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return socParticFreqRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //SocContactCntfreq
    @Override
    public Integer getSocContactCntfreqLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return socContactCntfreqRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findSocContactCntfreqLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return socContactCntfreqRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findSocContactCntfreqByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return socContactCntfreqRepos.findDataByYear(srcDataInfo, targetYear);
    }



} 