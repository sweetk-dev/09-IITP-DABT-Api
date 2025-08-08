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
        entity.setSubwayId(dto.getSubwayId());
        entity.setSidoCode(dto.getSidoCode());
        entity.setNodeLinkType(dto.getNodeLinkType());
        entity.setNodeWkt(dto.getNodeWkt());
        entity.setNodeId(dto.getNodeId());
        entity.setNodeTypeCode(dto.getNodeTypeCode());
        entity.setNodeTypeName(dto.getNodeTypeName());
        entity.setSigunguCode(dto.getSigunguCode());
        entity.setSigunguName(dto.getSigunguName());
        entity.setEupmyeondongCode(dto.getEupmyeondongCode());
        entity.setEupmyeondongName(dto.getEupmyeondongName());
        entity.setStationCode(dto.getStationCode());
        entity.setStationName(dto.getStationName());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setBaseDt(dto.getBaseDt());

        return entity;
    }
} 