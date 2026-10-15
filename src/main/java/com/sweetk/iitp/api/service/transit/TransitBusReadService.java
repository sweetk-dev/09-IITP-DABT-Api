package com.sweetk.iitp.api.service.transit;

import com.sweetk.iitp.api.dto.poi.PoiBaseSearchLocReq;
import com.sweetk.iitp.api.dto.transit.TransitBusRoute;
import com.sweetk.iitp.api.dto.transit.TransitBusRouteSearchReq;
import com.sweetk.iitp.api.dto.transit.TransitBusRouteStop;
import com.sweetk.iitp.api.dto.transit.TransitBusStation;
import com.sweetk.iitp.api.dto.transit.TransitBusStationSearchReq;
import com.sweetk.iitp.api.repository.transit.TransitBusRepository;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransitBusReadService {

    private final TransitBusRepository repository;

    public TransitBusRoute findRoute(Long routeId) {
        return repository.findRoute(routeId).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "routeId(%d) not found".formatted(routeId)));
    }

    public List<TransitBusRouteStop> findRouteStops(Long routeId) {
        findRoute(routeId);
        return repository.findRouteStops(routeId);
    }

    public PageRes<TransitBusRoute> searchRoutes(TransitBusRouteSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        PoiPageResult<TransitBusRoute> r = repository.searchRoutes(q.getRouteName(), q.getRegionName(), q.getLowBusYn(),
                offset, page.getSize());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    public TransitBusStation findStation(Long stationId) {
        return repository.findStation(stationId).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "stationId(%d) not found".formatted(stationId)));
    }

    public List<TransitBusRoute> findStationRoutes(Long stationId) {
        findStation(stationId);
        return repository.findStationRoutes(stationId);
    }

    public PageRes<TransitBusStation> searchStations(TransitBusStationSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        PoiPageResult<TransitBusStation> r = repository.searchStations(q.getStationName(), q.getMobileNo(),
                q.getRegionName(), offset, page.getSize());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    public PageRes<TransitBusStation> searchStationsByLocation(PoiBaseSearchLocReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        PoiPageResult<TransitBusStation> r = repository.searchStationsByLocation(q.getLatitude(), q.getLongitude(),
                q.getRadius(), offset, page.getSize());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }
}
