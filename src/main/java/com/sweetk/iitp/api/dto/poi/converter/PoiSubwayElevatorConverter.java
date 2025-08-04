package com.sweetk.iitp.api.dto.poi.converter;

import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.entity.poi.PoiSubwayElevatorEntity;

public class PoiSubwayElevatorConverter {

    public static PoiSubwayElevator toDto(PoiSubwayElevatorEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PoiSubwayElevator(
            entity.getSubwayId(),
            entity.getSidoCode(),
            entity.getNodeLinkType(),
            entity.getNodeWkt(),
            entity.getNodeId(),
            entity.getNodeTypeCode(),
            entity.getNodeTypeName(),
            entity.getSigunguCode(),
            entity.getSigunguName(),
            entity.getEupmyeondongCode(),
            entity.getEupmyeondongName(),
            entity.getStationCode(),
            entity.getStationName(),
            entity.getLatitude(),
            entity.getLongitude(),
            entity.getBaseDt()
        );
    }

    public static PoiSubwayElevatorEntity toEntity(PoiSubwayElevator dto) {
        if (dto == null) {
            return null;
        }

        PoiSubwayElevatorEntity entity = new PoiSubwayElevatorEntity();
        entity.setSubwayId(dto.subwayId());
        entity.setSidoCode(dto.sidoCode());
        entity.setNodeLinkType(dto.nodeLinkType());
        entity.setNodeWkt(dto.nodeWkt());
        entity.setNodeId(dto.nodeId());
        entity.setNodeTypeCode(dto.nodeTypeCode());
        entity.setNodeTypeName(dto.nodeTypeName());
        entity.setSigunguCode(dto.sigunguCode());
        entity.setSigunguName(dto.sigunguName());
        entity.setEupmyeondongCode(dto.eupmyeondongCode());
        entity.setEupmyeondongName(dto.eupmyeondongName());
        entity.setStationCode(dto.stationCode());
        entity.setStationName(dto.stationName());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setBaseDt(dto.baseDt());

        return entity;
    }
} 