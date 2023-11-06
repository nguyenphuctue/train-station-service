package com.jungdo.trainstationservice.util.map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;

public class PolygonSerializer extends JsonSerializer<Polygon> {

    @Override
    public void serialize(Polygon polygon, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "Polygon");
        jsonGenerator.writeArrayFieldStart("coordinates");

        Coordinate[] coordinates = polygon.getCoordinates();
        for (Coordinate coordinate : coordinates) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeNumber(coordinate.getX());
            jsonGenerator.writeNumber(coordinate.getY());
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
