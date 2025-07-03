package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HousingRepository {
    // 신규등록 장애인현황
    Integer getRegNatlByNewLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findRegNatlByNewLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findRegNatlByNewByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 전국 연령별,장애등급별,성별 등록장애인수
    Integer getRegNatlByAgeTypeSevGenLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findRegNatlByAgeTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findRegNatlByAgeTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 시도별,장애유형별,장애정도별,성별 등록장애인수
    Integer getRegSidoByTypeSevGenLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findRegSidoByTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findRegSidoByTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 일상생활 필요 지원 정도
    Integer getLifeSuppNeedLvlLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifeSuppNeedLvlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifeSuppNeedLvlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 주로 지원해주는 사람의 유형
    Integer getLifeMaincarerLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifeMaincarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifeMaincarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 일상생활 도와주는 사람(1순위)
    Integer getLifePrimcarerLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifePrimcarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifePrimcarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 도움받는 분야
    Integer getLifeSuppFieldLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifeSuppFieldLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findLifeSuppFieldByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 