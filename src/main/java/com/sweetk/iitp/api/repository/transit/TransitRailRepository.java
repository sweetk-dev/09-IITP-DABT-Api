package com.sweetk.iitp.api.repository.transit;

import com.sweetk.iitp.api.dto.transit.TransitRailElevator;
import com.sweetk.iitp.api.dto.transit.TransitRailPlatform;
import com.sweetk.iitp.api.dto.transit.TransitRailStation;
import com.sweetk.iitp.api.dto.transit.TransitRailToilet;
import com.sweetk.iitp.api.dto.transit.TransitRailWheelchairLift;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/** 철도 역사 편의설비 (poi_station_*) 조회 */
@Repository
public class TransitRailRepository {

    private static final String STATION_COLUMNS =
            "station_id, stn_cd, stn_name, line_name, elevator_cnt, escalator_cnt, wheelchair_lift_cnt, dis_slope_yn, " +
            "dis_toilet_yn, gen_toilet_yn, nursing_room_yn, info_center_yn, latitude, longitude, base_dt";
    private static final String STATION_FROM = " FROM poi_station_access_status WHERE coalesce(del_yn, 'N') = 'N' ";

    private static final RowMapper<TransitRailStation> STATION = (rs, n) -> {
        TransitRailStation s = new TransitRailStation();
        s.setStationId(i(rs, "station_id"));
        s.setStnCd(s(rs, "stn_cd"));
        s.setStnName(s(rs, "stn_name"));
        s.setLineName(s(rs, "line_name"));
        s.setElevatorCnt(i(rs, "elevator_cnt"));
        s.setEscalatorCnt(i(rs, "escalator_cnt"));
        s.setWheelchairLiftCnt(i(rs, "wheelchair_lift_cnt"));
        s.setDisSlopeYn(s(rs, "dis_slope_yn"));
        s.setDisToiletYn(s(rs, "dis_toilet_yn"));
        s.setGenToiletYn(s(rs, "gen_toilet_yn"));
        s.setNursingRoomYn(s(rs, "nursing_room_yn"));
        s.setInfoCenterYn(s(rs, "info_center_yn"));
        s.setLatitude(d(rs, "latitude"));
        s.setLongitude(d(rs, "longitude"));
        s.setBaseDt(date(rs, "base_dt"));
        s.setDistance(distance(rs));
        return s;
    };

    private static final RowMapper<TransitRailElevator> ELEVATOR = (rs, n) -> {
        TransitRailElevator e = new TransitRailElevator();
        e.setEvId(i(rs, "ev_id"));
        e.setOperOrg(s(rs, "oper_org"));
        e.setLineName(s(rs, "line_name"));
        e.setStnName(s(rs, "stn_name"));
        e.setUnitSeq(i(rs, "unit_seq"));
        e.setExitNo(s(rs, "exit_no"));
        e.setDetailLoc(s(rs, "detail_loc"));
        e.setCapacityPerson(i(rs, "capacity_person"));
        e.setCapacityKg(i(rs, "capacity_kg"));
        e.setBaseDt(date(rs, "base_dt"));
        return e;
    };

    private static final RowMapper<TransitRailPlatform> PLATFORM = (rs, n) -> {
        TransitRailPlatform p = new TransitRailPlatform();
        p.setPlatformId(i(rs, "platform_id"));
        p.setOperOrg(s(rs, "oper_org"));
        p.setLineName(s(rs, "line_name"));
        p.setStnName(s(rs, "stn_name"));
        p.setPlatformNo(s(rs, "platform_no"));
        p.setUpdown(s(rs, "updown"));
        p.setGroundDv(s(rs, "ground_dv"));
        p.setFloorNo(s(rs, "floor_no"));
        p.setPlatformConnectYn(s(rs, "platform_connect_yn"));
        p.setScreenDoorYn(s(rs, "screen_door_yn"));
        p.setSafetyPlateYn(s(rs, "safety_plate_yn"));
        p.setGapMinCm(rs.getBigDecimal("gap_min_cm"));
        p.setGapMaxCm(rs.getBigDecimal("gap_max_cm"));
        p.setGapAvgCm(rs.getBigDecimal("gap_avg_cm"));
        p.setDoorCnt(i(rs, "door_cnt"));
        p.setBaseDt(date(rs, "base_dt"));
        return p;
    };

    private static final RowMapper<TransitRailToilet> TOILET = (rs, n) -> {
        TransitRailToilet t = new TransitRailToilet();
        t.setToiletId(i(rs, "toilet_id"));
        t.setOperOrg(s(rs, "oper_org"));
        t.setLineName(s(rs, "line_name"));
        t.setStnName(s(rs, "stn_name"));
        t.setDisabledYn(s(rs, "disabled_yn"));
        t.setUnitSeq(i(rs, "unit_seq"));
        t.setGroundDv(s(rs, "ground_dv"));
        t.setFloorNo(s(rs, "floor_no"));
        t.setGateInout(s(rs, "gate_inout"));
        t.setExitNo(s(rs, "exit_no"));
        t.setDetailLoc(s(rs, "detail_loc"));
        t.setToiletKind(s(rs, "toilet_kind"));
        t.setBaseDt(date(rs, "base_dt"));
        return t;
    };

