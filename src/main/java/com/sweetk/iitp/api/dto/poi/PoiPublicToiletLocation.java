package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PoiPublicToiletLocation extends PoiPublicToilet {
    @Schema(description = "요청한 위치로부터 거리", example = "100")
    @JsonProperty("distance")
    private Integer distance;

    // 전체 필드 생성자
    public PoiPublicToiletLocation(Integer toiletId, String sidoCode, String toiletName,
                                   String toiletType, String basis, String addrRoad,
                                   String addrJibun, Integer mToiletCount, Integer mUrinalCount,
                                   Integer mDisToiletCount, Integer mDisUrinalCount,
                                   Integer mChildToiletCount, Integer mChildUrinalCount,
                                   Integer fToiletCount, Integer fDisToiletCount,
                                   Integer fChildToiletCount, String managingOrg,
                                   String phoneNumber, String openTime, String openTimeDetail,
                                   String installDt, Double latitude, Double longitude,
                                   String ownerType, String wasteProcessType,
                                   String safetyTargetYn, String emgBellYn,
                                   String emgBellLocation, String cctvYn,
                                   String diaperTableYn, String diaperTableLocation,
                                   String remodeledDt, java.time.LocalDate baseDt,
                                   Integer distance) {
        super(toiletId, sidoCode, toiletName, toiletType, basis, addrRoad, addrJibun,
              mToiletCount, mUrinalCount, mDisToiletCount, mDisUrinalCount,
              mChildToiletCount, mChildUrinalCount, fToiletCount, fDisToiletCount,
              fChildToiletCount, managingOrg, phoneNumber, openTime, openTimeDetail,
              installDt, latitude, longitude, ownerType, wasteProcessType,
              safetyTargetYn, emgBellYn, emgBellLocation, cctvYn,
              diaperTableYn, diaperTableLocation, remodeledDt, baseDt);
        this.distance = distance;
    }

    // PoiPublicToilet 객체와 distance를 받는 생성자
    public PoiPublicToiletLocation(PoiPublicToilet toiletInfo, Integer distance) {
        super(toiletInfo.getToiletId(), toiletInfo.getSidoCode(), toiletInfo.getToiletName(),
              toiletInfo.getToiletType(), toiletInfo.getBasis(), toiletInfo.getAddrRoad(),
              toiletInfo.getAddrJibun(), toiletInfo.getMToiletCount(), toiletInfo.getMUrinalCount(),
              toiletInfo.getMDisToiletCount(), toiletInfo.getMDisUrinalCount(),
              toiletInfo.getMChildToiletCount(), toiletInfo.getMChildUrinalCount(),
              toiletInfo.getFToiletCount(), toiletInfo.getFDisToiletCount(),
              toiletInfo.getFChildToiletCount(), toiletInfo.getManagingOrg(),
              toiletInfo.getPhoneNumber(), toiletInfo.getOpenTime(), toiletInfo.getOpenTimeDetail(),
              toiletInfo.getInstallDt(), toiletInfo.getLatitude(), toiletInfo.getLongitude(),
              toiletInfo.getOwnerType(), toiletInfo.getWasteProcessType(),
              toiletInfo.getSafetyTargetYn(), toiletInfo.getEmgBellYn(),
              toiletInfo.getEmgBellLocation(), toiletInfo.getCctvYn(),
              toiletInfo.getDiaperTableYn(), toiletInfo.getDiaperTableLocation(),
              toiletInfo.getRemodeledDt(), toiletInfo.getBaseDt());
        this.distance = distance;
    }
} 