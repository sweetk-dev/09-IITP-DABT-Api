package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.constant.poi.PoiWelfareFacilityType;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacility;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/**
 * 장애인복지시설 (poi_welfare_facility) 조회 — 경기데이터드림 장애인복지시설 5종.
 * 원천이 기준일을 주지 않아, 적재기가 원천과 대조해 갱신한 날(updated_at)을 확인일로 내보낸다.
 */
@Repository
public class PoiWelfareFacilityRepository {

    private static final String COLUMNS =
            "facility_id, facility_type, sub_type, sido_code, sigun_cd, sigun_nm, name, addr_road, addr_jibun, zip_code, " +
            "latitude, longitude, tel, homepage, capacity_cnt, current_cnt, staff_capacity_cnt, staff_current_cnt, " +
            "install_report_dt, corp_name, source, coalesce(updated_at, created_at)::date AS checked_dt";
    private static final String FROM = " FROM poi_welfare_facility WHERE del_yn = 'N' ";

    private static final RowMapper<PoiWelfareFacility> MAPPER = (rs, n) -> {
        PoiWelfareFacility f = new PoiWelfareFacility();
        String type = s(rs, "facility_type");
        f.setFacilityId(i(rs, "facility_id"));
        f.setFacilityType(type);
        f.setFacilityTypeName(PoiWelfareFacilityType.displayNameOf(type));
        f.setSubType(s(rs, "sub_type"));
        f.setSidoCode(s(rs, "sido_code"));
        f.setSigunCd(s(rs, "sigun_cd"));
        f.setSigunName(s(rs, "sigun_nm"));
        f.setName(s(rs, "name"));
        f.setAddrRoad(s(rs, "addr_road"));
        f.setAddrJibun(s(rs, "addr_jibun"));
        f.setZipCode(s(rs, "zip_code"));
        f.setLatitude(d(rs, "latitude"));
        f.setLongitude(d(rs, "longitude"));
        f.setTel(s(rs, "tel"));
        f.setHomepage(s(rs, "homepage"));
        f.setCapacityCnt(i(rs, "capacity_cnt"));
        f.setCurrentCnt(i(rs, "current_cnt"));
        f.setStaffCapacityCnt(i(rs, "staff_capacity_cnt"));
        f.setStaffCurrentCnt(i(rs, "staff_current_cnt"));
        f.setInstallReportDt(date(rs, "install_report_dt"));
        f.setCorpName(s(rs, "corp_name"));
        f.setSourceName("GG_WELFARE".equals(s(rs, "source")) ? "경기도 장애인복지시설 현황(경기데이터드림)" : null);
        f.setCheckedDt(date(rs, "checked_dt"));
        f.setDistance(distance(rs));
        return f;
    };

    private final JdbcReadSupport db;

    public PoiWelfareFacilityRepository(JdbcReadSupport db) {
        this.db = db;
    }

    public Optional<PoiWelfareFacility> findById(Integer id) {
        List<Object> p = new ArrayList<>();
        p.add(id);
        return db.one("SELECT " + COLUMNS + FROM + "AND facility_id = ?", p, MAPPER);
    }

    public PoiPageResult<PoiWelfareFacility> search(String facilityType, String sigunName, String subType, String name,
                                                    int offset, int size) {
        StringBuilder w = new StringBuilder(FROM);
        List<Object> p = new ArrayList<>();
        if (has(facilityType)) { w.append("AND facility_type = ? "); p.add(facilityType); }
        if (has(sigunName)) { w.append("AND sigun_nm ILIKE ? "); p.add(contains(sigunName)); }
        if (has(subType)) { w.append("AND sub_type ILIKE ? "); p.add(contains(subType)); }
        if (has(name)) { w.append("AND name ILIKE ? "); p.add(contains(name)); }
        return db.page(COLUMNS, w.toString(), p, "ORDER BY facility_type, sigun_nm, name, facility_id", offset, size, MAPPER);
    }

    public PoiPageResult<PoiWelfareFacility> searchByLocation(double lat, double lng, double radius, String facilityType,
                                                              int offset, int size) {
        StringBuilder w = new StringBuilder(FROM);
        List<Object> p = new ArrayList<>();
        if (has(facilityType)) { w.append("AND facility_type = ? "); p.add(facilityType); }
        return db.locationPage(COLUMNS, w.toString(), p, "facility_id", lat, lng, radius, offset, size, MAPPER);
    }
}
