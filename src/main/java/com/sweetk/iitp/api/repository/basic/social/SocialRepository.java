package com.sweetk.iitp.api.repository.basic.social;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialRepository {

    //StatsDisSocParticFreq
    List<StatDataItemDB> findSocParticFreqLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findSocParticFreqByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisSocContactCntfreq
    List<StatDataItemDB> findSocContactCntfreqLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findSocContactCntfreqByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

}