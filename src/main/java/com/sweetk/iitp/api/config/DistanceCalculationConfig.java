package com.sweetk.iitp.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "app.distance-calculation")
@Getter
@Setter
public class DistanceCalculationConfig {
    
    /**
     * 거리 계산 방식
     * POSTGIS_SPHERE: ST_Distance_Sphere (가장 정확, PostGIS 필요)
     * POSTGIS_PLANAR: ST_Distance (기본 PostGIS, 빠름)
     * POSTGRESQL_EARTH: earth_distance (PostgreSQL 기본, 확장 불필요)
     */
    private DistanceMethod method = DistanceMethod.POSTGIS_SPHERE;
    
    /**
     * PostGIS 확장 사용 여부
     */
    //private boolean usePostgis = true;
    
    public enum DistanceMethod {
        POSTGIS_SPHERE,
        POSTGIS_PLANAR,
        POSTGRESQL_EARTH
    }
    
    /**
     * 거리 계산 SQL 생성
     */
    public String getDistanceCalculationSql() {
        switch (method) {
            case POSTGIS_SPHERE:
                return "ST_Distance_Sphere(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(?, ?), 4326))";
            case POSTGIS_PLANAR:
                return "ST_Distance(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(?, ?), 4326))";
            case POSTGRESQL_EARTH:
                return "earth_distance(ll_to_earth(latitude, longitude), ll_to_earth(?, ?))";
            default:
                return "ST_Distance_Sphere(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(?, ?), 4326))";
        }
    }

    /**
     * 거리 필터링 SQL 생성 (숫자형 인자)
     */
    public String getDistanceFilterSql(Double latParam, Double lngParam, Double radiusParam) {
        switch (method) {
            case POSTGIS_SPHERE:
                return String.format("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, ST_SetSRID(ST_MakePoint(%s, %s), 4326)::geography, %s)", lngParam, latParam, radiusParam);
            case POSTGIS_PLANAR:
                return String.format("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(%s, %s), 4326), %s)", lngParam, latParam, radiusParam);
            case POSTGRESQL_EARTH:
                return String.format("AND earth_distance(ll_to_earth(latitude, longitude), ll_to_earth(%s, %s)) <= %s", latParam, lngParam, radiusParam);
            default:
                return String.format("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, ST_SetSRID(ST_MakePoint(%s, %s), 4326)::geography, %s)", lngParam, latParam, radiusParam);
        }
    }
}
