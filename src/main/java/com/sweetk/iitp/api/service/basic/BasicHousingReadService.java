package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.housing.HousingRepository;
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
public class BasicHousingReadService extends AbstractBasicService {

    private final HousingRepository housingRepository;
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;

    /*******************************
     * 신규등록 장애인현황
     *******************************/
    public StatInfo getHousingRegNewInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegNatlByNew();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.reg.new.latest", description = "주택 등록 신규 최신 데이터 조회")
    public StatDataRes getHousingRegNewLatest(Integer fromYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegNatlByNew();
        Integer fromStatYear = getReqFromYear("HousingRegNewLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = housingRepository.findRegNatlByNewLatest(srcDataInfo, fromStatYear);
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

    public StatDataRes getHousingRegNewYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegNatlByNew();
        Integer statYear = getReqFromYear("HousingRegNewYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = housingRepository.findRegNatlByNewByYear(srcDataInfo, statYear);
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
     * 전국 연령별,장애등급별,성별 등록장애인수
     *******************************/
    public StatInfo getHousingRegAgeSevGenInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegNatlByAgeTypeSevGen();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.reg.ageSevGen.latest", description = "전국 연령별,장애등급별,성별 등록장애인수 최신 데이터 조회")
    public StatDataRes getHousingRegAgeSevGenLatest(Integer fromYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegNatlByAgeTypeSevGen();
        Integer fromStatYear = getReqFromYear("HousingRegAgeSevGenLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findRegNatlByAgeTypeSevGenLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingRegAgeSevGenYear(Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegNatlByAgeTypeSevGen();
        Integer statYear = getReqFromYear("HousingRegAgeSevGenYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findRegNatlByAgeTypeSevGenByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    /*******************************
     * 시도별,장애유형별,장애정도별,성별 등록장애인수
     *******************************/
    public StatInfo getHousingRegSidoSevGenInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegSidoByTypeSevGen();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.reg.sidoSevGen.latest", description = "시도별,장애유형별,장애정도별,성별 등록장애인수 최신 데이터 조회")
    public StatDataRes getHousingRegSidoSevGenLatest(Integer fromYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegSidoByTypeSevGen();
        Integer fromStatYear = getReqFromYear("HousingRegSidoAgeSevGenLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findRegSidoByTypeSevGenLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingRegSidoSevGenYear(Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingRegSidoByTypeSevGen();
        Integer statYear = getReqFromYear("HousingRegSidoAgeSevGenYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findRegSidoByTypeSevGenByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    /*******************************
     * 일상생활 필요 지원 정도
     *******************************/
    public StatInfo getHousingLifeSuppNeedLvlInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeSuppNeedLvl();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.life.suppNeedLvl.latest", description = "일상생활 필요 지원 정도 최신 데이터 조회")
    public StatDataRes getHousingLifeSuppNeedLvlLatest(Integer fromYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeSuppNeedLvl();
        Integer fromStatYear = getReqFromYear("HousingLifeSuppNeedLvlLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifeSuppNeedLvlLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingLifeSuppNeedLvlYear(Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeSuppNeedLvl();
        Integer statYear = getReqFromYear("HousingLifeSuppNeedLvlYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifeSuppNeedLvlByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    /*******************************
     * 주로 지원해주는 사람의 유형
     *******************************/
    public StatInfo getHousingLifeMaincarerInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeMaincarer();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.life.maincarer.latest", description = "주로 지원해주는 사람의 유형 최신 데이터 조회")
    public StatDataRes getHousingLifeMaincarerLatest(Integer fromYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeMaincarer();
        Integer fromStatYear = getReqFromYear("HousingLifeMaincarerLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifeMaincarerLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingLifeMaincarerYear(Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeMaincarer();
        Integer statYear = getReqFromYear("HousingLifeMaincarerYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifeMaincarerByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    /*******************************
     * 일상생활 도와주는 사람(1순위)
     *******************************/
    public StatInfo getHousingLifePrimcarerInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifePrimcarer();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.life.primcarer.latest", description = "일상생활 도와주는 사람(1순위) 최신 데이터 조회")
    public StatDataRes getHousingLifePrimcarerLatest(Integer fromYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifePrimcarer();
        Integer fromStatYear = getReqFromYear("HousingLifePrimcarerLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifePrimcarerLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingLifePrimcarerYear(Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifePrimcarer();
        Integer statYear = getReqFromYear("HousingLifePrimcarerYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifePrimcarerByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    /*******************************
     * 도움받는 분야
     *******************************/
    public StatInfo getHousingLifeSuppFieldInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeSuppField();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.housing.life.suppField.latest", description = "도움받는 분야 최신 데이터 조회")
    public StatDataRes getHousingLifeSuppFieldLatest(Integer fromYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeSuppField();
        Integer fromStatYear = getReqFromYear("HousingLifeSuppFieldLatest", fromYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifeSuppFieldLatest(srcDataInfo, fromStatYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getHousingLifeSuppFieldYear(Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getHousingLifeSuppField();
        Integer statYear = getReqFromYear("HousingLifeSuppFieldYear", targetYear, 
            srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = housingRepository.findLifeSuppFieldByYear(srcDataInfo, statYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }
}
