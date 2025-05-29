package com.sweetk.iitp.api.controller.v1;

import com.sweetk.iitp.api.constant.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1)
@Tag(name = "API V1 - Common", description = "Common API Operations V1")
public class common {

    @GetMapping("/health")
    @Operation(summary = "Check API health status")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/version")
    @Operation(summary = "Get API version information")
    public ResponseEntity<Map<String, String>> getVersion() {
        Map<String, String> version = new HashMap<>();
        version.put("version", "1.0.0");
        version.put("api", "v1");
        return ResponseEntity.ok(version);
    }
}
