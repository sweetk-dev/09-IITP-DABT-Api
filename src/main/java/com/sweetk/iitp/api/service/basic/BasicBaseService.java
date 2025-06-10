package com.sweetk.iitp.api.service.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.basic.StatsKosisMetadataCodeRepository;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicBaseService {

    private final StatsSrcDataInfoRepository srcDataInfoRepos;
    private final StatsKosisMetadataCodeRepository statMetaCodeRepos;



    //Make StatDataItem
    public List<StatDataItem> makeStatDataItemList(StatsSrcDataInfoEntity srcDataInfo, List<StatDataItemDB> dataList ) {
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());

        // 2. 메타 코드 정보 조회
        //2.1 C1~C3
        List<StatMetaCodeDB> cMetaCodes = statMetaCodeRepos.findItemInfoByCObjId(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        //2.2 Item
        List<StatMetaCodeDB> iMetaCodes = statMetaCodeRepos.findItemInfoByIObjId(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 3. 메타 코드를 Map으로 변환 (초기 용량 지정)
        Map<String, StatMetaCodeDB> cMetaCodeMap = new HashMap<>(cMetaCodes.size());
        cMetaCodes.forEach(code -> cMetaCodeMap.put(code.getItemId(), code));
        Map<String, StatMetaCodeDB> iMetaCodeMap = new HashMap<>(iMetaCodes.size());
        iMetaCodes.forEach(code -> iMetaCodeMap.put(code.getItemId(), code));

        // 4. 데이터 변환 및 메타 코드 매핑 (병렬 처리)
        List<StatDataItem> items = dataList.parallelStream()
                .map(item -> {
                    StatMetaCodeDB c1Meta = cMetaCodeMap.get(item.getC1());
                    StatMetaCodeDB c2Meta = cMetaCodeMap.get(item.getC2());
                    StatMetaCodeDB c3Meta = cMetaCodeMap.get(item.getC3());
                    StatMetaCodeDB iMeta = iMetaCodeMap.get(item.getItmId());

                    return StatDataItem.builder()
                            .prdDe(item.getPrdDe())
                            .c1Nm(c1Meta != null ? c1Meta.getItemNm() : null)
                            .c2Nm(c2Meta != null ? c2Meta.getItemNm() : null)
                            .c3Nm(c3Meta != null ? c3Meta.getItemNm() : null)
                            .c1ObjNm(c1Meta != null ? c1Meta.getObjNm() : null)
                            .c2ObjNm(c2Meta != null ? c2Meta.getObjNm() : null)
                            .c3ObjNm(c3Meta != null ? c3Meta.getObjNm() : null)
                            .itmNm(iMeta != null ? iMeta.getItemNm() : null)
                            .unitNm(item.getUnitNm())
                            .data(item.getData())
                            .lstChnDe(item.getLstChnDe())
                            .build();
                })
                .collect(Collectors.toList());

        return items;
    }


    @Cacheable(value = "metaCodes", key = "#srcDataId + '_' + #statLatestChnDt")
    public List<StatMetaCodeDB> findMetaCodes(Integer srcDataId, LocalDate statLatestChnDt, Set<String> requiredCodes) {
        // Repository에 새로운 메서드 추가 필요
        return statMetaCodeRepos.findMetaCodesByIds(srcDataId, statLatestChnDt, requiredCodes);
    }

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

    //fromYear checking (Req Param)

    /**
     * fromYear checking and Default Set collectionEntDate
     * @param reqFnc
     * @param fromYear
     * @param startDate
     * @param entDate
     * @return
     */
    public Integer getReqFromYear(String reqFnc, Integer fromYear, Integer startDate, Integer entDate  ) {
        if (fromYear == null ) {
            log.debug("[{}] :: fromYear is null, DEF_SET_fromYear = {}", reqFnc, entDate);
            return entDate;
        }

        if( fromYear > entDate || (entDate- fromYear) > ApiConstants.Param.MAX_STAT_YEAR_PERIOD )
        {
            String detailMsg = String.format("from(%s) is invalid (MAx %s Year)", fromYear, ApiConstants.Param.MAX_STAT_YEAR_PERIOD);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
        }
        return fromYear;
    }

}
