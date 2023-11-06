package com.jungdo.trainstationservice.payload.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jungdo.trainstationservice.util.map.PointSerializer;
import com.jungdo.trainstationservice.util.map.PolygonSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainStationResponse {

    private Long id;

    private String nameStation;

    private String address;

    private Double longitude;

    private Double latitude;

    private Double distanceFromStart;

    private Double distanceToEnd;

    private String note;

    private String propertiesOfStation;

    @JsonSerialize(using = PointSerializer.class)
    private Point location;

    @JsonSerialize(using = PolygonSerializer.class)
    private Polygon boundary;

}
