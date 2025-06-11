package com.sweetk.iitp.api.service.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BasicCommLogic {


    //Make StatDataItem
    public List<StatDataItem> makeStatDataItemList(StatsSrcDataInfoEntity srcDataInfo, List<StatDataItemDB> dataList,
                                                   List<StatMetaCodeDB> cMetaCodes, List<StatMetaCodeDB> iMetaCodes) {

        // 3. 메타 코드를 Map으로 변환 (초기 용량 지정)
        Map<String, StatMetaCodeDB> cMetaCodeMap = new HashMap<>(cMetaCodes.size());
        cMetaCodes.forEach(code -> cMetaCodeMap.put(code.getItemId(), code));
        Map<String, StatMetaCodeDB> iMetaCodeMap = new HashMap<>(iMetaCodes.size());
        iMetaCodes.forEach(code -> iMetaCodeMap.put(code.getItemId(), code));

        // 4. 데이터 변환 및 메타 코드 매핑 (병렬 처리)
        List<StatDataItem> items = dataList.parallelStream()
                .map(item -> {
                    StatMetaCodeDB c1Meta = cMetaCodeMap.get(item.getC1());
                    StatMetaCodeDB c2Meta = cMetaCodeMap.get(item.getC2());
                    StatMetaCodeDB c3Meta = cMetaCodeMap.get(item.getC3());
                    StatMetaCodeDB iMeta = iMetaCodeMap.get(item.getItmId());

                    return StatDataItem.builder()
                            .prdDe(item.getPrdDe())
                            .c1Nm(c1Meta != null ? c1Meta.getItemNm() : null)
                            .c2Nm(c2Meta != null ? c2Meta.getItemNm() : null)
                            .c3Nm(c3Meta != null ? c3Meta.getItemNm() : null)
                            .c1ObjNm(c1Meta != null ? c1Meta.getObjNm() : null)
                            .c2ObjNm(c2Meta != null ? c2Meta.getObjNm() : null)
                            .c3ObjNm(c3Meta != null ? c3Meta.getObjNm() : null)
                            .itmNm(iMeta != null ? iMeta.getItemNm() : null)
                            .unitNm(item.getUnitNm())
                            .data(item.getData())
                            .lstChnDe(item.getLstChnDe())
                            .build();
                })
                .collect(Collectors.toList());

        return items;
    }

    //fromYear checking (Req Param)

    /**
     * fromYear checking and Default Set collectionEntDate
     * @param reqFnc
     * @param fromYear
     * @param startDate
     * @param entDate
     * @return
     */
    public Integer getReqFromYear(String reqFnc, Integer fromYear, Integer startDate, Integer entDate  ) {
        if (fromYear == null ) {
            log.debug("[{}] :: fromYear is null, DEF_SET_fromYear = {}", reqFnc, entDate);
            return entDate;
        }

        if( fromYear > entDate || (entDate- fromYear) > ApiConstants.Param.MAX_STAT_YEAR_PERIOD )
        {
            String detailMsg = String.format("from(%s) is invalid (MAx %s Year)", fromYear, ApiConstants.Param.MAX_STAT_YEAR_PERIOD);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
        }
        return fromYear;
    }

}
