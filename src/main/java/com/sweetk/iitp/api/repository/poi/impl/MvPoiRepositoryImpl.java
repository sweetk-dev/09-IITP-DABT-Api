package com.sweetk.iitp.api.repository.poi.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.constant.MvPoiCategoryType;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchCatReq;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchLocReq;
import com.sweetk.iitp.api.entity.poi.QMvPoiEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MvPoiRepositoryImpl implements MvPoiRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMvPoiEntity qEntity = QMvPoiEntity.mvPoiEntity;
    private final ObjectMapper objectMapper = new ObjectMapper();

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

        // category, subCategory 매핑 처리
        content = mapCategoryAndSubCategory(content);

        Long total = queryFactory
                .select(qEntity.count())
                .from(qEntity)
                .where(baseCondition)
                .fetchOne();

        return new PageRes<MvPoi>(content, pageable, total);
    }

    @Override
    public PageRes<MvPoi> findByCategory(MvPoiSearchCatReq catReq, Pageable pageable) {
        BooleanExpression baseCondition = getBaseCondition();

        // 1. name 단독 검색 (6번 케이스)
        if (catReq.getCategory() == null && catReq.getSubCate() == null && catReq.getName() != null) {
            baseCondition = baseCondition.and(qEntity.title.like("%" + catReq.getName() + "%"));
        }
        // 2. category 단독 검색 (1번 케이스)
        else if (catReq.getCategory() != null && catReq.getSubCate() == null && catReq.getName() == null) {
            baseCondition = baseCondition.and(eqCategory(catReq.getCategory()));
        }
        // 3. category + subCategory 검색 (2번 케이스)
        else if (catReq.getCategory() != null && catReq.getSubCate() != null && catReq.getName() == null) {
            baseCondition = baseCondition.and(eqCategory(catReq.getCategory()));
            baseCondition = addSubCategoryCondition(baseCondition, catReq);
        }
        // 4. category + name 검색 (4번 케이스)
        else if (catReq.getCategory() != null && catReq.getSubCate() == null && catReq.getName() != null) {
            baseCondition = baseCondition.and(eqCategory(catReq.getCategory()))
                    .and(qEntity.title.like("%" + catReq.getName() + "%"));
        }
        // 5. category + subCategory + name 검색 (5번 케이스)
        else if (catReq.getCategory() != null && catReq.getSubCate() != null && catReq.getName() != null) {
            baseCondition = baseCondition.and(eqCategory(catReq.getCategory()))
                    .and(qEntity.title.like("%" + catReq.getName() + "%"));
            baseCondition = addSubCategoryCondition(baseCondition, catReq);
        }

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

        // category, subCategory 매핑 처리
        content = mapCategoryAndSubCategory(content);

        Long total = queryFactory
                .select(qEntity.count())
                .from(qEntity)
                .where(baseCondition)
                .fetchOne();

        return new PageRes<MvPoi>(content, pageable, total);
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
                        qEntity.longitude,
                        qEntity.detailJson,
                        qEntity.searchFilterJson))
                .from(qEntity)
                .where(baseCondition)
                .orderBy(qEntity.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // category, subCategory 매핑 처리
        content = mapCategoryAndSubCategory(content);

        Long total = queryFactory
                .select(qEntity.count())
                .from(qEntity)
                .where(baseCondition)
                .fetchOne();

        return new PageRes<MvPoi>(content, pageable, total);
    }

    /**
     * category, subCategory 매핑 처리 공통 함수
     */
    private List<MvPoi> mapCategoryAndSubCategory(List<MvPoi> content) {
        return content.stream()
                .map(poi -> {
                    try {
                        String searchFilterJson = poi.getSearchFilterJson();
                        if (searchFilterJson != null) {
                            JsonNode rootNode = objectMapper.readTree(searchFilterJson);
                            JsonNode searchFilterNode = rootNode.get("search_filter");
                            if (searchFilterNode != null) {
                                Iterator<Map.Entry<String, JsonNode>> fields = searchFilterNode.fields();
                                while (fields.hasNext()) {
                                    Map.Entry<String, JsonNode> entry = fields.next();
                                    String key = entry.getKey();
                                    MvPoiCategoryType categoryType = getCategoryType(key);
                                    if (categoryType != null) {
                                        String subCategory = entry.getValue().asText();
                                        poi.setCategory(categoryType.getCode());
                                        poi.setSubCategory(subCategory);
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        poi.setCategory("");
                        poi.setSubCategory("");
                    }
                    return poi;
                })
                .collect(Collectors.toList());
    }

    private MvPoiCategoryType getCategoryType(String key) {
        try {
            return MvPoiCategoryType.fromString(key);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private BooleanExpression getBaseCondition() {
        return qEntity.isPublished.eq("Y")
                .and(qEntity.isDeleted.eq("N"));
    }

    private BooleanExpression eqCategory(MvPoiCategoryType category) {
        if (category == null) return null;
        // PostgreSQL JSON 연산자 사용
        return qEntity.searchFilterJson.contains("\"search_filter\":{\"" + category.getCode() + "\"");
    }

    private BooleanExpression withinRadius(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        if (latitude == null || longitude == null || radius == null) {
            return null;
        }
        
        // ST_DWithin을 사용하여 반경 검색
        // radius는 미터 단위로 변환 (1km = 1000m)
        return Expressions.booleanTemplate(
            "ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint({0}, {1}), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
            "{2})",
            longitude.doubleValue(), latitude.doubleValue(), radius.doubleValue() * 1000
        );
    }

    /**
     * subCategory 검색 조건 추가
     */
    private BooleanExpression addSubCategoryCondition(BooleanExpression baseCondition, MvPoiSearchCatReq catReq) {
        MvPoiCategoryType categoryType = catReq.getCategory();
        if (categoryType == null) {
            return baseCondition;
        }

        if (categoryType.isArray()) {
            // isArray가 true인 경우 like 검색
            if (catReq.getSubCate().contains(",")) {
                // 여러 subCategory를 OR 조건으로 검색
                String[] subCategories = catReq.getSubCate().split(",");
                BooleanExpression subCategoryCondition = null;
                for (String subCategory : subCategories) {
                    // PostgreSQL JSON 연산자 사용
                    BooleanExpression likeCondition = Expressions.booleanTemplate(
                        "search_filter_json->'search_filter'->>'{0}' LIKE {1}",
                        categoryType.getCode(),
                        "%" + subCategory.trim() + "%"
                    );
                    subCategoryCondition = subCategoryCondition == null ? likeCondition : subCategoryCondition.or(likeCondition);
                }
                return baseCondition.and(subCategoryCondition);
            } else {
                // 단일 subCategory like 검색
                return baseCondition.and(Expressions.booleanTemplate(
                    "search_filter_json->'search_filter'->>'{0}' LIKE {1}",
                    categoryType.getCode(),
                    "%" + catReq.getSubCate() + "%"
                ));
            }
        } else {
            // isArray가 false인 경우 정확한 매칭
            if (catReq.getSubCate().contains(",")) {
                // 여러 subCategory를 OR 조건으로 검색
                String[] subCategories = catReq.getSubCate().split(",");
                BooleanExpression subCategoryCondition = null;
                for (String subCategory : subCategories) {
                    // PostgreSQL JSON 연산자 사용
                    BooleanExpression eqCondition = Expressions.booleanTemplate(
                        "search_filter_json->'search_filter'->>'{0}' = {1}",
                        categoryType.getCode(),
                        subCategory.trim()
                    );
                    subCategoryCondition = subCategoryCondition == null ? eqCondition : subCategoryCondition.or(eqCondition);
                }
                return baseCondition.and(subCategoryCondition);
            } else {
                // 단일 subCategory 정확한 매칭
                return baseCondition.and(Expressions.booleanTemplate(
                    "search_filter_json->'search_filter'->>'{0}' = {1}",
                    categoryType.getCode(),
                    catReq.getSubCate()
                ));
            }
        }
    }
} 
