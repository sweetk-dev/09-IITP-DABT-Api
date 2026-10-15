package com.sweetk.iitp.api.repository.transit;

import com.sweetk.iitp.api.dto.transit.TransitBusRoute;
import com.sweetk.iitp.api.dto.transit.TransitBusRouteStop;
import com.sweetk.iitp.api.dto.transit.TransitBusStation;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/** 경기 버스 노선·정류소 (tran_bus_route_info, tran_bus_station_info, tran_bus_route_station) 조회 */
@Repository
public class TransitBusRepository {

    private static final String ROUTE_COLUMNS =
            "route_id, route_name, route_type_cd, route_type_name, region_name, admin_name, start_station_id, " +
            "start_station_name, end_station_id, end_station_name, company_name, company_tel, peek_alloc, npeek_alloc, " +
            "sat_peek_alloc, sat_npeek_alloc, sun_peek_alloc, sun_npeek_alloc, we_peek_alloc, we_npeek_alloc, " +
            "up_first_time, up_last_time, down_first_time, down_last_time, low_bus_yn, low_bus_base_dt, base_dt";
    private static final String ROUTE_FROM = " FROM tran_bus_route_info WHERE coalesce(del_yn, 'N') = 'N' ";

    private static final String STATION_COLUMNS =
            "station_id, mobile_no, station_name, region_name, admin_name, latitude, longitude, center_yn, district_cd, base_dt";
    private static final String STATION_FROM = " FROM tran_bus_station_info WHERE coalesce(del_yn, 'N') = 'N' ";

    private static final RowMapper<TransitBusRoute> ROUTE = (rs, n) -> {
        TransitBusRoute r = new TransitBusRoute();
        r.setRouteId(l(rs, "route_id"));
        r.setRouteName(s(rs, "route_name"));
        r.setRouteTypeCd(i(rs, "route_type_cd"));
        r.setRouteTypeName(s(rs, "route_type_name"));
        r.setRegionName(s(rs, "region_name"));
        r.setAdminName(s(rs, "admin_name"));
        r.setStartStationId(l(rs, "start_station_id"));
        r.setStartStationName(s(rs, "start_station_name"));
        r.setEndStationId(l(rs, "end_station_id"));
        r.setEndStationName(s(rs, "end_station_name"));
        r.setCompanyName(s(rs, "company_name"));
        r.setCompanyTel(s(rs, "company_tel"));
        r.setPeekAlloc(i(rs, "peek_alloc"));
        r.setNpeekAlloc(i(rs, "npeek_alloc"));
        r.setSatPeekAlloc(i(rs, "sat_peek_alloc"));
        r.setSatNpeekAlloc(i(rs, "sat_npeek_alloc"));
        r.setSunPeekAlloc(i(rs, "sun_peek_alloc"));
        r.setSunNpeekAlloc(i(rs, "sun_npeek_alloc"));
        r.setWePeekAlloc(i(rs, "we_peek_alloc"));
        r.setWeNpeekAlloc(i(rs, "we_npeek_alloc"));
        r.setUpFirstTime(s(rs, "up_first_time"));
        r.setUpLastTime(s(rs, "up_last_time"));
        r.setDownFirstTime(s(rs, "down_first_time"));
        r.setDownLastTime(s(rs, "down_last_time"));
        r.setLowBusYn(s(rs, "low_bus_yn"));
        r.setLowBusBaseDt(date(rs, "low_bus_base_dt"));
        r.setBaseDt(date(rs, "base_dt"));
        return r;
    };

    private static final RowMapper<TransitBusStation> STATION = (rs, n) -> {
        TransitBusStation s = new TransitBusStation();
        s.setStationId(l(rs, "station_id"));
        s.setMobileNo(s(rs, "mobile_no"));
        s.setStationName(s(rs, "station_name"));
        s.setRegionName(s(rs, "region_name"));
        s.setAdminName(s(rs, "admin_name"));
        s.setLatitude(d(rs, "latitude"));
        s.setLongitude(d(rs, "longitude"));
        s.setCenterYn(s(rs, "center_yn"));
        s.setDistrictCd(i(rs, "district_cd"));
        s.setBaseDt(date(rs, "base_dt"));
        s.setDistance(distance(rs));
        return s;
    };

