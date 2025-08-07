package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PoiTourBfFacilityLocation extends PoiTourBfFacility {
    @Schema(description = "요청한 위치로부터 거리", example = "100")
    @JsonProperty("distance")
    private Integer distance;

    // 전체 필드 생성자
    public PoiTourBfFacilityLocation(Integer fcltId, String sidoCode, String fcltName,
                                    String toiletYn, String elevatorYn, String parkingYn,
                                    String slopeYn, String subwayYn, String busStopYn,
                                    String wheelchairRentYn, String tactileMapYn,
                                    String audioGuideYn, String nursingRoomYn,
                                    String accessibleRoomYn, String strollerRentYn,
                                    String addrRoad, String addrJibun, Double latitude,
                                    Double longitude, java.time.LocalDate baseDt,
                                    Integer distance) {
        super(fcltId, sidoCode, fcltName, toiletYn, elevatorYn, parkingYn, slopeYn,
              subwayYn, busStopYn, wheelchairRentYn, tactileMapYn, audioGuideYn,
              nursingRoomYn, accessibleRoomYn, strollerRentYn, addrRoad, addrJibun,
              latitude, longitude, baseDt);
        this.distance = distance;
    }

    // PoiTourBfFacility 객체와 distance를 받는 생성자
    public PoiTourBfFacilityLocation(PoiTourBfFacility facility, Integer distance) {
        super(facility.getFcltId(), facility.getSidoCode(), facility.getFcltName(),
              facility.getToiletYn(), facility.getElevatorYn(), facility.getParkingYn(),
              facility.getSlopeYn(), facility.getSubwayYn(), facility.getBusStopYn(),
              facility.getWheelchairRentYn(), facility.getTactileMapYn(),
              facility.getAudioGuideYn(), facility.getNursingRoomYn(),
              facility.getAccessibleRoomYn(), facility.getStrollerRentYn(),
              facility.getAddrRoad(), facility.getAddrJibun(), facility.getLatitude(),
              facility.getLongitude(), facility.getBaseDt());
        this.distance = distance;
    }
} 