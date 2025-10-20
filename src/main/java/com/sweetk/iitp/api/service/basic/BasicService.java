package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.config.ApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BasicService {

    @Autowired
    protected ApiProperties apiProperties;

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
                    StatMetaCodeDB c1Meta = cMetaMap.get(item.getC1());
                    StatMetaCodeDB c2Meta = cMetaMap.get(item.getC2());
                    StatMetaCodeDB c3Meta = cMetaMap.get(item.getC3());
                    StatMetaCodeDB iMeta = iMetaMap.get(item.getItmId());

                    // Null 체크 및 로깅 (c1과 iMeta는 필수, c2와 c3는 선택적)
                    if (c1Meta == null) {
                        log.error("c1Meta is null for item.getC1()={}, prdDe={}", item.getC1(), item.getPrdDe());
                        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, 
                            String.format("필수 메타데이터를 찾을 수 없습니다. c1=%s", item.getC1()));
                    }
                    // c2가 있는데 메타를 못찾은 경우에만 에러
                    if (item.getC2() != null && !item.getC2().isEmpty() && c2Meta == null) {
                        log.error("c2Meta is null for item.getC2()={}, prdDe={}", item.getC2(), item.getPrdDe());
                        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, 
                            String.format("메타데이터를 찾을 수 없습니다. c2=%s", item.getC2()));
                    }
                    // c3가 있는데 메타를 못찾은 경우에만 에러
                    if (item.getC3() != null && !item.getC3().isEmpty() && c3Meta == null) {
                        log.error("c3Meta is null for item.getC3()={}, prdDe={}", item.getC3(), item.getPrdDe());
                        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, 
                            String.format("메타데이터를 찾을 수 없습니다. c3=%s", item.getC3()));
                    }
                    if (iMeta == null) {
                        log.error("iMeta is null for item.getItmId()={}, prdDe={}", item.getItmId(), item.getPrdDe());
                        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, 
                            String.format("필수 메타데이터를 찾을 수 없습니다. itmId=%s", item.getItmId()));
                    }

                    return StatDataItem.builder()
                            .prdDe(item.getPrdDe())
                            .c1Nm(c1Meta.getItemNm())
                            .c2Nm(c2Meta != null ? c2Meta.getItemNm() : null)
                            .c3Nm(c3Meta != null ? c3Meta.getItemNm() : null)
                            .c1ObjId(c1Meta.getObjId())
                            .c1ObjNm(c1Meta.getObjNm())
                            .c2ObjId(c2Meta != null ? c2Meta.getObjId() : null)
                            .c2ObjNm(c2Meta != null ? c2Meta.getObjNm() : null)
                            .c3ObjId(c3Meta != null ? c3Meta.getObjId() : null)
                            .c3ObjNm(c3Meta != null ? c3Meta.getObjNm() : null)
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
                Integer deffrom = endDate - ApiConstants.Param.DEFAULT_STAT_REG_YEAR_PERIOD;
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



    /**
     *  요청 통계 연도 검증 로직
     * @param reqFnc
     * @param statYear
     * @param startDate
     * @param endDate
     * @return
     */
    protected boolean checkReqStatYear(String reqFnc, Integer statYear, Integer startDate, Integer endDate) {

            if (statYear == null || statYear > endDate || statYear < startDate) {
            String detailMsg = String.format("statYear(%d) is invalid ( allowed range is {%d} ~ {%d})", statYear, startDate, endDate);
            log.error("[{}] :: {}", reqFnc, detailMsg);
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND, detailMsg);
        }
        return true;
    }

    /**
     * 통계 데이터 건수 제한 체크 (공통)
     * @param reqFnc
     * @param totalCount 전체 데이터 건수
     */
    protected void checkStatsDataLimitOrThrow(String reqFnc, int totalCount) {
        int limit = apiProperties.getStatsData() != null ? apiProperties.getStatsData().getLimitCount() : 0;
        if (limit > 0 && totalCount > limit) {
            String msg = String.format("조회 건수(%d)가 허용된 최대값(%d)을 초과했습니다.", totalCount, limit);
            log.warn("[{}] :: [통계 데이터 건수 제한] {}", reqFnc, msg);
            throw new BusinessException(ErrorCode.EXCEED_STATS_DATA_LIMIT, msg);
        }
    }

    /**
     * 통계 데이터 리스트를 limit 크기로 제한 (공통)
     * @param dataList 원본 데이터 리스트
     * @param <T> 데이터 타입
     * @return limit이 설정되어 있고 리스트 크기가 limit보다 크면 limit 크기만큼만 반환, 그렇지 않으면 원본 반환
     */
    protected <T> List<T> limitStatsDataList(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return dataList;
        }
        
        int limit = apiProperties.getStatsData() != null ? apiProperties.getStatsData().getLimitCount() : 0;
        if (limit > 0 && dataList.size() > limit) {
            log.debug("데이터 리스트 크기({})가 limit({})를 초과하여 limit 크기로 제한합니다.", dataList.size(), limit);
            return dataList.stream().limit(limit).collect(Collectors.toList());
        }
        
        return dataList;
    }

} 