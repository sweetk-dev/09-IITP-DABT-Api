package com.sweetk.iitp.api.entity.openapi;

import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "open_api_auth_key")
@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
public class OpenApiAuthKeyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long keyId;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "auth_key", length = 60, nullable = false, unique = true)
    private String authKey;

    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYn = SysConstants.YN_Y;

    @Column(name = "start_dt")
    private LocalDate startDt;
    
    @Column(name = "end_dt")
    private LocalDate endDt;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn = SysConstants.YN_N;
    
    @Column(name = "key_name", length = 120, nullable = false)
    private String keyName;
    
    @Column(name = "key_desc", length = 600, nullable = false)
    private String keyDesc;
    
    @Column(name = "key_reject_reason", length = 600)
    private String keyRejectReason;
    
    @Column(name = "active_at")
    private OffsetDateTime activeAt;
    
    @Column(name = "latest_acc_at")
    private OffsetDateTime latestAccAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "created_by", length = 40, nullable = false)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private OpenApiUserEntity openApiUser;

    @PrePersist
    protected void onCreate() {
        if (delYn == null) {
            delYn = SysConstants.YN_N;
        }
        if (activeYn == null) {
            activeYn = SysConstants.YN_Y;
        }
        if (createdBy == null) {
            createdBy = "SYS-BATCH";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (updatedBy == null && deletedAt == null) {
            updatedBy = "SYS-BATCH";
        }
    }

    public void softDelete(String deletedBy) {
        this.deletedAt = OffsetDateTime.now();
        this.deletedBy = deletedBy;
        this.delYn = SysConstants.YN_Y;
        this.activeYn = SysConstants.YN_N;
    }
} 