    private static final RowMapper<TransitBusRouteStop> STOP = (rs, n) -> {
        TransitBusRouteStop s = new TransitBusRouteStop();
        s.setStationSeq(i(rs, "station_seq"));
        s.setTurnYn(s(rs, "turn_yn"));
        s.setStationId(l(rs, "station_id"));
        s.setMobileNo(s(rs, "mobile_no"));
        s.setStationName(s(rs, "station_name"));
        s.setLatitude(d(rs, "latitude"));
        s.setLongitude(d(rs, "longitude"));
        s.setBaseDt(date(rs, "base_dt"));
        return s;
    };

    private final JdbcReadSupport db;

    public TransitBusRepository(JdbcReadSupport db) {
        this.db = db;
    }

    public Optional<TransitBusRoute> findRoute(Long routeId) {
        List<Object> p = new ArrayList<>();
        p.add(routeId);
        return db.one("SELECT " + ROUTE_COLUMNS + ROUTE_FROM + "AND route_id = ?", p, ROUTE);
    }

    public PoiPageResult<TransitBusRoute> searchRoutes(String routeName, String regionName, String lowBusYn,
                                                       int offset, int size) {
        StringBuilder w = new StringBuilder(ROUTE_FROM);
        List<Object> p = new ArrayList<>();
        if (has(routeName)) { w.append("AND route_name ILIKE ? "); p.add(contains(routeName)); }
        if (has(regionName)) { w.append("AND region_name ILIKE ? "); p.add(contains(regionName)); }
        if (has(lowBusYn)) { w.append("AND low_bus_yn = ? "); p.add(lowBusYn); }
        return db.page(ROUTE_COLUMNS, w.toString(), p, "ORDER BY route_name, route_id", offset, size, ROUTE);
    }

    /** 노선이 경유하는 정류소 (경유 순서대로) */
    public List<TransitBusRouteStop> findRouteStops(Long routeId) {
        List<Object> p = new ArrayList<>();
        p.add(routeId);
        // 정류소 정보가 없는 경유 지점도 순번·ID 는 보존한다(LEFT JOIN)
        return db.list("SELECT rs.station_seq, rs.turn_yn, rs.station_id, s.mobile_no, s.station_name, s.latitude, " +
                "s.longitude, rs.base_dt FROM tran_bus_route_station rs " +
                "LEFT JOIN tran_bus_station_info s ON s.station_id = rs.station_id AND coalesce(s.del_yn, 'N') = 'N' " +
                "WHERE coalesce(rs.del_yn, 'N') = 'N' AND rs.route_id = ? ORDER BY rs.station_seq", p, STOP);
    }

    public Optional<TransitBusStation> findStation(Long stationId) {
        List<Object> p = new ArrayList<>();
        p.add(stationId);
        return db.one("SELECT " + STATION_COLUMNS + STATION_FROM + "AND station_id = ?", p, STATION);
    }

    public PoiPageResult<TransitBusStation> searchStations(String stationName, String mobileNo, String regionName,
                                                           int offset, int size) {
        StringBuilder w = new StringBuilder(STATION_FROM);
        List<Object> p = new ArrayList<>();
        if (has(stationName)) { w.append("AND station_name ILIKE ? "); p.add(contains(stationName)); }
        if (has(mobileNo)) { w.append("AND mobile_no = ? "); p.add(mobileNo.trim()); }
        if (has(regionName)) { w.append("AND region_name ILIKE ? "); p.add(contains(regionName)); }
        return db.page(STATION_COLUMNS, w.toString(), p, "ORDER BY station_name, station_id", offset, size, STATION);
    }

    public PoiPageResult<TransitBusStation> searchStationsByLocation(double lat, double lng, double radius,
                                                                     int offset, int size) {
        return db.locationPage(STATION_COLUMNS, STATION_FROM, new ArrayList<>(), "station_id", lat, lng, radius, offset, size, STATION);
    }

    /** 정류소를 경유하는 노선 */
    public List<TransitBusRoute> findStationRoutes(Long stationId) {
        List<Object> p = new ArrayList<>();
        p.add(stationId);
        return db.list("SELECT " + ROUTE_COLUMNS + ROUTE_FROM +
                "AND route_id IN (SELECT route_id FROM tran_bus_route_station WHERE coalesce(del_yn, 'N') = 'N' " +
                "AND station_id = ?) ORDER BY route_name, route_id", p, ROUTE);
    }
}
