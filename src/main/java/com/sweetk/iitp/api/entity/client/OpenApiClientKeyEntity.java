package com.sweetk.iitp.api.entity.client;

import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "open_api_client_key")
@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
public class OpenApiClientKeyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "api_cli_id", nullable = false)
    private Integer apiCliId;
    
    @Column(name = "api_key", length = 60, nullable = false, unique = true)
    private String apiKey;

    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYn = SysConstants.YN_Y;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn = SysConstants.YN_N;
    
    @Column(name = "key_active_at")
    private OffsetDateTime keyActiveAt;
    
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

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_cli_id", insertable = false, updatable = false)
    private OpenApiClientEntity openApiClient;

    @PrePersist
    protected void onCreate() {
        delYn = SysConstants.YN_N;
        activeYn = SysConstants.YN_Y;
    }

    public void softDelete(String deletedBy) {
        this.deletedAt = OffsetDateTime.now();
        this.deletedBy = deletedBy;
        this.delYn = SysConstants.YN_Y;
        this.activeYn = SysConstants.YN_N;
    }
} 