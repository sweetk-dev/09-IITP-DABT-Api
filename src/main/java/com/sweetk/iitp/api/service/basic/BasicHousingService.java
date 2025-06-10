package com.sweetk.iitp.api.service.basic;


import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.StatsKosisMetadataCodeRepository;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import com.sweetk.iitp.api.repository.basic.house.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 기초-주거 자립 현황 API Service
 */
@Service
public class BasicHousingService extends BasicBaseService {
    private final StatsDisLifeMaincarerRepository LifeMaincarerRepos;
    private final StatsDisLifePrimcarerRepository LifePrimcarerRepos;
    private final StatsDisLifeSuppFieldRepository LifeSuppFieldRepos;
    private final StatsDisLifeSuppNeedLvlRepository LifeSuppNeedLvlRepos;
    private final StatsDisRegNatlByAgeTypeSevGenRepository RegNatlByAgeTypeSevGenRepos;
    private final StatsDisRegNatlByNewRepository statsDisRegNatlByNewRepos;
    private final StatsDisRegSidoByAgeTypeSevGenRepository RegSidoByAgeTypeSevGenRepos;
    private final StatsSrcDataInfoRepository statsSrcDataInfoRepos;
    private final StatsKosisMetadataCodeRepository statMetaCodeRepos;

    public BasicHousingService(
            StatsSrcDataInfoRepository srcDataInfoRepos,
            StatsKosisMetadataCodeRepository statMetaCodeRepos,
            StatsDisLifeMaincarerRepository lifeMaincarerRepos,
            StatsDisLifePrimcarerRepository lifePrimcarerRepos,
            StatsDisLifeSuppFieldRepository lifeSuppFieldRepos,
            StatsDisLifeSuppNeedLvlRepository lifeSuppNeedLvlRepos,
            StatsDisRegNatlByAgeTypeSevGenRepository regNatlByAgeTypeSevGenRepos,
            StatsDisRegNatlByNewRepository statsDisRegNatlByNewRepos,
            StatsDisRegSidoByAgeTypeSevGenRepository regSidoByAgeTypeSevGenRepos) {
        super(srcDataInfoRepos, statMetaCodeRepos);
        this.LifeMaincarerRepos = lifeMaincarerRepos;
        this.LifePrimcarerRepos = lifePrimcarerRepos;
        this.LifeSuppFieldRepos = lifeSuppFieldRepos;
        this.LifeSuppNeedLvlRepos = lifeSuppNeedLvlRepos;
        this.RegNatlByAgeTypeSevGenRepos = regNatlByAgeTypeSevGenRepos;
        this.statsDisRegNatlByNewRepos = statsDisRegNatlByNewRepos;
        this.RegSidoByAgeTypeSevGenRepos = regSidoByAgeTypeSevGenRepos;
        this.statsSrcDataInfoRepos = srcDataInfoRepos;
        this.statMetaCodeRepos = statMetaCodeRepos;
    }

    /*******************************
     * 신규등록 장애인현황
     *******************************/

    /**
     * 신규등록 장애인현황 - 최신 데이터 조회
     *
     * @param fromYear (옵션) 검색 시작 연도 (기본값: 1, 최대: 10년)
     * @return 주거 자립 현황-최신 데이터
     */
    @ConditionalTimed(value = "basic.housing.reg.new.latest", description = "주택 등록 신규 최신 데이터 조회")
    public StatDataRes getHousingRegNewLatest(Integer fromYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = getSrcInoHousingRegNatlByNew();

        Integer fromStatYear = getReqFromYear("HousingRegNewLatest", fromYear, srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = statsDisRegNatlByNewRepos.findDataLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

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

        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /**
     * 신규등록 장애인현황 - 특정연도 데이터 조회
     * @param targetYear
     * @return
     */
    public StatDataRes getHousingRegNewYear(Integer targetYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = getSrcInoHousingRegNatlByNew();

        Integer statYear = getReqFromYear("HousingRegNewYear", targetYear, srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt() );


        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = statsDisRegNatlByNewRepos.findDataByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList);

        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /*******************************
     * 전국 연령별,장애등급별,성별 등록장애인수
     *******************************/

    /**
     * 전국 연령별,장애등급별,성별 등록장애인수 - 최신 데이터 조회
     *
     * @param fromYear (옵션) 검색 시작 연도 (기본값: 1, 최대: 10년)
     * @return 주거 자립 현황-최신 데이터
     */
    @ConditionalTimed(value = "basic.housing.reg.ageSevGen.latest", description = "전국 연령별,장애등급별,성별 등록장애인수 최신 데이터 조회")
    public StatDataRes getHousingRegAgeSevGenLatest(Integer fromYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = getSrcInoHousingRegNatlByNew();

        Integer fromStatYear = getReqFromYear("HousingRegNewLatest", fromYear, srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt() );

        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = RegNatlByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList);

        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /**
     * 전국 연령별,장애등급별,성별 등록장애인수 - 특정연도 데이터 조회
     * @param targetYear
     * @return
     */
    public StatDataRes getHousingRegAgeSevGenYear(Integer targetYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = getSrcInoHousingRegNatlByNew();


        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = RegNatlByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList);

        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }



    /*******************************
     * 시도별,장애유형별,장애정도별,성별 등록장애인수
     *******************************/

    /*******************************
     * 일상생활 필요 지원 정도
     *******************************/

    /*******************************
     * 주로 지원해주는 사람의 유형
     *******************************/

    /*******************************
     * 일상생활 도와주는 사람(1순위)
     *******************************/

    /*******************************
     * 도움받는 분야
     *******************************/

}
