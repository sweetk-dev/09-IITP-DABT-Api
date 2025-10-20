package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.edu.EduRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 기초-진로 교육 현황 API Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicEduReadService extends BasicService {

    private final EduRepository eduRepository;
    private final MetadataService metadataService;
    private final DataSourceService dataSourceService;

    /*******************************
     * 장애인 진로 및 직업교육 실시 여부
     *******************************/
    public StatInfo getEduVocaExecInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEduVocaExec();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.edu.vocaExec.latest", description = "장애인 진로 및 직업교육 실시 여부 데이터 조회")
    public StatDataRes getEduVocaExecLatest(Integer from, Integer to) {
        String fnc = "EduVocaExecLatest";

        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEduVocaExec();
        Integer formYear = getReqFromYear(fnc, from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = eduRepository.findEduVocaExecLatest(srcDataInfo, formYear, toYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        // limit 적용: limit이 설정되어 있고 크기가 limit보다 크면 limit 만큼만 반환
        items = limitStatsDataList(items);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getEduVocaExecYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEduVocaExec();
        checkReqStatYear("EduVocaExecYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = eduRepository.findEduVocaExecByYear(srcDataInfo, targetYear);
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
     * 장애인 진로 및 직업교육 운영 방법
     *******************************/
    public StatInfo getEduVocaExecWayInfo() {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEduVocaExecWay();
        return StatsDataConverter.toStatInfoResponse(srcDataInfo);
    }

    @ConditionalTimed(value = "basic.edu.vocaExecWay.latest", description = "장애인 진로 및 직업교육 운영 방법 데이터 조회")
    public StatDataRes getEduVocaExecWayLatest(Integer from, Integer to) {
        String fnc = "EduVocaExecWayLatest";

        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEduVocaExecWay();
        Integer formYear = getReqFromYear(fnc, from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        Integer toYear = getReqToYear((Integer)to, (Integer)srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = eduRepository.findEduVocaExecWayLatest(srcDataInfo, formYear, toYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }

        // 3. 메타 코드 정보 조회
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);

        // 4. 데이터 변환 및 응답 생성
        List<StatDataItem> items = makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
        // limit 적용: limit이 설정되어 있고 크기가 limit보다 크면 limit 만큼만 반환
        items = limitStatsDataList(items);
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getEduVocaExecWayYear(Integer targetYear) {
        // 1. 데이터 소스 정보 조회
        StatsSrcDataInfoEntity srcDataInfo = dataSourceService.getEduVocaExecWay();
        checkReqStatYear("EduVocaExecYear", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        // 2. 기본 데이터 조회
        List<StatDataItemDB> dataList = eduRepository.findEduVocaExecWayByYear(srcDataInfo, targetYear);
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
