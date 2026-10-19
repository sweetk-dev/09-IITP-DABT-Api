package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.constant.poi.PoiWelfareFacilityType;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacility;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacilitySearchLocReq;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacilitySearchReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.poi.PoiWelfareFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PoiWelfareFacilityReadService extends PoiService {

    private final PoiWelfareFacilityRepository repository;

    public PoiWelfareFacility findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "facilityId(%d) not found".formatted(id)));
    }

    public PageRes<PoiWelfareFacility> search(PoiWelfareFacilitySearchReq q, PageReq page) {
        DbOffSet o = setDbOffset(page);
        PoiPageResult<PoiWelfareFacility> r = repository.search(code(q.getFacilityType()), q.getSigunName(),
                q.getSubType(), q.getName(), o.offset(), o.size());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    public PageRes<PoiWelfareFacility> searchByLocation(PoiWelfareFacilitySearchLocReq q, PageReq page) {
        DbOffSet o = setDbOffset(page);
        PoiPageResult<PoiWelfareFacility> r = repository.searchByLocation(q.getLatitude(), q.getLongitude(), q.getRadius(),
                code(q.getFacilityType()), o.offset(), o.size());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    private static String code(PoiWelfareFacilityType t) {
        return t == null ? null : t.getCode();
    }
}
