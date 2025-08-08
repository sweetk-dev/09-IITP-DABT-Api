package com.sweetk.iitp.api.dto.poi.converter;

import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.entity.poi.PoiPublicToiletInfoEntity;

public class PoiPublicToiletInfoConverter {

    public static PoiPublicToiletInfo toDto(PoiPublicToiletInfoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PoiPublicToiletInfo(
            entity.getToiletId(),
            entity.getSidoCode(),
            entity.getToiletName(),
            entity.getToiletType(),
            entity.getBasis(),
            entity.getAddrRoad(),
            entity.getAddrJibun(),
            entity.getMToiletCount(),
            entity.getMUrinalCount(),
            entity.getMDisToiletCount(),
            entity.getMDisUrinalCount(),
            entity.getMChildToiletCount(),
            entity.getMChildUrinalCount(),
            entity.getFToiletCount(),
            entity.getFDisToiletCount(),
            entity.getFChildToiletCount(),
            entity.getManagingOrg(),
            entity.getPhoneNumber(),
            entity.getOpenTime(),
            entity.getOpenTimeDetail(),
            entity.getInstallDt(),
            entity.getLatitude(),
            entity.getLongitude(),
            entity.getOwnerType(),
            entity.getWasteProcessType(),
            entity.getSafetyTargetYn(),
            entity.getEmgBellYn(),
            entity.getEmgBellLocation(),
            entity.getCctvYn(),
            entity.getDiaperTableYn(),
            entity.getDiaperTableLocation(),
            entity.getRemodeledDt(),
            entity.getBaseDt()
        );
    }

    public static PoiPublicToiletInfoEntity toEntity(PoiPublicToiletInfo dto) {
        if (dto == null) {
            return null;
        }

        PoiPublicToiletInfoEntity entity = new PoiPublicToiletInfoEntity();
        entity.setToiletId(dto.getToiletId());
        entity.setSidoCode(dto.getSidoCode());
        entity.setToiletName(dto.getToiletName());
        entity.setToiletType(dto.getToiletType());
        entity.setBasis(dto.getBasis());
        entity.setAddrRoad(dto.getAddrRoad());
        entity.setAddrJibun(dto.getAddrJibun());
        entity.setMToiletCount(dto.getMToiletCount());
        entity.setMUrinalCount(dto.getMUrinalCount());
        entity.setMDisToiletCount(dto.getMDisToiletCount());
        entity.setMDisUrinalCount(dto.getMDisUrinalCount());
        entity.setMChildToiletCount(dto.getMChildToiletCount());
        entity.setMChildUrinalCount(dto.getMChildUrinalCount());
        entity.setFToiletCount(dto.getFToiletCount());
        entity.setFDisToiletCount(dto.getFDisToiletCount());
        entity.setFChildToiletCount(dto.getFChildToiletCount());
        entity.setManagingOrg(dto.getManagingOrg());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setOpenTime(dto.getOpenTime());
        entity.setOpenTimeDetail(dto.getOpenTimeDetail());
        entity.setInstallDt(dto.getInstallDt());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setOwnerType(dto.getOwnerType());
        entity.setWasteProcessType(dto.getWasteProcessType());
        entity.setSafetyTargetYn(dto.getSafetyTargetYn());
        entity.setEmgBellYn(dto.getEmgBellYn());
        entity.setEmgBellLocation(dto.getEmgBellLocation());
        entity.setCctvYn(dto.getCctvYn());
        entity.setDiaperTableYn(dto.getDiaperTableYn());
        entity.setDiaperTableLocation(dto.getDiaperTableLocation());
        entity.setRemodeledDt(dto.getRemodeledDt());
        entity.setBaseDt(dto.getBaseDt());

        return entity;
    }
} 