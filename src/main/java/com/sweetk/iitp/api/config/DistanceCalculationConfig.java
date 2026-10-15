package com.sweetk.iitp.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 위치 기반 검색의 거리 계산 SQL 을 만들어 주는 설정.
 *
 * <p>사용처: MvPoi / PoiPublicToiletInfo / PoiSubwayElevator / PoiTourBfFacility 의 RepositoryImpl.
 * 각 저장소는 아래 두 메서드를 이렇게 쓴다.
 * <ul>
 *   <li>{@link #getDistanceCalculationSql()} — SELECT 절의 {@code ... AS distance} 식.
 *       물음표 2개가 들어 있고, 저장소는 항상 <b>1번 = 경도(longitude), 2번 = 위도(latitude)</b>
 *       순서로 {@code PreparedStatement.setDouble} 한다. 그래서 여기서 만드는 모든 식은
 *       이 바인딩 순서(경도, 위도)를 전제로 작성해야 한다.</li>
 *   <li>{@link #getDistanceFilterSql(Double, Double, Double)} — WHERE 절에 덧붙이는
 *       {@code AND ...} 반경 조건. 값을 문자열로 직접 넣는다(반경 단위: 미터).</li>
 * </ul>
 *
 * <p>계산 결과의 단위는 모든 방식에서 미터(m)다.
 *
 * <p>방식 선택 기준(application.yml 의 {@code app.distance-calculation.method}):
 * <ul>
 *   <li>{@code HAVERSINE} (기본값) — 순수 SQL 삼각함수만 쓴다. PostGIS·earthdistance 같은
 *       확장이 없어도 동작한다. 구면 반지름은 PostGIS {@code ST_DistanceSphere} 와 같은
 *       6,371,008.8 m(지구 평균 반지름)를 써서 결과가 PostGIS 와 소수점 둘째 자리까지 같다.
 *       공간 인덱스는 쓰지 못해 행 전체를 계산하지만, mv_poi 약 5만 행 기준 수십 ms 수준이다.</li>
 *   <li>{@code POSTGIS_SPHERE} — PostGIS 확장이 있는 DB 에서만. 반경 조건은 geography
 *       캐스팅이라 공간 인덱스를 탈 수 있다.</li>
 *   <li>{@code POSTGIS_PLANAR} — PostGIS 확장 필요. SRID 4326 좌표의 평면 거리라
 *       결과 단위가 "도(degree)"다. 미터 반경과 비교하면 틀리므로 운영에서 쓰지 않는다
 *       (기존 호환용으로만 남겨 둔다).</li>
 *   <li>{@code POSTGRESQL_EARTH} — PostgreSQL 기본 배포의 cube + earthdistance 확장 필요
 *       ({@code CREATE EXTENSION cube; CREATE EXTENSION earthdistance;}).</li>
 * </ul>
 *
 * <p>변경 이력
 * <ul>
 *   <li>v1.0.1 — {@code ST_Distance_Sphere} 는 PostGIS 3.0 에서 제거된 이름이라
 *       {@code ST_DistanceSphere} 로 정정. {@code POSTGRESQL_EARTH} 의 좌표 순서 오류 정정
 *       ({@code ll_to_earth} 는 (위도, 경도)를 받는데 바인딩은 (경도, 위도)였다).
 *       확장 없이 동작하는 {@code HAVERSINE} 방식 추가 및 기본값 변경.</li>
 * </ul>
 */
@Component
@ConfigurationProperties(prefix = "app.distance-calculation")
@Getter
@Setter
public class DistanceCalculationConfig {

    /**
     * 지구 평균 반지름(미터). PostGIS ST_DistanceSphere 가 쓰는 값과 같다.
     * HAVERSINE 결과를 PostGIS 결과와 맞추기 위해 이 값을 고정해서 쓴다.
     */
    static final String EARTH_RADIUS_M = "6371008.8";

    /**
     * 질의 좌표를 한 번만 바인딩하기 위한 파생 테이블.
     *
     * <p>하버사인 공식은 질의 위도를 두 번, 경도를 한 번 쓴다. 그런데 저장소는 물음표를
     * 정확히 2개(경도, 위도 순)만 바인딩하므로, 질의 좌표를 이 파생 테이블 q 의 컬럼으로
     * 한 번 받아 두고 식 안에서는 q.lng / q.lat 로 여러 번 참조한다.
     * 바깥 행의 latitude / longitude 를 참조하는 상관 스칼라 서브쿼리 형태라
     * SELECT 절에 그대로 넣을 수 있다.
     */
    private static final String BOUND_POINT =
            "(SELECT CAST(? AS double precision) AS lng, CAST(? AS double precision) AS lat) q";

    /**
     * 거리 계산 방식. application.yml 의 app.distance-calculation.method 로 바꾼다.
     * 값을 지정하지 않으면 확장이 필요 없는 HAVERSINE 을 쓴다.
     */
    private DistanceMethod method = DistanceMethod.HAVERSINE;

    public enum DistanceMethod {
        /** 순수 SQL 하버사인 공식 (확장 불필요, 기본값) */
        HAVERSINE,
        /** PostGIS ST_DistanceSphere / geography ST_DWithin (PostGIS 필요) */
        POSTGIS_SPHERE,
        /** PostGIS 평면 ST_Distance — 결과 단위가 도(degree)라 운영 사용 금지, 호환용 */
        POSTGIS_PLANAR,
        /** cube + earthdistance 확장의 earth_distance */
        POSTGRESQL_EARTH
    }

    /**
     * 하버사인 거리(미터) 식을 만든다.
     *
     * @param lat 질의 위도 식 (예: "q.lat" 또는 숫자 문자열)
     * @param lng 질의 경도 식 (예: "q.lng" 또는 숫자 문자열)
     * @return 바깥 행의 latitude / longitude 컬럼과 질의 좌표 사이의 거리(미터)를 돌려주는 SQL 식.
     *         좌표가 NULL 인 행은 NULL 이 된다(반경 조건에서는 자연히 빠지고, 정렬에서는 뒤로 간다).
     *         부동소수 오차로 SQRT 결과가 1 을 아주 조금 넘으면 ASIN 이 오류를 내므로 LEAST(1, …) 로 막는다.
     */
    static String haversineMeters(String lat, String lng) {
        return EARTH_RADIUS_M + " * 2 * ASIN(LEAST(1, SQRT("
                + "POWER(SIN(RADIANS(latitude - " + lat + ") / 2), 2) + "
                + "COS(RADIANS(" + lat + ")) * COS(RADIANS(latitude)) * "
                + "POWER(SIN(RADIANS(longitude - " + lng + ") / 2), 2)"
                + ")))";
    }

    /**
     * SELECT 절에 넣는 거리 계산 SQL 을 만든다.
     *
     * <p>물음표 2개를 포함하며, 호출 측은 1번 = 경도, 2번 = 위도로 바인딩한다(클래스 설명 참고).
     *
     * @return {@code ... AS distance} 앞에 들어갈 SQL 식(단위: 미터. POSTGIS_PLANAR 만 도)
     */
    public String getDistanceCalculationSql() {
        switch (method) {
            case POSTGIS_SPHERE:
                // ST_MakePoint(x=경도, y=위도) — 바인딩 순서(경도, 위도)와 그대로 맞는다.
                return "ST_DistanceSphere(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(?, ?), 4326))";
            case POSTGIS_PLANAR:
                return "ST_Distance(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(?, ?), 4326))";
            case POSTGRESQL_EARTH:
                // ll_to_earth(위도, 경도) 순서. 바인딩이 (경도, 위도)라서 파생 테이블로 받은 뒤 순서를 바꿔 넣는다.
                return "(SELECT earth_distance(ll_to_earth(latitude, longitude), ll_to_earth(q.lat, q.lng)) FROM " + BOUND_POINT + ")";
            case HAVERSINE:
            default:
                return "(SELECT " + haversineMeters("q.lat", "q.lng") + " FROM " + BOUND_POINT + ")";
        }
    }

    /**
     * WHERE 절에 덧붙이는 반경 조건 SQL 을 만든다(앞에 "AND " 가 붙어 있다).
     *
     * <p>값은 Double 을 문자열로 그대로 넣는다. Double.toString 은 로케일과 무관하게
     * 소수점을 "." 으로 쓰므로 SQL 숫자 표기가 깨지지 않는다. 입력은 컨트롤러에서
     * Double 로 받은 값만 들어오므로 문자열 주입 위험은 없다.
     *
     * @param latParam    질의 위도
     * @param lngParam    질의 경도
     * @param radiusParam 반경(미터). 요청 값(미터)을 그대로 넘긴다.
     * @return {@code AND <거리식> <= 반경} 형태의 SQL 조각
     */
    public String getDistanceFilterSql(Double latParam, Double lngParam, Double radiusParam) {
        switch (method) {
            case POSTGIS_SPHERE:
                return String.format("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, ST_SetSRID(ST_MakePoint(%s, %s), 4326)::geography, %s)", lngParam, latParam, radiusParam);
            case POSTGIS_PLANAR:
                return String.format("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ST_SetSRID(ST_MakePoint(%s, %s), 4326), %s)", lngParam, latParam, radiusParam);
            case POSTGRESQL_EARTH:
                // ll_to_earth(위도, 경도) — 이 경로는 원래부터 순서가 맞았다.
                return String.format("AND earth_distance(ll_to_earth(latitude, longitude), ll_to_earth(%s, %s)) <= %s", latParam, lngParam, radiusParam);
            case HAVERSINE:
            default:
                return "AND " + haversineMeters(String.valueOf(latParam), String.valueOf(lngParam)) + " <= " + radiusParam;
        }
    }
}
