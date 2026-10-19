package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibility;
import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibilitySearchLocReq;
import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibilitySearchReq;
import com.sweetk.iitp.api.repository.poi.PoiBuildingAccessibilityRepository;
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
public class PoiBuildingAccessibilityReadService extends PoiService {

    private final PoiBuildingAccessibilityRepository repository;

    public PoiBuildingAccessibility findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "faclId(%d) not found".formatted(id)));
    }

    public PageRes<PoiBuildingAccessibility> search(PoiBuildingAccessibilitySearchReq q, PageReq page) {
        DbOffSet o = setDbOffset(page);
        PoiPageResult<PoiBuildingAccessibility> r = repository.search(q.getFaclName(), q.getFaclType(), q.getAddr(),
                q.getElevatorYn(), q.getDisToiletYn(), q.getDisParkingYn(), q.getEntranceRampYn(), o.offset(), o.size());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    public PageRes<PoiBuildingAccessibility> searchByLocation(PoiBuildingAccessibilitySearchLocReq q, PageReq page) {
        DbOffSet o = setDbOffset(page);
        PoiPageResult<PoiBuildingAccessibility> r = repository.searchByLocation(q.getLatitude(), q.getLongitude(),
                q.getRadius(), q.getFaclType(), q.getElevatorYn(), q.getDisToiletYn(), q.getDisParkingYn(), o.offset(), o.size());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }
}
