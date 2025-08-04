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
        entity.setToiletId(dto.toiletId());
        entity.setSidoCode(dto.sidoCode());
        entity.setToiletName(dto.toiletName());
        entity.setToiletType(dto.toiletType());
        entity.setBasis(dto.basis());
        entity.setAddrRoad(dto.addrRoad());
        entity.setAddrJibun(dto.addrJibun());
        entity.setMToiletCount(dto.mToiletCount());
        entity.setMUrinalCount(dto.mUrinalCount());
        entity.setMDisToiletCount(dto.mDisToiletCount());
        entity.setMDisUrinalCount(dto.mDisUrinalCount());
        entity.setMChildToiletCount(dto.mChildToiletCount());
        entity.setMChildUrinalCount(dto.mChildUrinalCount());
        entity.setFToiletCount(dto.fToiletCount());
        entity.setFDisToiletCount(dto.fDisToiletCount());
        entity.setFChildToiletCount(dto.fChildToiletCount());
        entity.setManagingOrg(dto.managingOrg());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setOpenTime(dto.openTime());
        entity.setOpenTimeDetail(dto.openTimeDetail());
        entity.setInstallDt(dto.installDt());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setOwnerType(dto.ownerType());
        entity.setWasteProcessType(dto.wasteProcessType());
        entity.setSafetyTargetYn(dto.safetyTargetYn());
        entity.setEmgBellYn(dto.emgBellYn());
        entity.setEmgBellLocation(dto.emgBellLocation());
        entity.setCctvYn(dto.cctvYn());
        entity.setDiaperTableYn(dto.diaperTableYn());
        entity.setDiaperTableLocation(dto.diaperTableLocation());
        entity.setRemodeledDt(dto.remodeledDt());
        entity.setBaseDt(dto.baseDt());

        return entity;
    }
} 