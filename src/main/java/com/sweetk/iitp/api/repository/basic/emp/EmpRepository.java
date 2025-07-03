package com.sweetk.iitp.api.repository.basic.emp;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository {
    // StatsDisEmpNatlDisTypeIndust
    Integer getEmpNatlLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    //EmpNatlDisTypeIndust
    Integer getEmpNatlDisTypeIndustLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlDisTypeIndustLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlDisTypeIndustByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlGovOrg
    Integer getEmpNatlGovOrgLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlGovOrgLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlGovOrgByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlPrivate
    Integer getEmpNatlPrivateLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlPrivateLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlPrivateByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlPublic
    Integer getEmpNatlPublicLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlPublicLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlPublicByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlDisTypeSev
    Integer getEmpNatlDisTypeSevLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlDisTypeSevLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEmpNatlDisTypeSevByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 