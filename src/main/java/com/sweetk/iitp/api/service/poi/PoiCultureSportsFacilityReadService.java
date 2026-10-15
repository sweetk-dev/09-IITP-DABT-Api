package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants;
import com.sweetk.iitp.api.constant.poi.PoiCultureSportsType;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacility;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilityLocation;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilitySearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiCultureSportsFacilityRepository;
import com.sweetk.iitp.api.service.sys.SysCommonCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiCultureSportsFacilityReadService extends PoiService {

    private final PoiCultureSportsFacilityRepository repository;
    private final SysCommonCodeService commonCodeService;

    public Optional<PoiCultureSportsFacility> findById(Integer facilityId) {
        return repository.findByIdToDto(facilityId);
    }

    public List<PoiCultureSportsFacility> getAll() {
        return repository.findByConditions(null, null, null, null, null, null).getContent();
    }

    public PageRes<PoiCultureSportsFacility> getAllPaging(PageReq pageReq) {
        DbOffSet o = setDbOffset(pageReq);
        PoiPageResult<PoiCultureSportsFacility> r = repository.findByConditions(null, null, null, null, o.offset(), o.size());
        return new PageRes<>(r.getContent(), pageReq.toPageable(), r.getTotalCount());
    }

    public List<PoiCultureSportsFacility> getBySido(String sidoCode) {
        validateSido(sidoCode);
        return repository.findByConditions(null, null, null, sidoCode, null, null).getContent();
    }

    public PageRes<PoiCultureSportsFacility> getBySidoPaging(String sidoCode, PageReq pageReq) {
        validateSido(sidoCode);
        DbOffSet o = setDbOffset(pageReq);
        PoiPageResult<PoiCultureSportsFacility> r = repository.findByConditions(null, null, null, sidoCode, o.offset(), o.size());
        return new PageRes<>(r.getContent(), pageReq.toPageable(), r.getTotalCount());
    }

    public List<PoiCultureSportsFacility> search(PoiCultureSportsFacilitySearchCatReq req) {
        validateCategory(req);
        return repository.findByConditions(code(req.getFacilityType()), req.getName(), req.getSigunNm(),
                req.getSidoCode(), null, null).getContent();
    }

    public PageRes<PoiCultureSportsFacility> searchPaging(PoiCultureSportsFacilitySearchCatReq req, PageReq pageReq) {
        validateCategory(req);
        DbOffSet o = setDbOffset(pageReq);
        PoiPageResult<PoiCultureSportsFacility> r = repository.findByConditions(code(req.getFacilityType()),
                req.getName(), req.getSigunNm(), req.getSidoCode(), o.offset(), o.size());
        return new PageRes<>(r.getContent(), pageReq.toPageable(), r.getTotalCount());
    }

    public List<PoiCultureSportsFacilityLocation> searchByLocation(PoiCultureSportsFacilitySearchLocReq req) {
        log.debug("문화·체육 시설 위치 검색 - 위도: {}, 경도: {}, 반경: {}m", req.getLatitude(), req.getLongitude(), req.getRadius());
        return repository.findByLocation(req.getLatitude(), req.getLongitude(), req.getRadius(),
                code(req.getFacilityType()), req.getName(), null, null).getContent();
    }

    public PageRes<PoiCultureSportsFacilityLocation> searchByLocationPaging(PoiCultureSportsFacilitySearchLocReq req, PageReq pageReq) {
        DbOffSet o = setDbOffset(pageReq);
        PoiPageResult<PoiCultureSportsFacilityLocation> r = repository.findByLocation(req.getLatitude(), req.getLongitude(),
                req.getRadius(), code(req.getFacilityType()), req.getName(), o.offset(), o.size());
        return new PageRes<>(r.getContent(), pageReq.toPageable(), r.getTotalCount());
    }

    private static String code(PoiCultureSportsType type) {
        return type == null ? null : type.getCode();
    }

    private void validateSido(String sidoCode) {
        if (!commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, sidoCode)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "sidoCode(%s) not valid".formatted(sidoCode));
        }
    }

    private void validateCategory(PoiCultureSportsFacilitySearchCatReq req) {
        if (req == null || !req.hasCondition()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "검색 조건(facilityType, name, sigunNm, sidoCode)이 하나 이상 필요합니다.");
        }
        if (req.getSidoCode() != null) {
            validateSido(req.getSidoCode());
        }
    }
}
