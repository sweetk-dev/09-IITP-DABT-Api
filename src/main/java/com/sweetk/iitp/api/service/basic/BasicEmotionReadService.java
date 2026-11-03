package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.annotation.ConditionalTimed;
import com.sweetk.iitp.api.constant.basic.EmotionStatType;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.basic.converter.StatsDataConverter;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.basic.StatsSrcDataInfoRepository;
import com.sweetk.iitp.api.repository.basic.emotion.EmotionStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 기초-정서 자립 현황 API Service — 통계 20종을 통계키 하나로 다룬다.
 * 응답 형식(StatInfo·StatDataRes)과 기간 규칙은 다른 기초 통계와 같다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicEmotionReadService extends BasicService {

    private final EmotionStatsRepository emotionRepository;
    private final StatsSrcDataInfoRepository srcDataInfoRepos;
    private final MetadataService metadataService;

    public StatInfo getInfo(EmotionStatType type) {
        return StatsDataConverter.toStatInfoResponse(getSrcDataInfo(type));
    }

    @ConditionalTimed(value = "basic.emotion.latest", description = "정서 자립 현황 통계 조회")
    public StatDataRes getLatest(EmotionStatType type, Integer from, Integer to) {
        StatsSrcDataInfoEntity srcDataInfo = getSrcDataInfo(type);
        String fnc = "Emotion." + type.getCode() + ".Latest";
        Integer fromYear = getReqFromYear(fnc, from, to,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());
        Integer toYear = getReqToYear(to, srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = emotionRepository.findLatest(type, srcDataInfo, fromYear, toYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }
        List<StatDataItem> items = limitStatsDataList(toItems(srcDataInfo, dataList));
        return StatsDataConverter.toResponseFromItems(srcDataInfo, items);
    }

    public StatDataRes getYear(EmotionStatType type, Integer targetYear) {
        StatsSrcDataInfoEntity srcDataInfo = getSrcDataInfo(type);
        checkReqStatYear("Emotion." + type.getCode() + ".Year", targetYear,
                srcDataInfo.toIntCollectStartDt(), srcDataInfo.toIntCollectEndDt());

        List<StatDataItemDB> dataList = emotionRepository.findByYear(type, srcDataInfo, targetYear);
        if (dataList.isEmpty()) {
            return StatsDataConverter.toResponseFromItems(srcDataInfo, Collections.emptyList());
        }
        return StatsDataConverter.toResponseFromItems(srcDataInfo, toItems(srcDataInfo, dataList));
    }

    private List<StatDataItem> toItems(StatsSrcDataInfoEntity srcDataInfo, List<StatDataItemDB> dataList) {
        LocalDate srcLatestChnDate = LocalDate.parse(srcDataInfo.getStatLatestChnDt());
        List<StatMetaCodeDB> cMetaCodes = metadataService.getCatMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        List<StatMetaCodeDB> iMetaCodes = metadataService.getItemMetaCodes(srcDataInfo.getSrcDataId(), srcLatestChnDate);
        return makeStatDataItemList(dataList, cMetaCodes, iMetaCodes);
    }

    private StatsSrcDataInfoEntity getSrcDataInfo(EmotionStatType type) {
        StatsSrcDataInfoEntity srcDataInfo = srcDataInfoRepos.findActiveByIntgTblId(type.getIntgTblId());
        if (srcDataInfo == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "통계 정보가 없습니다: " + type.getCode());
        }
        return srcDataInfo;
    }
}
