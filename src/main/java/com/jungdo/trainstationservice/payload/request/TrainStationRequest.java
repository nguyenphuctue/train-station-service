package com.jungdo.trainstationservice.payload.request;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jungdo.trainstationservice.util.map.PointDeserializer;
import com.jungdo.trainstationservice.util.map.PolygonDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainStationRequest {

    private String nameStation;

    private String address;

    private Double longitude;

    private Double latitude;

    private Double distanceFromStart;

    private Double distanceToEnd;

    private String note;

    private String propertiesOfStation;

    private List<String> imageUrls;

    @JsonDeserialize(using = PolygonDeserializer.class)
    private Polygon boundary;

    @JsonDeserialize(using = PointDeserializer.class)
    private Point location;
}
