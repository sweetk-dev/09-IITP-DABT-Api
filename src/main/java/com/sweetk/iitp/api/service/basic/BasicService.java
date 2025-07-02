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
public class BasicService {

    // 공통 데이터 변환 로직
    protected List<StatDataItem> makeStatDataItemList(List<StatDataItemDB> dataList,
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
                            .c1ObjId(c1Meta.getObjId())
                            .c1ObjNm(c1Meta.getObjNm())
                            .c2ObjId(c2Meta.getObjId())
                            .c2ObjNm(c2Meta.getObjNm())
                            .c3ObjId(c3Meta.getObjId())
                            .c3ObjNm(c3Meta.getObjNm())
                            .itmNm(iMeta.getItemNm())
                            .itmObjId(iMeta.getObjId())
                            .unitNm(item.getUnitNm())
                            .data(item.getData())
                            .lstChnDe(item.getLstChnDe())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 연도 검증 로직
    protected Integer getReqFromYear(String reqFnc, Integer from, Integer to, Integer startDate, Integer endDate) {

        if (from == null || from == 0 ) {
            if( to == null || to == 0 ) {
                Integer deffrom = endDate - ApiConstants.Param.DEFAULT_STAT_REG_YEAR_PERIOD-1;
                log.debug("[{}] :: from is null, DEF_SET_from = {}", reqFnc, deffrom);
                return deffrom;
            }

            String detailMsg = String.format("from(%d), to(%d) are invalid ( allowed range is {%d} ~ {%d})", from, to, startDate, endDate);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
        }

        if (from > endDate || from < startDate) {
            String detailMsg = String.format("from(%d) is invalid ( allowed range is {%d} ~ {%d})", from, startDate, endDate);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
        }

        if(to != null && to != 0) {
            if (to < startDate || to > endDate || from > to) {
                String detailMsg = String.format("from(%d), to(%d) are invalid ( allowed range is {%d} ~ {%d})", from, to, startDate, endDate);
                log.error("[{}] :: {}", reqFnc, detailMsg);
                throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
            }

            if ((to - from) > ApiConstants.Param.MAX_STAT_REQ_YEAR_PERIOD) {
                String detailMsg = String.format("from(%d), to(%d) are invalid (Max Period %d Year)", from, to, ApiConstants.Param.MAX_STAT_REQ_YEAR_PERIOD);
                log.error("[{}] :: {}", reqFnc, detailMsg);
                throw new BusinessException(ErrorCode.INVALID_PARAMETER, detailMsg);
            }
        }

        return from;
    }


    public Integer getReqToYear( Integer to, Integer endDate) {
        return (to == null || to == 0) ? endDate : to;
    }


    // 연도 검증 로직
    protected boolean checkReqStatYear(String reqFnc, Integer statYear, Integer startDate, Integer endDate) {

            if (statYear == null || statYear > endDate || statYear < startDate) {
            String detailMsg = String.format("statYear(%d) is invalid ( allowed range is {%d} ~ {%d})", statYear, startDate, endDate);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND, detailMsg);
        }
        return true;
    }


} 