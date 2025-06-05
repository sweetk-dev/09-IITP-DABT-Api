package com.sweetk.iitp.api.entity.poi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mv_poi")
@Getter
@Setter
@NoArgsConstructor
public class MvPoiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poi_id")
    private Long poiId;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "basic_info")
    private String basicInfo;

    @Column(name = "address_code", length = 10)
    private String addressCode;

    @Column(name = "address_road", length = 200)
    private String addressRoad;

    @Column(name = "address_detail", length = 200)
    private String addressDetail;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "detail_json", columnDefinition = "jsonb")
    private String detailJson;

    @Column(name = "search_filter_json", columnDefinition = "jsonb")
    private String searchFilterJson;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "is_deleted", nullable = false, length = 1)
    private String isDeleted = "N";

    @Column(name = "is_published", nullable = false, length = 1)
    private String isPublished = "N";

    @Column(name = "source_organization", length = 100)
    private String sourceOrganization;

    @Column(name = "source_id", length = 50)
    private String sourceId;
}