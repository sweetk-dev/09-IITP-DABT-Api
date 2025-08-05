package com.sweetk.iitp.api.service.sys;

import com.sweetk.iitp.api.dto.sys.SysCommonCodeDto;
import com.sweetk.iitp.api.dto.sys.converter.SysCommonCodeConverter;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeEntity;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeIdEntity;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.sys.SysCommonCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SysCommonCodeService {

    private final SysCommonCodeRepository sysCommonCodeRepository;

    /**
     * 그룹 ID로 공통 코드 조회 (사용 중인 것만, 캐시 적용)
     */
    @Cacheable(value = "commonCodes", key = "#grpId")
    public List<SysCommonCodeDto> getCommonCodesByGrpId(String grpId) {
        log.debug("공통 코드 조회 요청 - 그룹 ID: {}", grpId);
        
        try {
            List<SysCommonCodeEntity> entities = sysCommonCodeRepository.findByGrpIdAndUseYn(grpId);
            return entities.stream()
                    .map(SysCommonCodeConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("공통 코드 조회 중 오류 발생 - 그룹 ID: {}, 오류: {}", grpId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 그룹 ID와 코드 타입으로 공통 코드 조회 (사용 중인 것만, 캐시 적용)
     */
    @Cacheable(value = "commonCodes", key = "#grpId + '_' + #codeType")
    public List<SysCommonCodeDto> getCommonCodesByGrpIdAndCodeType(String grpId, String codeType) {
        log.debug("공통 코드 조회 요청 - 그룹 ID: {}, 코드 타입: {}", grpId, codeType);
        
        try {
            List<SysCommonCodeEntity> entities = sysCommonCodeRepository.findByGrpIdAndCodeTypeAndUseYn(grpId, codeType);
            return entities.stream()
                    .map(SysCommonCodeConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("공통 코드 조회 중 오류 발생 - 그룹 ID: {}, 코드 타입: {}, 오류: {}", grpId, codeType, e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 그룹 ID로 공통 코드를 Map으로 조회 (코드ID -> 코드명)
     */
    @Cacheable(value = "commonCodeMaps", key = "#grpId")
    public Map<String, String> getCommonCodeMapByGrpId(String grpId) {
        log.debug("공통 코드 Map 조회 요청 - 그룹 ID: {}", grpId);
        
        List<SysCommonCodeDto> codes = getCommonCodesByGrpId(grpId);
        return codes.stream()
                .collect(Collectors.toMap(
                        SysCommonCodeDto::getCodeId,
                        SysCommonCodeDto::getCodeNm,
                        (existing, replacement) -> existing // 중복 시 기존 값 유지
                ));
    }

    /**
     * 특정 코드 ID의 코드명 조회
     */
    public String getCodeName(String grpId, String codeId) {
        log.debug("코드명 조회 요청 - 그룹 ID: {}, 코드 ID: {}", grpId, codeId);
        
        Map<String, String> codeMap = getCommonCodeMapByGrpId(grpId);
        return codeMap.getOrDefault(codeId, "");
    }

    /**
     * 코드 ID가 유효한지 검증
     */
    public boolean isValidCode(String grpId, String codeId) {
        log.debug("코드 유효성 검증 - 그룹 ID: {}, 코드 ID: {}", grpId, codeId);
        
        Map<String, String> codeMap = getCommonCodeMapByGrpId(grpId);
        return codeMap.containsKey(codeId);
    }

    /**
     * 그룹 ID로 모든 공통 코드 조회 (삭제된 것 포함)
     */
    public List<SysCommonCodeDto> getAllCommonCodesByGrpId(String grpId) {
        log.debug("전체 공통 코드 조회 요청 - 그룹 ID: {}", grpId);
        
        try {
            List<SysCommonCodeEntity> entities = sysCommonCodeRepository.findByGrpId(grpId);
            return entities.stream()
                    .map(SysCommonCodeConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("전체 공통 코드 조회 중 오류 발생 - 그룹 ID: {}, 오류: {}", grpId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 특정 코드 상세 정보 조회
     */
    public Optional<SysCommonCodeDto> getCommonCodeById(String grpId, String codeId) {
        log.debug("공통 코드 상세 조회 요청 - 그룹 ID: {}, 코드 ID: {}", grpId, codeId);
        
        try {
            SysCommonCodeIdEntity id = SysCommonCodeIdEntity.builder()
                    .grpId(grpId)
                    .codeId(codeId)
                    .build();
            
            return sysCommonCodeRepository.findById(id)
                    .map(SysCommonCodeConverter::toDto);
        } catch (Exception e) {
            log.error("공통 코드 상세 조회 중 오류 발생 - 그룹 ID: {}, 코드 ID: {}, 오류: {}", grpId, codeId, e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
} 