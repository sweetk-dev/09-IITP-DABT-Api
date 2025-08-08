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
        entity.setFcltId(dto.getFcltId());
        entity.setSidoCode(dto.getSidoCode());
        entity.setFcltName(dto.getFcltName());
        entity.setToiletYn(dto.getToiletYn());
        entity.setElevatorYn(dto.getElevatorYn());
        entity.setParkingYn(dto.getParkingYn());
        entity.setSlopeYn(dto.getSlopeYn());
        entity.setSubwayYn(dto.getSubwayYn());
        entity.setBusStopYn(dto.getBusStopYn());
        entity.setWheelchairRentYn(dto.getWheelchairRentYn());
        entity.setTactileMapYn(dto.getTactileMapYn());
        entity.setAudioGuideYn(dto.getAudioGuideYn());
        entity.setNursingRoomYn(dto.getNursingRoomYn());
        entity.setAccessibleRoomYn(dto.getAccessibleRoomYn());
        entity.setStrollerRentYn(dto.getStrollerRentYn());
        entity.setAddrRoad(dto.getAddrRoad());
        entity.setAddrJibun(dto.getAddrJibun());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setBaseDt(dto.getBaseDt());

        return entity;
    }
} 