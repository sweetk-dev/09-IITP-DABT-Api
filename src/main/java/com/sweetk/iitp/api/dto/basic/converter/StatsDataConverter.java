package com.sweetk.iitp.api.dto.basic.converter;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsDataConverter {
    public static StatDataRes toResponse(StatsSrcDataInfoEntity infoEntity, List<BaseStatsEntity> dataList) {
        if (infoEntity == null) {
            return null;
        }

        StatInfo statInfo = StatInfo.builder()
                .tblName(infoEntity.getStatTblName())
                .period(infoEntity.getPeriodicity())
                .collectDate(String.format(ApiConstants.Param.FORMAT_STAT_COLLECT_DATE, infoEntity.getCollectStartDt(), infoEntity.getCollectEndDt()))
                .orgName(infoEntity.getStatOrgName())
                .surveyName(infoEntity.getStatSurveyName())
                .latestChnDate(infoEntity.getStatLatestChnDt())
                .build();

        List<StatDataItem> items = (dataList != null)
                ? dataList.stream()
                .map(StatDataItem::fromEntity)
                .toList()
                : Collections.emptyList();

        return StatDataRes.builder()
                .statInfo(statInfo)
                .items(items)
                .build();
    }

    public static StatDataRes toResponseFromItems(StatsSrcDataInfoEntity infoEntity, List<StatDataItem> dataList) {
        if (infoEntity == null) {
            return null;
        }

        StatInfo statInfo = StatInfo.builder()
                .tblName(infoEntity.getStatTblName())
                .period(infoEntity.getPeriodicity())
                .collectDate(String.format(ApiConstants.Param.FORMAT_STAT_COLLECT_DATE, infoEntity.getCollectStartDt(), infoEntity.getCollectEndDt()))
                .orgName(infoEntity.getStatOrgName())
                .surveyName(infoEntity.getStatSurveyName())
                .latestChnDate(infoEntity.getStatLatestChnDt())
                .build();

        List<StatDataItem> items = (dataList != null) ? dataList : Collections.emptyList();

        return StatDataRes.builder()
                .statInfo(statInfo)
                .items(items)
                .build();
    }
}