    private static final RowMapper<TransitRailWheelchairLift> LIFT = (rs, n) -> {
        TransitRailWheelchairLift l = new TransitRailWheelchairLift();
        l.setLiftId(i(rs, "lift_id"));
        l.setOperOrg(s(rs, "oper_org"));
        l.setLineName(s(rs, "line_name"));
        l.setStnName(s(rs, "stn_name"));
        l.setMngNo(s(rs, "mng_no"));
        l.setExitNo(s(rs, "exit_no"));
        l.setDetailLoc(s(rs, "detail_loc"));
        l.setLengthMm(i(rs, "length_mm"));
        l.setWidthMm(i(rs, "width_mm"));
        l.setStartFloor(s(rs, "start_floor"));
        l.setEndFloor(s(rs, "end_floor"));
        l.setBaseDt(date(rs, "base_dt"));
        return l;
    };

    private final JdbcReadSupport db;

    public TransitRailRepository(JdbcReadSupport db) {
        this.db = db;
    }

    public Optional<TransitRailStation> findStation(Integer stationId) {
        List<Object> p = new ArrayList<>();
        p.add(stationId);
        return db.one("SELECT " + STATION_COLUMNS + STATION_FROM + "AND station_id = ?", p, STATION);
    }

    public PoiPageResult<TransitRailStation> searchStations(String stnName, String lineName, int offset, int size) {
        StringBuilder w = new StringBuilder(STATION_FROM);
        List<Object> p = new ArrayList<>();
        nameLine(w, p, stnName, lineName);
        return db.page(STATION_COLUMNS, w.toString(), p, "ORDER BY stn_name, station_id", offset, size, STATION);
    }

    public PoiPageResult<TransitRailStation> searchStationsByLocation(double lat, double lng, double radius,
                                                                      int offset, int size) {
        return db.locationPage(STATION_COLUMNS, STATION_FROM, new ArrayList<>(), "station_id", lat, lng, radius, offset, size, STATION);
    }

    public PoiPageResult<TransitRailElevator> elevators(String stnName, String lineName, int offset, int size) {
        return unit("ev_id, oper_org, line_name, stn_name, unit_seq, exit_no, detail_loc, capacity_person, capacity_kg, base_dt",
                "poi_station_elevator_unit", "ORDER BY stn_name, line_name, unit_seq", stnName, lineName, null,
                offset, size, ELEVATOR);
    }

    public PoiPageResult<TransitRailPlatform> platforms(String stnName, String lineName, int offset, int size) {
        return unit("platform_id, oper_org, line_name, stn_name, platform_no, updown, ground_dv, floor_no, " +
                        "platform_connect_yn, screen_door_yn, safety_plate_yn, gap_min_cm, gap_max_cm, gap_avg_cm, door_cnt, base_dt",
                "poi_station_platform", "ORDER BY stn_name, line_name, platform_no", stnName, lineName, null,
                offset, size, PLATFORM);
    }

    public PoiPageResult<TransitRailToilet> toilets(String stnName, String lineName, String disabledYn, int offset, int size) {
        return unit("toilet_id, oper_org, line_name, stn_name, disabled_yn, unit_seq, ground_dv, floor_no, gate_inout, " +
                        "exit_no, detail_loc, toilet_kind, base_dt",
                "poi_station_toilet_unit", "ORDER BY stn_name, line_name, disabled_yn DESC, unit_seq", stnName, lineName,
                disabledYn, offset, size, TOILET);
    }

    public PoiPageResult<TransitRailWheelchairLift> wheelchairLifts(String stnName, String lineName, int offset, int size) {
        return unit("lift_id, oper_org, line_name, stn_name, mng_no, exit_no, detail_loc, length_mm, width_mm, " +
                        "start_floor, end_floor, base_dt",
                "poi_station_wheelchair_lift", "ORDER BY stn_name, line_name, mng_no", stnName, lineName, null,
                offset, size, LIFT);
    }

    private <T> PoiPageResult<T> unit(String columns, String table, String orderBy, String stnName, String lineName,
                                      String disabledYn, int offset, int size, RowMapper<T> mapper) {
        StringBuilder w = new StringBuilder(" FROM " + table + " WHERE coalesce(del_yn, 'N') = 'N' ");
        List<Object> p = new ArrayList<>();
        nameLine(w, p, stnName, lineName);
        if (has(disabledYn)) { w.append("AND disabled_yn = ? "); p.add(disabledYn); }
        return db.page(columns, w.toString(), p, orderBy, offset, size, mapper);
    }

    private static void nameLine(StringBuilder w, List<Object> p, String stnName, String lineName) {
        if (has(stnName)) { w.append("AND stn_name ILIKE ? "); p.add(contains(stnName)); }
        if (has(lineName)) { w.append("AND line_name = ? "); p.add(lineName.trim()); }
    }
}
