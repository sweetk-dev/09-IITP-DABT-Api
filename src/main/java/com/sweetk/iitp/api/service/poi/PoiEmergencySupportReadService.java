package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.CommonCodeConstants;
import com.sweetk.iitp.api.constant.poi.PoiEmergencySupportType;
import com.sweetk.iitp.api.dto.poi.PoiEmergencySupport;
import com.sweetk.iitp.api.dto.poi.PoiEmergencySupportSearchLocReq;
import com.sweetk.iitp.api.dto.poi.PoiEmergencySupportSearchReq;
import com.sweetk.iitp.api.repository.poi.PoiEmergencySupportRepository;
import com.sweetk.iitp.api.service.sys.SysCommonCodeService;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiEmergencySupportReadService extends PoiService {

    private final PoiEmergencySupportRepository repository;
    private final SysCommonCodeService commonCodeService;

    public PoiEmergencySupport findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "supportId(%d) not found".formatted(id)));
    }

    public PageRes<PoiEmergencySupport> search(PoiEmergencySupportSearchReq q, PageReq page) {
        if (q.getSidoCode() != null && !q.getSidoCode().isBlank()
                && !commonCodeService.isValidCode(CommonCodeConstants.SysCodeGroup.SIDO_CODE, q.getSidoCode())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "sidoCode(%s) not valid".formatted(q.getSidoCode()));
        }
        DbOffSet o = setDbOffset(page);
        PoiPageResult<PoiEmergencySupport> r = repository.search(code(q.getSupportType()), q.getSidoCode(), q.getSggName(),
                q.getName(), o.offset(), o.size());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    public PageRes<PoiEmergencySupport> searchByLocation(PoiEmergencySupportSearchLocReq q, PageReq page) {
        DbOffSet o = setDbOffset(page);
        PoiPageResult<PoiEmergencySupport> r = repository.searchByLocation(q.getLatitude(), q.getLongitude(), q.getRadius(),
                code(q.getSupportType()), o.offset(), o.size());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    private static String code(PoiEmergencySupportType t) {
        return t == null ? null : t.getCode();
    }
}
