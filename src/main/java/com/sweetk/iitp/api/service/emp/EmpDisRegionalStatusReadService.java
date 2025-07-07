package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisRegionalStatusDto;
import com.sweetk.iitp.api.dto.emp.converter.EmpDisRegionalStatusConverter;
import com.sweetk.iitp.api.entity.emp.EmpDisRegionalStatusEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisRegionalStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 지역별 장애인 고용 현황 읽기 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpDisRegionalStatusReadService {

    private final EmpDisRegionalStatusRepository repository;
    private final EmpDisRegionalStatusConverter converter;

    /**
     * 전체 조회
     */
    public List<EmpDisRegionalStatusDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisRegionalStatusDto toDto(EmpDisRegionalStatusEntity e) {
        return EmpDisRegionalStatusDto.builder()
                // 필드 매핑 필요
                .build();
    }

    /**
     * ID로 조회
     */
    public Optional<EmpDisRegionalStatusDto> findById(Integer id) {
        Optional<EmpDisRegionalStatusEntity> entity = repository.findById(id);
        return entity.map(converter::toDto);
    }

    /**
     * 지역별 조회
     */
    public List<EmpDisRegionalStatusDto> findByRegion(String region) {
        List<EmpDisRegionalStatusEntity> entities = repository.findByRegionContainingOrderBySeqNo(region);
        return converter.toDtoList(entities);
    }

    /**
     * 고용률 범위별 조회
     */
    public List<EmpDisRegionalStatusDto> findBySevereRateBetween(BigDecimal minRate, BigDecimal maxRate) {
        List<EmpDisRegionalStatusEntity> entities = repository.findBySevereRateBetweenOrderBySevereRateDesc(minRate, maxRate);
        return converter.toDtoList(entities);
    }

    /**
     * 고용률 기준 상위 10개 조회
     */
    public List<EmpDisRegionalStatusDto> findTop10BySevereRate() {
        List<EmpDisRegionalStatusEntity> entities = repository.findTop10ByOrderBySevereRateDesc();
        return converter.toDtoList(entities);
    }

    /**
     * 사업체수 기준 상위 10개 조회
     */
    public List<EmpDisRegionalStatusDto> findTop10ByCompanyCount() {
        List<EmpDisRegionalStatusEntity> entities = repository.findTop10ByOrderByCompanyCountDesc();
        return converter.toDtoList(entities);
    }

    /**
     * 근로자수 기준 상위 10개 조회
     */
    public List<EmpDisRegionalStatusDto> findTop10ByWorkerCount() {
        List<EmpDisRegionalStatusEntity> entities = repository.findTop10ByOrderByWorkerCountDesc();
        return converter.toDtoList(entities);
    }

    /**
     * 의무고용 인원 기준 상위 10개 조회
     */
    public List<EmpDisRegionalStatusDto> findTop10ByObligationCount() {
        List<EmpDisRegionalStatusEntity> entities = repository.findTop10ByOrderByObligationCountDesc();
        return converter.toDtoList(entities);
    }

    /**
     * 중증 장애인 고용인원 기준 상위 10개 조회
     */
    public List<EmpDisRegionalStatusDto> findTop10BySevereCount() {
        List<EmpDisRegionalStatusEntity> entities = repository.findTop10ByOrderBySevereCountDesc();
        return converter.toDtoList(entities);
    }

    /**
     * 평균 고용률 조회
     */
    public BigDecimal findAverageSevereRate() {
        return repository.findAverageSevereRate();
    }

    /**
     * 지역별 평균 고용률 조회
     */
    public List<Object[]> findAverageSevereRateByRegion() {
        return repository.findAverageSevereRateByRegion();
    }

    /**
     * 고용률이 평균 이상인 지역 조회
     */
    public List<EmpDisRegionalStatusDto> findBySevereRateAboveAverage() {
        List<EmpDisRegionalStatusEntity> entities = repository.findBySevereRateAboveAverage();
        return converter.toDtoList(entities);
    }

    /**
     * 특정 고용률 이상인 지역 조회
     */
    public List<EmpDisRegionalStatusDto> findBySevereRateGreaterThanEqual(BigDecimal rate) {
        List<EmpDisRegionalStatusEntity> entities = repository.findBySevereRateGreaterThanEqualOrderBySevereRateDesc(rate);
        return converter.toDtoList(entities);
    }

    /**
     * 특정 고용률 이하인 지역 조회
     */
    public List<EmpDisRegionalStatusDto> findBySevereRateLessThanEqual(BigDecimal rate) {
        List<EmpDisRegionalStatusEntity> entities = repository.findBySevereRateLessThanEqualOrderBySevereRateAsc(rate);
        return converter.toDtoList(entities);
    }
} 