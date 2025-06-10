package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.StatsKosisMetadataCodeRepository;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import com.sweetk.iitp.api.repository.basic.house.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 기초-주거 자립 현황 API Service
 */
@Service
@RequiredArgsConstructor
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

    /*******************************
     * 신규등록 장애인현황
     *******************************/

    /**
     * 기초-주거 자립 현황-최신 데이터 조회
     *
     * @param fromYear (옵션) 검색 시작 연도 (기본값: 1, 최대: 10년)
     * @return 주거 자립 현황-최신 데이터
     */
    @ConditionalTimed(value = "basic.housing.reg.new.latest", description = "주택 등록 신규 최신 데이터 조회")
    public StatDataRes getHousingRegNewLatest(Integer fromYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = getSrcInoHousingRegNatlByNew();

        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = statsDisRegNatlByNewRepos.findLatestRegNewData(srcDataInfo, fromYear);

        List<StatDataItem> items = null;

        /*
        // 2. 메타 코드 정보 조회
        Map<String, String> metaCodes = statMetaCodeRepos.findByClassIdIn(List.of("C1", "C2", "C3", "ITM"))
            .stream()
            .collect(Collectors.toMap(
                code -> code.getClassId() + "_" + code.getCode(),
                code -> code.getName()
            ));

        // 3. 데이터 변환 및 메타 코드 매핑
        List<StatDataItem> items = dataList.stream()
            .map(item -> StatDataItem.builder()
                .prdDe(item.getPrdDe())
                .c1Nm(metaCodes.get("C1_" + item.getC1()))
                .c2Nm(metaCodes.get("C2_" + item.getC2()))
                .c3Nm(metaCodes.get("C3_" + item.getC3()))
                .c1ObjNm(item.getC1ObjNm())
                .c2ObjNm(item.getC2ObjNm())
                .c3ObjNm(item.getC3ObjNm())
                .itmNm(metaCodes.get("ITM_" + item.getItmId()))
                .unitNm(item.getUnitNm())
                .data(item.getData())
                .lstChnDe(item.getLstChnDe())
                .build())
            .collect(Collectors.toList());
            */
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingRegNewYear(Integer targetYear) {
        //get Src data Info
        StatsSrcDataInfoEntity srcDataInfo = getSrcInoHousingRegNatlByNew();

        List<StatDataItem> items = null;
        /*
        // 1. 기본 데이터 조회
        List<StatDataItemDB> dataList = statsDisRegNatlByNewRepos.findLatestRegNewData(srcDataInfo, targetYear);

        // 2. 메타 코드 정보 조회
        Map<String, String> metaCodes = statMetaCodeRepos.findByClassIdIn(List.of("C1", "C2", "C3", "ITM"))
                .stream()
                .collect(Collectors.toMap(
                        code -> code.getClassId() + "_" + code.getCode(),
                        code -> code.getName()
                ));

        // 3. 데이터 변환 및 메타 코드 매핑
        List<StatDataItem> items = dataList.stream()
                .map(item -> StatDataItem.builder()
                        .prdDe(item.getPrdDe())
                        .c1(item.getC1())
                        .c2(item.getC2())
                        .c3(item.getC3())
                        .c1Nm(metaCodes.get("C1_" + item.getC1()))
                        .c2Nm(metaCodes.get("C2_" + item.getC2()))
                        .c3Nm(metaCodes.get("C3_" + item.getC3()))
                        .c1ObjNm(item.getC1ObjNm())
                        .c2ObjNm(item.getC2ObjNm())
                        .c3ObjNm(item.getC3ObjNm())
                        .itmId(item.getItmId())
                        .itmNm(metaCodes.get("ITM_" + item.getItmId()))
                        .unitNm(item.getUnitNm())
                        .data(item.getData())
                        .lstChnDe(item.getLstChnDe())
                        .build())
                .collect(Collectors.toList());


         */
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /*******************************
     * 전국 연령별,장애등급별,성별 등록장애인수
     *******************************/

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
