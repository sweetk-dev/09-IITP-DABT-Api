package com.sweetk.iitp.api.service.example;

import com.sweetk.iitp.api.dto.sys.SysCommonCodeDto;
import com.sweetk.iitp.api.service.sys.SysCommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 공통 코드 서비스 사용 예시
 * 다른 서비스에서 이와 같은 방식으로 공통 코드를 활용할 수 있습니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCodeUsageExample {

    private final SysCommonCodeService sysCommonCodeService;

    /**
     * 예시: 성별 코드 조회
     */
    public void getGenderCodes() {
        // 성별 코드 목록 조회
        List<SysCommonCodeDto> genderCodes = sysCommonCodeService.getCommonCodesByGrpId("GENDER");
        
        // 성별 코드 Map 조회 (코드ID -> 코드명)
        Map<String, String> genderCodeMap = sysCommonCodeService.getCommonCodeMapByGrpId("GENDER");
        
        // 특정 코드명 조회
        String maleName = sysCommonCodeService.getCodeName("GENDER", "M"); // "남성"
        String femaleName = sysCommonCodeService.getCodeName("GENDER", "F"); // "여성"
        
        // 코드 유효성 검증
        boolean isValidMale = sysCommonCodeService.isValidCode("GENDER", "M"); // true
        boolean isValidInvalid = sysCommonCodeService.isValidCode("GENDER", "X"); // false
    }

    /**
     * 예시: 지역 코드 조회
     */
    public void getRegionCodes() {
        // 지역 코드 목록 조회 (서비스용 코드만)
        List<SysCommonCodeDto> regionCodes = sysCommonCodeService.getCommonCodesByGrpIdAndCodeType("REGION", "B");
        
        // 지역 코드 Map 조회
        Map<String, String> regionCodeMap = sysCommonCodeService.getCommonCodeMapByGrpId("REGION");
        
        // 서울 코드명 조회
        String seoulName = sysCommonCodeService.getCodeName("REGION", "1100000"); // "서울특별시"
    }

    /**
     * 예시: 화장실 유형 코드 조회
     */
    public void getToiletTypeCodes() {
        // 화장실 유형 코드 목록 조회
        List<SysCommonCodeDto> toiletTypeCodes = sysCommonCodeService.getCommonCodesByGrpId("TOILET_TYPE");
        
        // 화장실 유형 코드 Map 조회
        Map<String, String> toiletTypeMap = sysCommonCodeService.getCommonCodeMapByGrpId("TOILET_TYPE");
        
        // 공중화장실 코드명 조회
        String publicToiletName = sysCommonCodeService.getCodeName("TOILET_TYPE", "PUBLIC"); // "공중화장실"
    }

    /**
     * 예시: 코드 유효성 검증 후 처리
     */
    public void validateAndProcessCode(String grpId, String codeId) {
        // 코드 유효성 검증
        if (!sysCommonCodeService.isValidCode(grpId, codeId)) {
            log.warn("유효하지 않은 코드 - 그룹 ID: {}, 코드 ID: {}", grpId, codeId);
            return;
        }
        
        // 유효한 코드인 경우 처리 로직
        String codeName = sysCommonCodeService.getCodeName(grpId, codeId);
        log.info("유효한 코드 처리 - 그룹 ID: {}, 코드 ID: {}, 코드명: {}", grpId, codeId, codeName);
        
        // 비즈니스 로직 처리...
    }
} 