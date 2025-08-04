package com.sweetk.iitp.api.entity.poi;

import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "poi_public_toilet_info")
@Getter
@Setter
@NoArgsConstructor
public class PoiPublicToiletInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toilet_id")
    private Integer toiletId;

    @Column(name = "sido_code", nullable = false, length = 12)
    private String sidoCode;

    @Column(name = "toilet_name", nullable = false, length = 300)
    private String toiletName;

    @Column(name = "toilet_type", nullable = false, length = 50)
    private String toiletType;

    @Column(name = "basis", length = 300)
    private String basis;

    @Column(name = "addr_road", length = 600)
    private String addrRoad;

    @Column(name = "addr_jibun", length = 600)
    private String addrJibun;

    @Column(name = "m_toilet_count")
    private Integer mToiletCount;

    @Column(name = "m_urinal_count")
    private Integer mUrinalCount;

    @Column(name = "m_dis_toilet_count")
    private Integer mDisToiletCount;

    @Column(name = "m_dis_urinal_count")
    private Integer mDisUrinalCount;

    @Column(name = "m_child_toilet_count")
    private Integer mChildToiletCount;

    @Column(name = "m_child_urinal_count")
    private Integer mChildUrinalCount;

    @Column(name = "f_toilet_count")
    private Integer fToiletCount;

    @Column(name = "f_dis_toilet_count")
    private Integer fDisToiletCount;

    @Column(name = "f_child_toilet_count")
    private Integer fChildToiletCount;

    @Column(name = "managing_org", length = 300)
    private String managingOrg;

    @Column(name = "phone_number", length = 32)
    private String phoneNumber;

    @Column(name = "open_time", length = 100)
    private String openTime;

    @Column(name = "open_time_detail", length = 300)
    private String openTimeDetail;

    @Column(name = "install_dt", length = 10)
    private String installDt;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "owner_type", length = 300)
    private String ownerType;

    @Column(name = "waste_process_type", length = 100)
    private String wasteProcessType;

    @Column(name = "safety_target_yn", length = 1)
    private String safetyTargetYn;

    @Column(name = "emg_bell_yn", length = 1)
    private String emgBellYn;

    @Column(name = "emg_bell_location", length = 300)
    private String emgBellLocation;

    @Column(name = "cctv_yn", length = 1)
    private String cctvYn;

    @Column(name = "diaper_table_yn", length = 1)
    private String diaperTableYn;

    @Column(name = "diaper_table_location", length = 300)
    private String diaperTableLocation;

    @Column(name = "remodeled_dt", length = 12)
    private String remodeledDt;

    @Column(name = "base_dt")
    private LocalDate baseDt;

    @Column(name = "del_yn", length = 1)
    private String delYn = SysConstants.YN_N;

    @Column(name = "created_at", nullable = false)
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