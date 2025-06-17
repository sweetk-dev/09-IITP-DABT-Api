package com.sweetk.iitp.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_clients")
@Getter
@Setter
@NoArgsConstructor
public class ApiClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean active = false;

    @Column(name = "client_id", length = 40, unique = true, nullable = false)
    private String clientId;

    @Column(name = "api_key", length = 60, unique = true, nullable = false)
    private String apiKey;

    @Column(name = "client_name", length = 160, nullable = false)
    private String clientName;

    @Column(length = 600)
    private String description;


    @Column(length = 600)
    private String note;


//    @Column(name = "api_usage_start_time")
//    @Comment("API 사용 시작 시간")
//    private LocalDateTime apiUsageStartTime;
//
//    @Column(name = "api_usage_end_time")
//    private LocalDateTime apiUsageEndTime;

    @Column(name = "approval_at")
    private LocalDateTime approvalAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}