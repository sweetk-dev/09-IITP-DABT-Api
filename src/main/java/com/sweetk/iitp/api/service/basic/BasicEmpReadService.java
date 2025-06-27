package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.emp.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 기초-고용 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicEmpReadService extends AbstractBasicService {

    private final EmpRepository empRepository;
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;


    /*******************************
     * 장애인 근로자 고용현황
     *******************************/
    public StatInfo getEmpNatlInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatl();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.emp.natl.latest", description = "장애인 근로자 고용현황 데이터 조회")
    public StatDataRes getEmpNatlLatest(Integer from, Integer to) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatl();
        Integer formYear = getReqFromYear("EmpNatlLatest", from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getEmpNatlYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatl();
        checkReqStatYear("EmpNatlYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlByYear(srcDataInfo, targetYear);
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
     * 공공기관 장애인고용 현황
     *******************************/
    public StatInfo getEmpNatlPublicInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlPublic();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.emp.natl.public.latest", description = "공공기관 장애인고용 현황 데이터 조회")
    public StatDataRes getEmpNatlPublicLatest(Integer from, Integer to) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlPublic();
        Integer formYear = getReqFromYear("EmpNatlPublicLatest", from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlPublicLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getEmpNatlPublicYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlPublic();
        checkReqStatYear("EmpNatlPublicYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlPublicByYear(srcDataInfo, targetYear);
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
     * 민간기업 장애인고용 현황
     *******************************/
    public StatInfo getEmpNatlPrivateInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlPrivate();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.emp.natl.private.latest", description = "민간기업 장애인고용 현황 데이터 조회")
    public StatDataRes getEmpNatlPrivateLatest(Integer from, Integer to) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlPrivate();
        Integer formYear = getReqFromYear("EmpNatlPrivateLatest", from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlPrivateLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getEmpNatlPrivateYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlPrivate();
        checkReqStatYear("EmpNatlPrivateYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlPrivateByYear(srcDataInfo, targetYear);
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
     * 정부부문 장애인고용 현황
     *******************************/
    public StatInfo getEmpNatlGovOrgInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlGovOrg();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.emp.natl.govOrg.latest", description = "정부부문 장애인고용 현황 데이터 조회")
    public StatDataRes getEmpNatlGovOrgLatest(Integer from, Integer to) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlGovOrg();
        Integer formYear = getReqFromYear("EmpNatlGovOrgLatest", from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlGovOrgLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getEmpNatlGovOrgYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlGovOrg();
        checkReqStatYear("EmpNatlGovOrgYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlGovOrgByYear(srcDataInfo, targetYear);
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
     * 장애유형 및 장애정도별 장애인 근로자 고용현황
     *******************************/
    public StatInfo getEmpNatlDisTypeSevInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlDisTypeSev();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.emp.natl.disTypeSev.latest", description = "장애유형 및 장애정도별 장애인 근로자 고용현황 데이터 조회")
    public StatDataRes getEmpNatlDisTypeSevLatest(Integer from, Integer to) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlDisTypeSev();
        Integer formYear = getReqFromYear("EmpNatlDisTypeSevLatest", from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlDisTypeSevLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getEmpNatlDisTypeSevYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlDisTypeSev();
        checkReqStatYear("EmpNatlDisTypeSevYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlDisTypeSevByYear(srcDataInfo, targetYear);
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
     * 장애유형 및 산업별 장애인 근로자 고용현황
     *******************************/
    public StatInfo getEmpNatlDisTypeIndustInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlDisTypeIndust();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.emp.natl.disTypeIndust.latest", description = "장애유형 및 산업별 장애인 근로자 고용현황 데이터 조회")
    public StatDataRes getEmpNatlDisTypeIndustLatest(Integer from, Integer to) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlDisTypeIndust();
        Integer formYear = getReqFromYear("EmpNatlDisTypeIndustLatest", from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlDisTypeIndustLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getEmpNatlDisTypeIndustYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEmpNatlDisTypeIndust();
        checkReqStatYear("EmpNatlDisTypeIndustYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = empRepository.findEmpNatlDisTypeIndustByYear(srcDataInfo, targetYear);
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
