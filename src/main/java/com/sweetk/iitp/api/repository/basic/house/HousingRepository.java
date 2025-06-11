package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HousingRepository {
    // 신규등록 장애인현황
    List<StatDataItemDB> findRegNatlByNewLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findRegNatlByNewByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 전국 연령별,장애등급별,성별 등록장애인수
    List<StatDataItemDB> findRegNatlByAgeTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findRegNatlByAgeTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 시도별,장애유형별,장애정도별,성별 등록장애인수
    List<StatDataItemDB> findRegSidoByAgeTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findRegSidoByAgeTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 일상생활 필요 지원 정도
    List<StatDataItemDB> findLifeSuppNeedLvlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findLifeSuppNeedLvlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 주로 지원해주는 사람의 유형
    List<StatDataItemDB> findLifeMaincarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findLifeMaincarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 일상생활 도와주는 사람(1순위)
    List<StatDataItemDB> findLifePrimcarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findLifePrimcarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 도움받는 분야
    List<StatDataItemDB> findLifeSuppFieldLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear);
    List<StatDataItemDB> findLifeSuppFieldByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 