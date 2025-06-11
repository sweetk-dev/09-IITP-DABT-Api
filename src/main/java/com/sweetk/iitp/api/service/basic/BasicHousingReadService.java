package com.sweetk.iitp.api.service.basic;


import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.house.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


/**
 * 기초-주거 자립 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicHousingReadService extends BasicCommLogic {
    private final StatsDisLifeMaincarerRepository LifeMaincarerRepos;
    private final StatsDisLifePrimcarerRepository LifePrimcarerRepos;
    private final StatsDisLifeSuppFieldRepository LifeSuppFieldRepos;
    private final StatsDisLifeSuppNeedLvlRepository LifeSuppNeedLvlRepos;
    private final StatsDisRegNatlByAgeTypeSevGenRepository RegNatlByAgeTypeSevGenRepos;
    private final StatsDisRegNatlByNewRepository statsDisRegNatlByNewRepos;
    private final StatsDisRegSidoByAgeTypeSevGenRepository RegSidoByAgeTypeSevGenRepos;

    private final BasicBaseReadService basicBaseReadService;


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
        StatsSrcDataInfoEntity srcDataInfo = basicBaseReadService.getSrcInoHousingRegNatlByNew();

        Integer fromStatYear = getReqFromYear("HousingRegNewLatest", fromYear, srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = statsDisRegNatlByNewRepos.findDataLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 2. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        //2.1 C1~C3
        List<StatMetaCodeDB> cMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        //2.2 Item
        List<StatMetaCodeDB> iMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);


        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList, cMetaCodes, iMetaCodes);

        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /**
     * 신규등록 장애인현황 - 특정연도 데이터 조회
     * @param targetYear
     * @return
     */
    public StatDataRes getHousingRegNewYear(Integer targetYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = basicBaseReadService.getSrcInoHousingRegNatlByNew();

        Integer statYear = getReqFromYear("HousingRegNewYear", targetYear, srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt() );


        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = statsDisRegNatlByNewRepos.findDataByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 2. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        //2.1 C1~C3
        List<StatMetaCodeDB> cMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        //2.2 Item
        List<StatMetaCodeDB> iMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);


        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList, cMetaCodes, iMetaCodes);

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
        StatsSrcDataInfoEntity srcDataInfo = basicBaseReadService.getSrcInoHousingRegNatlByNew();

        Integer fromStatYear = getReqFromYear("HousingRegNewLatest", fromYear, srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt() );

        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = RegNatlByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 2. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        //2.1 C1~C3
        List<StatMetaCodeDB> cMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        //2.2 Item
        List<StatMetaCodeDB> iMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);


        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList, cMetaCodes, iMetaCodes);

        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /**
     * 전국 연령별,장애등급별,성별 등록장애인수 - 특정연도 데이터 조회
     * @param targetYear
     * @return
     */
    public StatDataRes getHousingRegAgeSevGenYear(Integer targetYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = basicBaseReadService.getSrcInoHousingRegNatlByNew();


        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = RegNatlByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 2. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        //2.1 C1~C3
        List<StatMetaCodeDB> cMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        //2.2 Item
        List<StatMetaCodeDB> iMetaCodes = basicBaseReadService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);


        List<StatDataItem> items = makeStatDataItemList( srcDataInfo, dataList, cMetaCodes, iMetaCodes);

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
