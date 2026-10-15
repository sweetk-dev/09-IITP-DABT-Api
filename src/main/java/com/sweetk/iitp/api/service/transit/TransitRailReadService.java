package com.sweetk.iitp.api.service.transit;

import com.sweetk.iitp.api.dto.poi.PoiBaseSearchLocReq;
import com.sweetk.iitp.api.dto.transit.TransitRailElevator;
import com.sweetk.iitp.api.dto.transit.TransitRailPlatform;
import com.sweetk.iitp.api.dto.transit.TransitRailSearchReq;
import com.sweetk.iitp.api.dto.transit.TransitRailStation;
import com.sweetk.iitp.api.dto.transit.TransitRailToilet;
import com.sweetk.iitp.api.dto.transit.TransitRailWheelchairLift;
import com.sweetk.iitp.api.repository.transit.TransitRailRepository;
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
public class TransitRailReadService {

    private final TransitRailRepository repository;

    public TransitRailStation findStation(Integer stationId) {
        return repository.findStation(stationId).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "stationId(%d) not found".formatted(stationId)));
    }

    public PageRes<TransitRailStation> searchStations(TransitRailSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        return res(repository.searchStations(q.getStnName(), q.getLineName(), offset, page.getSize()), page);
    }

    public PageRes<TransitRailStation> searchStationsByLocation(PoiBaseSearchLocReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        return res(repository.searchStationsByLocation(q.getLatitude(), q.getLongitude(), q.getRadius(), offset, page.getSize()), page);
    }

    public PageRes<TransitRailElevator> elevators(TransitRailSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        return res(repository.elevators(q.getStnName(), q.getLineName(), offset, page.getSize()), page);
    }

    public PageRes<TransitRailPlatform> platforms(TransitRailSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        return res(repository.platforms(q.getStnName(), q.getLineName(), offset, page.getSize()), page);
    }

    public PageRes<TransitRailToilet> toilets(TransitRailSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        return res(repository.toilets(q.getStnName(), q.getLineName(), q.getDisabledYn(), offset, page.getSize()), page);
    }

    public PageRes<TransitRailWheelchairLift> wheelchairLifts(TransitRailSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        return res(repository.wheelchairLifts(q.getStnName(), q.getLineName(), offset, page.getSize()), page);
    }

    private static <T> PageRes<T> res(PoiPageResult<T> r, PageReq page) {
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }
}
