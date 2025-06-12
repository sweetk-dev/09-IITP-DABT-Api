package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.health.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 기초-건강 관리 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicHealthReadService extends AbstractBasicService {

    private final HealthRepository healthRepository;
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;

    /*******************************
     * 장애인 의료이용 현황
     *******************************/
    public StatInfo getHealthMedicalUsageInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthMedicalUsage();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.health.medicalUsg.latest", description = "장애인 의료이용 현황 데이터 조회")
    public StatDataRes getHealthMedicalUsageLatest(Integer fromYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthMedicalUsage();
        Integer fromStatYear = getReqFromYear("HealthMedicalUsageLatest", fromYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthMedicalUsageLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHealthMedicalUsageYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthMedicalUsage();
        Integer statYear = getReqFromYear("HealthMedicalUsageYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthMedicalUsageByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /*******************************
     * 장애인 장애유형별 다빈도질환별 진료비현황: 소분류
     *******************************/
    public StatInfo getHealthDiseaseCostSubInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthDiseaseCostSub();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.health.disCostSub.latest", description = "장애인 장애유형별 다빈도질환별 진료비현황: 소분류 데이터 조회")
    public StatDataRes getHealthDiseaseCostSubLatest(Integer fromYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthDiseaseCostSub();
        Integer fromStatYear = getReqFromYear("HealthDiseaseCostSubLatest", fromYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthDiseaseCostSubLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHealthDiseaseCostSubYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthDiseaseCostSub();
        Integer statYear = getReqFromYear("HealthDiseaseCostSubYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthDiseaseCostSubByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /*******************************
     * 장애인 생활체육 실행 유형
     *******************************/
    public StatInfo getHealthSportExecTypeInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthSportExecType();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.health.sportExecType.latest", description = "장애인 생활체육 실행 유형 데이터 조회")
    public StatDataRes getHealthSportExecTypeLatest(Integer fromYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthSportExecType();
        Integer fromStatYear = getReqFromYear("HealthSportExecTypeLatest", fromYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthSportExecTypeLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHealthSportExecTypeYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthSportExecType();
        Integer statYear = getReqFromYear("HealthSportExecTypeYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthSportExecTypeByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }


    /*******************************
     * 운동 시 가장 도움이 되는 지원 사항
     *******************************/
    public StatInfo getHealthExrcBestAidInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthExrcBestAid();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.health.exrcBestAid.latest", description = "운동 시 가장 도움이 되는 지원 사항 데이터 조회")
    public StatDataRes getHealthExrcBestAidLatest(Integer fromYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthExrcBestAid();
        Integer fromStatYear = getReqFromYear("HealthExrcBestAidLatest", fromYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthExrcBestAidLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHealthExrcBestAidYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHealthExrcBestAid();
        Integer statYear = getReqFromYear("HealthExrcBestAidYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = healthRepository.findHlthExrcBestAidByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }
}
