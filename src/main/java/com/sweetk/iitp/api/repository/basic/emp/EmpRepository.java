package com.sweetk.iitp.api.repository.basic.emp;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository {
    // StatsDisEmpNatlDisTypeIndust

    List<StatDataItemDB> findEmpNatlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findEmpNatlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);


    List<StatDataItemDB> findEmpNatlDisTypeIndustLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findEmpNatlDisTypeIndustByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlGovOrg
    List<StatDataItemDB> findEmpNatlGovOrgLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findEmpNatlGovOrgByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlPrivate
    List<StatDataItemDB> findEmpNatlPrivateLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findEmpNatlPrivateByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlPublic
    List<StatDataItemDB> findEmpNatlPublicLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findEmpNatlPublicByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // StatsDisEmpNatlDisTypeSev
    List<StatDataItemDB> findEmpNatlDisTypeSevLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findEmpNatlDisTypeSevByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 