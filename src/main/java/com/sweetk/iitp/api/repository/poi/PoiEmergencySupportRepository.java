package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.constant.poi.PoiEmergencySupportType;
import com.sweetk.iitp.api.dto.poi.PoiEmergencySupport;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/**
 * 긴급대응 지원 거점 (poi_emergency_support) 조회.
 *
 * <p>공개 이용허락이 확인된 원천만 제공한다(전국전동휠체어급속충전기표준데이터, 경기도 시군별
 * 보조기기서비스센터 현황, 운영 기관 공지 정리분). 외부 지도 서비스에서 확인한 운영시간 근거가 달린 행은
 * 운영시간·비고를 비워 내보낸다.
 */
@Repository
public class PoiEmergencySupportRepository {

    private static final String PUBLIC_SOURCES = "('STD_WCHAIR_CHARGER', 'GG_ASSIST_REPAIR', 'MANUAL')";

    private static final String COLUMNS =
            "support_id, support_type, sido_code, sgg_name, name, install_desc, addr_road, addr_jibun, zip_code, " +
            "latitude, longitude, tel, homepage, " +
            "CASE WHEN note ILIKE '%Google Places%' THEN NULL ELSE open_hours END AS open_hours, " +
            "CASE WHEN note ILIKE '%Google Places%' THEN NULL ELSE note END AS note, " +
            "source, base_dt";
    private static final String FROM = " FROM poi_emergency_support WHERE coalesce(del_yn, 'N') = 'N' AND source IN "
            + PUBLIC_SOURCES + " ";

    private static final RowMapper<PoiEmergencySupport> MAPPER = (rs, n) -> {
        PoiEmergencySupport f = new PoiEmergencySupport();
        String type = s(rs, "support_type");
        f.setSupportId(i(rs, "support_id"));
        f.setSupportType(type);
        f.setSupportTypeName(PoiEmergencySupportType.displayNameOf(type));
        f.setSidoCode(s(rs, "sido_code"));
        f.setSggName(s(rs, "sgg_name"));
        f.setName(s(rs, "name"));
        f.setInstallDesc(s(rs, "install_desc"));
        f.setAddrRoad(s(rs, "addr_road"));
        f.setAddrJibun(s(rs, "addr_jibun"));
        f.setZipCode(s(rs, "zip_code"));
        f.setLatitude(d(rs, "latitude"));
        f.setLongitude(d(rs, "longitude"));
        f.setTel(s(rs, "tel"));
        f.setHomepage(s(rs, "homepage"));
        f.setOpenHours(s(rs, "open_hours"));
        f.setNote(s(rs, "note"));
        f.setSourceName(sourceName(s(rs, "source")));
        f.setBaseDt(date(rs, "base_dt"));
        f.setDistance(distance(rs));
        return f;
    };

    private static String sourceName(String code) {
        if (code == null) {
            return null;
        }
        return switch (code) {
            case "STD_WCHAIR_CHARGER" -> "전국전동휠체어급속충전기표준데이터(공공데이터포털)";
            case "GG_ASSIST_REPAIR" -> "경기도 시군별 보조기기서비스센터 현황(경기데이터드림)";
            case "MANUAL" -> "운영 기관 공지";
            default -> null;
        };
    }

    private final JdbcReadSupport db;

    public PoiEmergencySupportRepository(JdbcReadSupport db) {
        this.db = db;
    }

    public Optional<PoiEmergencySupport> findById(Integer id) {
        List<Object> p = new ArrayList<>();
        p.add(id);
        return db.one("SELECT " + COLUMNS + FROM + "AND support_id = ?", p, MAPPER);
    }

    public PoiPageResult<PoiEmergencySupport> search(String supportType, String sidoCode, String sggName, String name,
                                                     int offset, int size) {
        StringBuilder w = new StringBuilder(FROM);
        List<Object> p = new ArrayList<>();
        if (has(supportType)) { w.append("AND support_type = ? "); p.add(supportType); }
        if (has(sidoCode)) { w.append("AND sido_code = ? "); p.add(sidoCode); }
        if (has(sggName)) { w.append("AND sgg_name ILIKE ? "); p.add(contains(sggName)); }
        if (has(name)) { w.append("AND name ILIKE ? "); p.add(contains(name)); }
        return db.page(COLUMNS, w.toString(), p, "ORDER BY sido_code, sgg_name, name, support_id", offset, size, MAPPER);
    }

    public PoiPageResult<PoiEmergencySupport> searchByLocation(double lat, double lng, double radius, String supportType,
                                                               int offset, int size) {
        StringBuilder w = new StringBuilder(FROM);
        List<Object> p = new ArrayList<>();
        if (has(supportType)) { w.append("AND support_type = ? "); p.add(supportType); }
        return db.locationPage(COLUMNS, w.toString(), p, "support_id", lat, lng, radius, offset, size, MAPPER);
    }
}
