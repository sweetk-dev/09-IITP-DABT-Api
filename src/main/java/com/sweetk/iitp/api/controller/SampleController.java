package com.sweetk.iitp.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {

    /**
     * GET 요청 처리 예시
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getSample(@PathVariable Long id) {
        // 요청 시작 로깅
        log.info("샘플 데이터 조회 요청 - ID: {}", id);
        
        try {
            // 비즈니스 로직 실행
            Map<String, Object> result = new HashMap<>();
            result.put("id", id);
            result.put("name", "Sample Data");
            
            // 상세 정보는 DEBUG 레벨로 로깅
            log.debug("조회된 데이터: {}", result);
            
            // 성공 로깅
            log.info("샘플 데이터 조회 성공 - ID: {}", id);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            // 에러 로깅 (스택 트레이스 포함)
            log.error("샘플 데이터 조회 실패 - ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST 요청 처리 예시
     */
    @PostMapping
    public ResponseEntity<?> createSample(@RequestBody Map<String, Object> request) {
        // 요청 데이터 로깅 (민감 정보 제외)
        log.info("샘플 데이터 생성 요청 - 요청 데이터: {}", request);
        
        try {
            // 비즈니스 로직 실행
            Map<String, Object> result = new HashMap<>();
            result.put("id", 1L);
            result.put("status", "created");
            
            // 성공 로깅
            log.info("샘플 데이터 생성 성공 - 생성된 ID: {}", result.get("id"));
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            // 에러 로깅
            log.error("샘플 데이터 생성 실패 - 요청 데이터: {}, 에러: {}", request, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * PUT 요청 처리 예시
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSample(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        // 요청 시작 로깅
        log.info("샘플 데이터 수정 요청 - ID: {}, 수정 데이터: {}", id, request);
        
        try {
            // 비즈니스 로직 실행
            Map<String, Object> result = new HashMap<>();
            result.put("id", id);
            result.put("status", "updated");
            
            // 성공 로깅
            log.info("샘플 데이터 수정 성공 - ID: {}", id);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            // 에러 로깅
            log.error("샘플 데이터 수정 실패 - ID: {}, 수정 데이터: {}, 에러: {}", 
                     id, request, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * DELETE 요청 처리 예시
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSample(@PathVariable Long id) {
        // 요청 시작 로깅
        log.info("샘플 데이터 삭제 요청 - ID: {}", id);
        
        try {
            // 비즈니스 로직 실행
            Map<String, Object> result = new HashMap<>();
            result.put("id", id);
            result.put("status", "deleted");
            
            // 성공 로깅
            log.info("샘플 데이터 삭제 성공 - ID: {}", id);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            // 에러 로깅
            log.error("샘플 데이터 삭제 실패 - ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 