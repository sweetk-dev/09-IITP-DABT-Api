package com.sweetk.iitp.api.entity.poi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * 장애인 문화·체육 시설 (poi_culture_sports_facility)
 *
 * <p>적재는 수집 배치가 맡고 이 API 는 읽기만 한다. 조회는 저장소 구현의 SQL 로 하며,
 * 이 엔티티는 저장소 타입 지정용이다.
 */
@Entity
@Immutable
@Table(name = "poi_culture_sports_facility")
@Getter
@NoArgsConstructor
public class PoiCultureSportsFacilityEntity {

    @Id
    @Column(name = "facility_id")
    private Integer facilityId;

    @Column(name = "facility_type", nullable = false, length = 20)
    private String facilityType;

    @Column(name = "sido_code", length = 10)
    private String sidoCode;

    @Column(name = "sigun_cd", length = 10)
    private String sigunCd;

    @Column(name = "sigun_nm", length = 40)
    private String sigunNm;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "addr_road", length = 600)
    private String addrRoad;

    @Column(name = "addr_jibun", length = 600)
    private String addrJibun;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "tel", length = 40)
    private String tel;

    @Column(name = "homepage", length = 400)
    private String homepage;

    @Column(name = "install_report_dt")
    private LocalDate installReportDt;

    @Column(name = "corp_name", length = 200)
    private String corpName;

    @Column(name = "source", length = 40)
    private String source;

    @Column(name = "del_yn", length = 1)
    private String delYn;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
