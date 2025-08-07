package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PoiSubwayElevatorLocation extends PoiSubwayElevator {
    @Schema(description = "요청한 위치로부터 거리", example = "100")
    @JsonProperty("distance")
    private Integer distance;

    // 전체 필드 생성자
    public PoiSubwayElevatorLocation(Integer subwayId, String sidoCode, String nodeLinkType,
                                    String nodeWkt, Long nodeId, Integer nodeTypeCode,
                                    String nodeTypeName, String sigunguCode, String sigunguName,
                                    String eupmyeondongCode, String eupmyeondongName,
                                    String stationCode, String stationName, Double latitude,
                                    Double longitude, String baseDt, Integer distance) {
        super(subwayId, sidoCode, nodeLinkType, nodeWkt, nodeId, nodeTypeCode, nodeTypeName,
              sigunguCode, sigunguName, eupmyeondongCode, eupmyeondongName,
              stationCode, stationName, latitude, longitude, baseDt);
        this.distance = distance;
    }

    // PoiSubwayElevator 객체와 distance를 받는 생성자
    public PoiSubwayElevatorLocation(PoiSubwayElevator elevator, Integer distance) {
        super(elevator.getSubwayId(), elevator.getSidoCode(), elevator.getNodeLinkType(),
              elevator.getNodeWkt(), elevator.getNodeId(), elevator.getNodeTypeCode(),
              elevator.getNodeTypeName(), elevator.getSigunguCode(), elevator.getSigunguName(),
              elevator.getEupmyeondongCode(), elevator.getEupmyeondongName(),
              elevator.getStationCode(), elevator.getStationName(), elevator.getLatitude(),
              elevator.getLongitude(), elevator.getBaseDt());
        this.distance = distance;
    }
} 