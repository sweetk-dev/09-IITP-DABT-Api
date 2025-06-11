package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataSourceService {
    private final StatsSrcDataInfoRepository srcDataInfoRepos;

    //01.Housing
    @Cacheable(
        value = "housingDataSources", 
        key = "'regNatlByNew_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingRegNatlByNew() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_NEW);
    }

    @Cacheable(
        value = "housingDataSources", 
        key = "'regNatlByAgeTypeSevGen_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingRegNatlByAgeTypeSevGen() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_AGE_TYPE_SEV_GEN);
    }

    @Cacheable(
        value = "housingDataSources", 
        key = "'regSidoByAgeTypeSevGen_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingRegSidoByAgeTypeSevGen() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_REG_SIDO_BY_AGE_TYPE_SEV_GEN);
    }

    @Cacheable(
        value = "housingDataSources", 
        key = "'lifeSuppNeedLvl_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingLifeSuppNeedLvl() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_SUPP_NEED_LVL);
    }

    @Cacheable(
        value = "housingDataSources", 
        key = "'lifeMaincarer_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingLifeMaincarer() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_MAINCARER);
    }

    @Cacheable(
        value = "housingDataSources", 
        key = "'lifePrimcarer_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingLifePrimcarer() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_PRIMCARER);
    }

    @Cacheable(
        value = "housingDataSources", 
        key = "'lifeSuppField_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHousingLifeSuppField() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_SUPP_FIELD);
    }

    //02.Health
    @Cacheable(
        value = "healthDataSources", 
        key = "'medicalUsage_' + #result.statLatestChnDt", 
        unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHealthMedicalUsage() {
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_MEDICAL_USAGE);
    }

    @Cacheable(
            value = "healthDataSources",
            key = "'diseaseCostSub_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHealthDiseaseCostSub (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_DISEASE_COST_SUB);
    }

    @Cacheable(
            value = "healthDataSources",
            key = "'sportExecType_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHealthSportExecType (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_SPORT_EXEC_TYPE);
    }

    @Cacheable(
            value = "healthDataSources",
            key = "'exrcBestAid_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getHealthExrcBestAid (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_EXRC_BEST_AID);
    }

    //03.Aid (Assistive devices)
    @Cacheable(
            value = "AidDataSources",
            key = "'deviceUsage_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getAidDeviceUsage (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_AID_DEVICE_USAGE);
    }

    @Cacheable(
            value = "AidDataSources",
            key = "'deviceNeed_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getAidDeviceNeed (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_AID_DEVICE_NEED);
    }


    //04.Education
    @Cacheable(
            value = "EduDataSources",
            key = "'vocaExec_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEduVocaExec (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EDU_VOCA_EXEC);
    }

    @Cacheable(
            value = "EduDataSources",
            key = "'vocaExecWay_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEduVocaExecWay (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EDU_VOCA_EXEC_WAY);
    }


    //05.Employment
    @Cacheable(
            value = "EmpDataSources",
            key = "'empNatl_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEmpNatl (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL);
    }

    @Cacheable(
            value = "EmpDataSources",
            key = "'empNatlPublic_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEmpNatlPublic (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_PUBLIC);
    }

    @Cacheable(
            value = "EmpDataSources",
            key = "'empNatlPrivate_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEmpNatlPrivate (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_PRIVATE);
    }

    @Cacheable(
            value = "EmpDataSources",
            key = "'empNatlGovOrg_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEmpNatlGovOrg (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_GOV_ORG);
    }

    @Cacheable(
            value = "EmpDataSources",
            key = "'empNatlDisTypeSev_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEmpNatlDisTypeSev (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_DIS_TYPE_SEV);
    }

    @Cacheable(
            value = "EmpDataSources",
            key = "'empNatlDisTypeIndust_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getEmpNatlDisTypeIndust (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_DIS_TYPE_INDUST);
    }


    //06.Social
    @Cacheable(
            value = "SocialDataSources",
            key = "'particFreq_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getSocialParticFreq (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_SOC_PARTIC_FREQ);
    }

    @Cacheable(
            value = "SocialDataSources",
            key = "'contactCntfreq_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getSocialContactCntfreq (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_SOC_CONTACT_CNTFREQ);
    }


    //07.Facilities
    @Cacheable(
            value = "FcltyDataSources",
            key = "'welfareUsage_' + #result.statLatestChnDt",
            unless = "#result == null"
    )
    public StatsSrcDataInfoEntity getFcltyWelfareUsage (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_FCLTY_WELFARE_USAGE);
    }
} 