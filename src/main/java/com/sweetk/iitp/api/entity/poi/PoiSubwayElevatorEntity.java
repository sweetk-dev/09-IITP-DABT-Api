package com.sweetk.iitp.api.entity.poi;

import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "poi_subway_elevator")
@Getter
@Setter
@NoArgsConstructor
public class PoiSubwayElevatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subway_id")
    private Integer subwayId;

    @Column(name = "sido_code", nullable = false, length = 12)
    private String sidoCode;

    @Column(name = "node_link_type", nullable = false, length = 20)
    private String nodeLinkType;

    @Column(name = "node_wkt", length = 100)
    private String nodeWkt;

    @Column(name = "node_id", nullable = false)
    private Long nodeId;

    @Column(name = "node_type_code", nullable = false)
    private Integer nodeTypeCode;

    @Column(name = "node_type_name", nullable = false, length = 60)
    private String nodeTypeName;

    @Column(name = "sigungu_code", length = 16)
    private String sigunguCode;

    @Column(name = "sigungu_name", length = 100)
    private String sigunguName;

    @Column(name = "eupmyeondong_code", length = 16)
    private String eupmyeondongCode;

    @Column(name = "eupmyeondong_name", length = 100)
    private String eupmyeondongName;

    @Column(name = "station_code", length = 16)
    private String stationCode;

    @Column(name = "station_name", length = 120)
    private String stationName;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "base_dt", length = 12)
    private String baseDt;

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