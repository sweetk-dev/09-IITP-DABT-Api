package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataSourceService {
    private final StatsSrcDataInfoRepository srcDataInfoRepos;

    //01.Housing
    public StatsSrcDataInfoEntity getHousingRegNatlByNew() {
        //StatsSrcDataInfoEntity result = srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_NEW);
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_NEW);
    }

    public StatsSrcDataInfoEntity getHousingRegNatlByAgeTypeSevGen() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_AGE_TYPE_SEV_GEN);
    }

    public StatsSrcDataInfoEntity getHousingRegSidoByTypeSevGen() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_REG_SIDO_BY_TYPE_SEV_GEN);
    }

    public StatsSrcDataInfoEntity getHousingLifeSuppNeedLvl() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_LIFE_SUPP_NEED_LVL);
    }

    public StatsSrcDataInfoEntity getHousingLifeMaincarer() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_LIFE_MAINCARER);
    }

    public StatsSrcDataInfoEntity getHousingLifePrimcarer() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_LIFE_PRIMCARER);
    }

    public StatsSrcDataInfoEntity getHousingLifeSuppField() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_LIFE_SUPP_FIELD);
    }

    //02.Health
    public StatsSrcDataInfoEntity getHealthMedicalUsage() {
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_HLTH_MEDICAL_USAGE);
    }

    public StatsSrcDataInfoEntity getHealthDiseaseCostSub (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_HLTH_DISEASE_COST_SUB);
    }

    public StatsSrcDataInfoEntity getHealthSportExecType (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_HLTH_SPORT_EXEC_TYPE);
    }

    public StatsSrcDataInfoEntity getHealthExrcBestAid (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_HLTH_EXRC_BEST_AID);
    }

    //03.Aid (Assistive devices)
    public StatsSrcDataInfoEntity getAidDeviceUsage (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_AID_DEVICE_USAGE);
    }

    public StatsSrcDataInfoEntity getAidDeviceNeed (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_AID_DEVICE_NEED);
    }


    //04.Education
    public StatsSrcDataInfoEntity getEduVocaExec (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EDU_VOCA_EXEC);
    }

    public StatsSrcDataInfoEntity getEduVocaExecWay (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EDU_VOCA_EXEC_WAY);
    }


    //05.Employment
    public StatsSrcDataInfoEntity getEmpNatl (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EMP_NATL);
    }

    public StatsSrcDataInfoEntity getEmpNatlPublic (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_PUBLIC);
    }

    public StatsSrcDataInfoEntity getEmpNatlPrivate (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_PRIVATE);
    }

    public StatsSrcDataInfoEntity getEmpNatlGovOrg (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_GOV_ORG);
    }

    public StatsSrcDataInfoEntity getEmpNatlDisTypeSev (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_DIS_TYPE_SEV);
    }

    public StatsSrcDataInfoEntity getEmpNatlDisTypeIndust (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_DIS_TYPE_INDUST);
    }


    //06.Social
    public StatsSrcDataInfoEntity getSocialParticFreq (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_SOC_PARTIC_FREQ);
    }

    public StatsSrcDataInfoEntity getSocialContactCntfreq (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_SOC_CONTACT_CNTFREQ);
    }


    //07.Facilities
    public StatsSrcDataInfoEntity getFcltyWelfareUsage (){
        return srcDataInfoRepos.findActiveByIntgTblId(SysConstants.Stats.TBL_FCLTY_WELFARE_USAGE);
    }
} 