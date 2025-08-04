package com.sweetk.iitp.api.entity.poi;

import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "poi_tour_bf_facility")
@Getter
@Setter
@NoArgsConstructor
public class PoiTourBfFacilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fclt_id")
    private Integer fcltId;

    @Column(name = "sido_code", nullable = false, length = 12)
    private String sidoCode;

    @Column(name = "fclt_name", nullable = false, length = 200)
    private String fcltName;

    @Column(name = "toilet_yn", length = 1)
    private String toiletYn;

    @Column(name = "elevator_yn", length = 1)
    private String elevatorYn;

    @Column(name = "parking_yn", length = 1)
    private String parkingYn;

    @Column(name = "slope_yn", length = 1)
    private String slopeYn;

    @Column(name = "subway_yn", length = 1)
    private String subwayYn;

    @Column(name = "bus_stop_yn", length = 1)
    private String busStopYn;

    @Column(name = "wheelchair_rent_yn", length = 1)
    private String wheelchairRentYn;

    @Column(name = "tactile_map_yn", length = 1)
    private String tactileMapYn;

    @Column(name = "audio_guide_yn", length = 1)
    private String audioGuideYn;

    @Column(name = "nursing_room_yn", length = 1)
    private String nursingRoomYn;

    @Column(name = "accessible_room_yn", length = 1)
    private String accessibleRoomYn;

    @Column(name = "stroller_rent_yn", length = 1)
    private String strollerRentYn;

    @Column(name = "addr_road", length = 600)
    private String addrRoad;

    @Column(name = "addr_jibun", length = 600)
    private String addrJibun;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "base_dt")
    private LocalDate baseDt;

    @Column(name = "del_yn", length = 1)
    private String delYn = SysConstants.YN_N;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", nullable = false, length = 40)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 