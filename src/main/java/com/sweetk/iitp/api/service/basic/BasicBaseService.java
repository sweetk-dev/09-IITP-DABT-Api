package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicBaseService {

    private StatsSrcDataInfoRepository srcDataInfoRepos;

    //01.Housing
    public StatsSrcDataInfoEntity getSrcInoHousingRegNatlByNew (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_NEW);
    }

    public StatsSrcDataInfoEntity getSrcInoHousingRegNatlByAgeTypeSevGen (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_REG_NATL_BY_AGE_TYPE_SEV_GEN);
    }

    public StatsSrcDataInfoEntity getSrcInoHousingRegSidolByAgeTypeSevGen (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_REG_SIDO_BY_AGE_TYPE_SEV_GEN);
    }

    public StatsSrcDataInfoEntity getSrcInoHousingLifeSuppNeedLvl (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_SUPP_NEED_LVL);
    }

    public StatsSrcDataInfoEntity getSrcInoHousingLifeMaincarer (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_MAINCARER);
    }

    public StatsSrcDataInfoEntity getSrcInoHousingLifePrimcarer (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_PRIMCARER);
    }

    public StatsSrcDataInfoEntity getSrcInoHousingLifeSuppField (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_LIFE_SUPP_FIELD);
    }

    //02.Health
    public StatsSrcDataInfoEntity getSrcInoHealthMedicalUsage (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_MEDICAL_USAGE);
    }

    public StatsSrcDataInfoEntity getSrcInoHealthDiseaseCostSub (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_DISEASE_COST_SUB);
    }

    public StatsSrcDataInfoEntity getSrcInoHealthSportExecType (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_SPORT_EXEC_TYPE);
    }

    public StatsSrcDataInfoEntity getSrcInoHealthExrcBestAid (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_HLTH_EXRC_BEST_AID);
    }

    //03.Aid (Assistive devices)
    public StatsSrcDataInfoEntity getSrcInoAidDeviceUsage (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_AID_DEVICE_USAGE);
    }

    public StatsSrcDataInfoEntity getSrcInoAidDeviceNeed (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_AID_DEVICE_NEED);
    }


    //04.Education
    public StatsSrcDataInfoEntity getSrcInoEduVocaExec (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EDU_VOCA_EXEC);
    }

    public StatsSrcDataInfoEntity getSrcInoEduVocaExecWay (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EDU_VOCA_EXEC_WAY);
    }


    //05.Employment
    public StatsSrcDataInfoEntity getSrcInoEmpNatl (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL);
    }

    public StatsSrcDataInfoEntity getSrcInoEmpNatlPublic (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_PUBLIC);
    }

    public StatsSrcDataInfoEntity getSrcInoEmpNatlPrivate (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_PRIVATE);
    }

    public StatsSrcDataInfoEntity getSrcInoEmpNatlGovOrg (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_GOV_ORG);
    }

    public StatsSrcDataInfoEntity getSrcInoEmpNatlDisTypeSev (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_DIS_TYPE_SEV);
    }

    public StatsSrcDataInfoEntity getSrcInoEmpNatlDisTypeIndust (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_EMP_NATL_DIS_TYPE_INDUST);
    }


    //06.Social
    public StatsSrcDataInfoEntity getSrcInoSocialParticFreq (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_SOC_PARTIC_FREQ);
    }

    public StatsSrcDataInfoEntity getSrcInoSocialContactCntfreq (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_SOC_CONTACT_CNTFREQ);
    }


    //07.Facilities
    public StatsSrcDataInfoEntity getSrcInoFcltyWelfareUsage (){
        return srcDataInfoRepos.findByIntgTblId(SysConstants.Stats.TBL_FCLTY_WELFARE_USAGE);
    }

}
