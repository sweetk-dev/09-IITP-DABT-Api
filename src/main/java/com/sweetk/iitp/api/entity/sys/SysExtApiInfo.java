package com.sweetk.iitp.api.entity.sys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_ext_api_info")
@Getter
@Setter
@NoArgsConstructor
public class SysExtApiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ext_api_id")
    private Integer extApiId;

    @Column(name = "if_name", length = 60, nullable = false)
    private String ifName;

    @Column(name = "ext_sys", length = 20, nullable = false)
    private String extSys;

    @Column(name = "ext_url", length = 300, nullable = false)
    private String extUrl;

    @Column(name = "auth", length = 200)
    private String auth;

    @Column(name = "data_format", length = 20)
    private String dataFormat;

    @Column(name = "last_sync_time")
    private LocalDateTime lastSyncTime;

    @Column(name = "memo", length = 100)
    private String memo;

    @Column(name = "status", length = 1, nullable = false)
    private String status;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", length = 40)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;
}