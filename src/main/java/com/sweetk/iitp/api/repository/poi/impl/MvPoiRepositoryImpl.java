package com.sweetk.iitp.api.repository.poi.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchCatReq;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchLocReq;
import com.sweetk.iitp.api.entity.poi.QMvPoiEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MvPoiRepositoryImpl implements MvPoiRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMvPoiEntity qEntity = QMvPoiEntity.mvPoiEntity;

    @Override
    public PageRes<MvPoi> findPoiList(Pageable pageable) {
        BooleanExpression baseCondition = getBaseCondition();
        
        List<MvPoi> content = queryFactory
                .select(Projections.constructor(MvPoi.class,
                        qEntity.poiId,
                        qEntity.languageCode,
                        qEntity.title,
                        qEntity.summary,
                        qEntity.basicInfo,
                        qEntity.addressCode,
                        qEntity.addressRoad,
                        qEntity.addressDetail,
                        qEntity.latitude,
                        qEntity.longitude,
                        qEntity.detailJson,
                        qEntity.searchFilterJson))
                .from(qEntity)
                .where(baseCondition)
                .orderBy(qEntity.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(qEntity.count())
                .from(qEntity)
                .where(baseCondition)
                .fetchOne();

        return new PageRes<>(content, pageable, total);
    }

    @Override
    public PageRes<MvPoi> findByCategory(MvPoiSearchCatReq catReq, Pageable pageable) {
        BooleanExpression baseCondition = getBaseCondition()
                .and(eqCategory(catReq.getCategory()));

        List<MvPoi> content = queryFactory
                .select(Projections.constructor(MvPoi.class,
                        qEntity.poiId,
                        qEntity.languageCode,
                        qEntity.title,
                        qEntity.summary,
                        qEntity.basicInfo,
                        qEntity.addressCode,
                        qEntity.addressRoad,
                        qEntity.addressDetail,
                        qEntity.latitude,
                        qEntity.longitude))
                .from(qEntity)
                .where(baseCondition)
                .orderBy(qEntity.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(qEntity.count())
                .from(qEntity)
                .where(baseCondition)
                .fetchOne();

        return new PageRes<>(content, pageable, total);
    }

    @Override
    public PageRes<MvPoi> findByLocation(MvPoiSearchLocReq locReq, Pageable pageable) {
        BooleanExpression baseCondition = getBaseCondition()
                .and(withinRadius(locReq.getLatitude(), locReq.getLongitude(), locReq.getRadius()));

        List<MvPoi> content = queryFactory
                .select(Projections.constructor(MvPoi.class,
                        qEntity.poiId,
                        qEntity.languageCode,
                        qEntity.title,
                        qEntity.summary,
                        qEntity.basicInfo,
                        qEntity.addressCode,
                        qEntity.addressRoad,
                        qEntity.addressDetail,
                        qEntity.latitude,
                        qEntity.longitude))
                .from(qEntity)
                .where(baseCondition)
                .orderBy(qEntity.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(qEntity.count())
                .from(qEntity)
                .where(baseCondition)
                .fetchOne();

        return new PageRes<>(content, pageable, total);
    }

    private BooleanExpression getBaseCondition() {
        return qEntity.isPublished.eq("Y")
                .and(qEntity.isDeleted.eq("N"));
    }

    private BooleanExpression eqCategory(String category) {
        return category != null ? qEntity.searchFilterJson.contains(category) : null;
    }

    private BooleanExpression withinRadius(Double latitude, Double longitude, Double radius) {
        if (latitude == null || longitude == null || radius == null) {
            return null;
        }
        // ST_Distance 함수를 사용하여 반경 내 POI 검색
        return qEntity.latitude.isNotNull()
                .and(qEntity.longitude.isNotNull())
                .and(qEntity.latitude.between(latitude - radius, latitude + radius))
                .and(qEntity.longitude.between(longitude - radius, longitude + radius));
    }
} 
