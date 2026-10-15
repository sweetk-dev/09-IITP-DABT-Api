package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibility;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/** 건물 장애인편의시설 (poi_facility_accessibility) 조회 */
@Repository
public class PoiBuildingAccessibilityRepository {

    private static final String COLUMNS =
            "facl_id, facl_inf_id, wfclt_id, facl_name, facl_type, addr, latitude, longitude, estb_date, " +
            "elevator_yn, dis_toilet_yn, dis_parking_yn, entrance_ramp_yn, entrance_door_yn, approach_road_yn, " +
            "guide_facility_yn, accessible_room_yn, eval_info_raw, base_dt";
    private static final String FROM = " FROM poi_facility_accessibility WHERE coalesce(del_yn, 'N') = 'N' ";

    private static final RowMapper<PoiBuildingAccessibility> MAPPER = (rs, n) -> {
        PoiBuildingAccessibility f = new PoiBuildingAccessibility();
        f.setFaclId(i(rs, "facl_id"));
        f.setFaclInfId(s(rs, "facl_inf_id"));
        f.setWfcltId(s(rs, "wfclt_id"));
        f.setFaclName(s(rs, "facl_name"));
        f.setFaclType(s(rs, "facl_type"));
        f.setAddr(s(rs, "addr"));
        f.setLatitude(d(rs, "latitude"));
        f.setLongitude(d(rs, "longitude"));
        f.setEstbDate(s(rs, "estb_date"));
        f.setElevatorYn(s(rs, "elevator_yn"));
        f.setDisToiletYn(s(rs, "dis_toilet_yn"));
        f.setDisParkingYn(s(rs, "dis_parking_yn"));
        f.setEntranceRampYn(s(rs, "entrance_ramp_yn"));
        f.setEntranceDoorYn(s(rs, "entrance_door_yn"));
        f.setApproachRoadYn(s(rs, "approach_road_yn"));
        f.setGuideFacilityYn(s(rs, "guide_facility_yn"));
        f.setAccessibleRoomYn(s(rs, "accessible_room_yn"));
        f.setEvalInfo(s(rs, "eval_info_raw"));
        f.setBaseDt(date(rs, "base_dt"));
        f.setDistance(distance(rs));
        return f;
    };

    private final JdbcReadSupport db;

    public PoiBuildingAccessibilityRepository(JdbcReadSupport db) {
        this.db = db;
    }

    public Optional<PoiBuildingAccessibility> findById(Integer id) {
        List<Object> p = new ArrayList<>();
        p.add(id);
        return db.one("SELECT " + COLUMNS + FROM + "AND facl_id = ?", p, MAPPER);
    }

    public PoiPageResult<PoiBuildingAccessibility> search(String faclName, String faclType, String addr,
                                                          String elevatorYn, String disToiletYn, String disParkingYn,
                                                          String entranceRampYn, int offset, int size) {
        StringBuilder w = new StringBuilder(FROM);
        List<Object> p = new ArrayList<>();
        conditions(w, p, faclName, faclType, addr, elevatorYn, disToiletYn, disParkingYn, entranceRampYn);
        return db.page(COLUMNS, w.toString(), p, "ORDER BY facl_name, facl_id", offset, size, MAPPER);
    }

    public PoiPageResult<PoiBuildingAccessibility> searchByLocation(double lat, double lng, double radius,
                                                                    String faclType, String elevatorYn,
                                                                    String disToiletYn, String disParkingYn,
                                                                    int offset, int size) {
        StringBuilder w = new StringBuilder(FROM);
        List<Object> p = new ArrayList<>();
        conditions(w, p, null, faclType, null, elevatorYn, disToiletYn, disParkingYn, null);
        return db.locationPage(COLUMNS, w.toString(), p, "facl_id", lat, lng, radius, offset, size, MAPPER);
    }

    private static void conditions(StringBuilder w, List<Object> p, String faclName, String faclType, String addr,
                                   String elevatorYn, String disToiletYn, String disParkingYn, String entranceRampYn) {
        if (has(faclName)) { w.append("AND facl_name ILIKE ? "); p.add(contains(faclName)); }
        if (has(faclType)) { w.append("AND facl_type ILIKE ? "); p.add(contains(faclType)); }
        if (has(addr)) { w.append("AND addr ILIKE ? "); p.add(contains(addr)); }
        if (has(elevatorYn)) { w.append("AND elevator_yn = ? "); p.add(elevatorYn); }
        if (has(disToiletYn)) { w.append("AND dis_toilet_yn = ? "); p.add(disToiletYn); }
        if (has(disParkingYn)) { w.append("AND dis_parking_yn = ? "); p.add(disParkingYn); }
        if (has(entranceRampYn)) { w.append("AND entrance_ramp_yn = ? "); p.add(entranceRampYn); }
    }
}
