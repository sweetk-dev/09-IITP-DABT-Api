package com.sweetk.iitp.api.repository.basic.poi;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.common.PageRequest;
import com.sweetk.iitp.api.dto.basic.poi.MvPoi;
import com.sweetk.iitp.api.entity.basic.poi.QMvPoiEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MvPoiRepositoryImpl extends BasicRepositoryQuerySupport implements MvPoiRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public PageRes<MvPoi> findPoiList(PageRequest pageRequest, String sidoCd, String sggCd, String poiCd, String poiNm) {
        QMvPoiEntity mvPoi = QMvPoiEntity.mvPoiEntity;

        List<MvPoi> content = queryFactory
                .select(Projections.constructor(MvPoi.class,
                        mvPoi.poiCd,
                        mvPoi.poiNm,
                        mvPoi.sidoCd,
                        mvPoi.sidoNm,
                        mvPoi.sggCd,
                        mvPoi.sggNm,
                        mvPoi.lon,
                        mvPoi.lat))
                .from(mvPoi)
                .where(
                        eqSidoCd(sidoCd),
                        eqSggCd(sggCd),
                        eqPoiCd(poiCd),
                        containsPoiNm(poiNm)
                )
                .orderBy(mvPoi.poiNm.asc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getSize())
                .fetch();

        Long total = queryFactory
                .select(mvPoi.count())
                .from(mvPoi)
                .where(
                        eqSidoCd(sidoCd),
                        eqSggCd(sggCd),
                        eqPoiCd(poiCd),
                        containsPoiNm(poiNm)
                )
                .fetchOne();

        return new PageRes<>(content, total, pageRequest);
    }

    private BooleanExpression eqSidoCd(String sidoCd) {
        return sidoCd != null ? QMvPoiEntity.mvPoiEntity.sidoCd.eq(sidoCd) : null;
    }

    private BooleanExpression eqSggCd(String sggCd) {
        return sggCd != null ? QMvPoiEntity.mvPoiEntity.sggCd.eq(sggCd) : null;
    }

    private BooleanExpression eqPoiCd(String poiCd) {
        return poiCd != null ? QMvPoiEntity.mvPoiEntity.poiCd.eq(poiCd) : null;
    }

    private BooleanExpression containsPoiNm(String poiNm) {
        return poiNm != null ? QMvPoiEntity.mvPoiEntity.poiNm.contains(poiNm) : null;
    }
} 