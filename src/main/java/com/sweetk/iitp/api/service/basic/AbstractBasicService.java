package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractBasicService {
    
    protected final BasicBaseReadService basicBaseReadService;
    
    protected AbstractBasicService(BasicBaseReadService basicBaseReadService) {
        this.basicBaseReadService = basicBaseReadService;
    }

    // 공통 데이터 변환 로직
    protected List<StatDataItem> makeStatDataItemList( List<StatDataItemDB> dataList,
                                                     List<StatMetaCodeDB> cMetaCodes, 
                                                     List<StatMetaCodeDB> iMetaCodes) {
        Map<String, StatMetaCodeDB> cMetaMap = new HashMap<>(cMetaCodes.size());
        cMetaCodes.forEach(code -> cMetaMap.put(code.getItemId(), code));
        Map<String, StatMetaCodeDB> iMetaMap = new HashMap<>(iMetaCodes.size());
        iMetaCodes.forEach(code -> iMetaMap.put(code.getItemId(), code));

        return dataList.stream()
                .map(item -> {
                    Map<String, String> metaData = new HashMap<>();
                    StatMetaCodeDB c1Meta = cMetaMap.get(item.getC1());
                    StatMetaCodeDB c2Meta = cMetaMap.get(item.getC2());
                    StatMetaCodeDB c3Meta = cMetaMap.get(item.getC3());
                    StatMetaCodeDB iMeta = iMetaMap.get(item.getItmId());

                    return StatDataItem.builder()
                            .prdDe(item.getPrdDe())
                            .c1Nm(c1Meta.getItemNm())
                            .c2Nm(c2Meta.getItemNm())
                            .c3Nm(c3Meta.getItemNm())
                            .c1ObjNm(c1Meta.getObjNm())
                            .c2ObjNm(c2Meta.getObjNm())
                            .c3ObjNm(c3Meta.getObjNm())
                            .itmNm(iMeta.getItemNm())
                            .unitNm(item.getUnitNm())
                            .data(item.getData())
                            .lstChnDe(item.getLstChnDe())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 연도 검증 로직
    protected Integer getReqFromYear(String reqFnc, Integer fromYear, Integer startDate, Integer entDate) {
        if (fromYear == null) {
            log.debug("[{}] :: fromYear is null, DEF_SET_fromYear = {}", reqFnc, entDate);
            return entDate;
        }

        if (fromYear > entDate || (entDate - fromYear) > ApiConstants.Param.MAX_STAT_YEAR_PERIOD) {
            String detailMsg = String.format("from(%s) is invalid (Max %s Year)", fromYear, ApiConstants.Param.MAX_STAT_YEAR_PERIOD);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
        }
        return fromYear;
    }
} 