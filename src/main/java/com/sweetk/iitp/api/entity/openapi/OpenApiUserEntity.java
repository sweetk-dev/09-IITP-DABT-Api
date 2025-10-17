package com.sweetk.iitp.api.entity.openapi;

import com.sweetk.iitp.api.constant.DataStatusType;
import com.sweetk.iitp.api.constant.converter.DataStatusTypeConverter;
import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "open_api_user")
@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
public class OpenApiUserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "login_id", length = 128, nullable = false, unique = true)
    private String loginId;
    
    @Column(name = "password", length = 60, nullable = false)
    private String password;
    
    @Column(name = "user_name", length = 90, nullable = false)
    private String userName;

    @Convert(converter = DataStatusTypeConverter.class)
    @Column(name = "status", length = 1, nullable = false)
    private DataStatusType status = DataStatusType.ACTIVE;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn = SysConstants.YN_N;

    @Column(name = "latest_key_created_at")
    private OffsetDateTime latestKeyCreatedAt;
    
    @Column(name = "latest_login_at")
    private OffsetDateTime latestLoginAt;

    @Column(name = "affiliation", length = 200)
    private String affiliation;

    @Column(name = "note", length = 600)
    private String note;

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

    public String getRole() {
        return "API_CLIENT";
    }

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = DataStatusType.ACTIVE;
        }
        if (delYn == null) {
            delYn = SysConstants.YN_N;
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
        this.status = DataStatusType.DELETED;
        this.delYn = SysConstants.YN_Y;
    }
} 