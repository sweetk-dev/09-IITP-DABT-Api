package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.facility.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 기초-편의 시설 제공 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicFacilityReadService extends BasicService {

    private final FacilityRepository facilityRepository;
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;

    /*******************************
     * 사회복지시설 이용 현황
     *******************************/
    public StatInfo getFcltyWelfareUsageInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getFcltyWelfareUsage();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }


    @ConditionalTimed(value = "basic.facility.welfareUsage.latest", description = "사회복지시설 이용 현황 데이터 조회")
    public StatDataRes getFcltyWelfareUsageLatest(Integer from, Integer to) {
        String fnc = "FcltyWelfareUsageLatest";

        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getFcltyWelfareUsage();
        Integer formYear = getReqFromYear(fnc, from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        Integer dataCnt = facilityRepository.getFcltyWelfareUsageLatestCount(srcDataInfo, formYear, toYear);
        checkStatsDataLimitOrThrow(fnc, dataCnt);

        List<StatDataItemDB> dataList = facilityRepository.findFcltyWelfareUsageLatest(srcDataInfo, formYear, toYear);
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

    public StatDataRes getFcltyWelfareUsageYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getFcltyWelfareUsage();
        checkReqStatYear("FcltyWelfareUsageYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = facilityRepository.findFcltyWelfareUsageByYear(srcDataInfo, targetYear);
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
