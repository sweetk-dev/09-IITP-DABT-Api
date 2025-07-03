package com.sweetk.iitp.api.repository.basic.emp;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.emp.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {
    private final StatsDisEmpNatlRepository empNatlRepos;
    private final StatsDisEmpNatlDisTypeIndustRepository empNatlDisTypeIndustRepos;
    private final StatsDisEmpNatlGovOrgRepository empNatlGovOrgRepos;
    private final StatsDisEmpNatlPrivateRepository empNatlPrivateRepos;
    private final StatsDisEmpNatlPublicRepository empNatlPublicRepos;
    private final StatsDisEmpNatlDisTypeSevRepository empNatlDisTypeSevRepos;


    //EmpNatl
    @Override
    public Integer getEmpNatlLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return empNatlRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //EmpNatlDisTypeIndust
    @Override
    public Integer getEmpNatlDisTypeIndustLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlDisTypeIndustRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlDisTypeIndustLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlDisTypeIndustRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlDisTypeIndustByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return empNatlDisTypeIndustRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //EmpNatlGovOrg
    @Override
    public Integer getEmpNatlGovOrgLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlGovOrgRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlGovOrgLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlGovOrgRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlGovOrgByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return empNatlGovOrgRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //EmpNatlPrivate
    @Override
    public Integer getEmpNatlPrivateLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlPrivateRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlPrivateLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlPrivateRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlPrivateByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return empNatlPrivateRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //EmpNatlPublic
    @Override
    public Integer getEmpNatlPublicLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlPublicRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlPublicLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlPublicRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlPublicByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return empNatlPublicRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //EmpNatlDisTypeSev
    @Override
    public Integer getEmpNatlDisTypeSevLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlDisTypeSevRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlDisTypeSevLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return empNatlDisTypeSevRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEmpNatlDisTypeSevByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return empNatlDisTypeSevRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 