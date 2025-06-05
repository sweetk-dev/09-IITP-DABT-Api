package com.sweetk.iitp.api.service.basic;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.basic.StatDataItem;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.entity.basic.QBaseStatsEntity;
import com.sweetk.iitp.api.entity.basic.QMetaCodeEntity;
import com.sweetk.iitp.api.repository.basic.house.*;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 기초-주거 자립 현황 API Service
 */
@Service
@RequiredArgsConstructor
public class BasicHousingService extends BasicBaseService {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final StatsDisLifeMaincarerRepository LifeMaincarerRepos;
    private final StatsDisLifePrimcarerRepository LifePrimcarerRepos;
    private final StatsDisLifeSuppFieldRepository LifeSuppFieldRepos;
    private final StatsDisLifeSuppNeedLvlRepository LifeSuppNeedLvlRepos;
    private final StatsDisRegNatlByAgeTypeSevGenRepository RegNatlByAgeTypeSevGenRepos;
    private final StatsDisRegNatlByNewRepository RegNatlByNewRepos;
    private final StatsDisRegSidoByAgeTypeSevGenRepository RegSidoByAgeTypeSevGenRepos;



    /*******************************
     * 신규등록 장애인현황
     *******************************/

    /**
     * 기초-주거 자립 현황-최신 데이터 조회
     *
     * @param fromYear (옵션) 검색 시작 연도 (기본값: 1, 최대: 10년)
     * @return 주거 자립 현황-최신 데이터
     */
    @Timed(value = "housing.reg.new.latest", description = "신규등록 장애인현황 조회 시간")
    public StatDataRes getHousingRegNewLatest(Integer fromYear) {

        QBaseStatsEntity stats = QBaseStatsEntity.baseStatsEntity;
        QMetaCodeEntity metaC1 = new QMetaCodeEntity("metaC1");
        QMetaCodeEntity metaC2 = new QMetaCodeEntity("metaC2");
        QMetaCodeEntity metaC3 = new QMetaCodeEntity("metaC3");
        QMetaCodeEntity metaItm = new QMetaCodeEntity("metaItm");

        List<StatDataItem> result = queryFactory
                .select(Projections.constructor(StatDataItem.class,
                        stats.prdDe,
                        metaC1.name, // c1Nm
                        metaC2.name, // c2Nm
                        metaC3.name, // c3Nm
                        stats.c1ObjNm,
                        stats.c2ObjNm,
                        stats.c3ObjNm,
                        metaItm.name, // itmNm
                        stats.unitNm,
                        stats.dt.stringValue(),
                        stats.lstChnDe
                ))
                .from(stats)
                .leftJoin(metaC1).on(metaC1.code.eq(stats.c1).and(metaC1.classId.eq("C1")))
                .leftJoin(metaC2).on(metaC2.code.eq(stats.c2).and(metaC2.classId.eq("C2")))
                .leftJoin(metaC3).on(metaC3.code.eq(stats.c3).and(metaC3.classId.eq("C3")))
                .leftJoin(metaItm).on(metaItm.code.eq(stats.itmId).and(metaItm.classId.eq("ITM"))) // 같은 테이블이지만 조건 다름
                .fetch();

    }



    /*******************************
     * 전국 연령별,장애등급별,성별 등록장애인수
     *******************************/



    /*******************************
     * 시도별,장애유형별,장애정도별,성별 등록장애인수
     *******************************/



    /*******************************
     * 일상생활 필요 지원 정도
     *******************************/




    /*******************************
     * 주로 지원해주는 사람의 유형
     *******************************/



    /*******************************
     * 일상생활 도와주는 사람(1순위)
     *******************************/





    /*******************************
     * 도움받는 분야
     *******************************/





}
