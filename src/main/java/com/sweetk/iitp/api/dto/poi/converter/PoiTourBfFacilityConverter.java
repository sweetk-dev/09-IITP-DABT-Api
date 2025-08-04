package com.sweetk.iitp.api.dto.poi.converter;

import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.entity.poi.PoiTourBfFacilityEntity;

public class PoiTourBfFacilityConverter {

    public static PoiTourBfFacility toDto(PoiTourBfFacilityEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PoiTourBfFacility(
            entity.getFcltId(),
            entity.getSidoCode(),
            entity.getFcltName(),
            entity.getToiletYn(),
            entity.getElevatorYn(),
            entity.getParkingYn(),
            entity.getSlopeYn(),
            entity.getSubwayYn(),
            entity.getBusStopYn(),
            entity.getWheelchairRentYn(),
            entity.getTactileMapYn(),
            entity.getAudioGuideYn(),
            entity.getNursingRoomYn(),
            entity.getAccessibleRoomYn(),
            entity.getStrollerRentYn(),
            entity.getAddrRoad(),
            entity.getAddrJibun(),
            entity.getLatitude(),
            entity.getLongitude(),
            entity.getBaseDt()
        );
    }

    public static PoiTourBfFacilityEntity toEntity(PoiTourBfFacility dto) {
        if (dto == null) {
            return null;
        }

        PoiTourBfFacilityEntity entity = new PoiTourBfFacilityEntity();
        entity.setFcltId(dto.fcltId());
        entity.setSidoCode(dto.sidoCode());
        entity.setFcltName(dto.fcltName());
        entity.setToiletYn(dto.toiletYn());
        entity.setElevatorYn(dto.elevatorYn());
        entity.setParkingYn(dto.parkingYn());
        entity.setSlopeYn(dto.slopeYn());
        entity.setSubwayYn(dto.subwayYn());
        entity.setBusStopYn(dto.busStopYn());
        entity.setWheelchairRentYn(dto.wheelchairRentYn());
        entity.setTactileMapYn(dto.tactileMapYn());
        entity.setAudioGuideYn(dto.audioGuideYn());
        entity.setNursingRoomYn(dto.nursingRoomYn());
        entity.setAccessibleRoomYn(dto.accessibleRoomYn());
        entity.setStrollerRentYn(dto.strollerRentYn());
        entity.setAddrRoad(dto.addrRoad());
        entity.setAddrJibun(dto.addrJibun());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setBaseDt(dto.baseDt());

        return entity;
    }
} 