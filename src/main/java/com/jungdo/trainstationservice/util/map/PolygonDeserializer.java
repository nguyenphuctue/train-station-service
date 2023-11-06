package com.jungdo.trainstationservice.util.map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;

public class PolygonDeserializer extends JsonDeserializer<Polygon> {

    @Override
    public Polygon deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode coordinatesNode = node.get("coordinates");

        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate[] boundaryCoordinates = new Coordinate[coordinatesNode.size()];
        for (int i = 0; i < coordinatesNode.size(); i++) {
            JsonNode coordinateNode = coordinatesNode.get(i);
            double x = coordinateNode.get(0).asDouble();
            double y = coordinateNode.get(1).asDouble();
            boundaryCoordinates[i] = new Coordinate(x, y);
        }

        LinearRing boundaryRing = geometryFactory.createLinearRing(boundaryCoordinates);

        return geometryFactory.createPolygon(boundaryRing, null);
    }
}
