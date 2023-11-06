package com.jungdo.trainstationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.*;

@Entity
@Table(name = "tb_train_stations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainStation extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameStation;

    private String address;

    private Double longitude;

    private Double latitude;

    private Double distanceFromStart;

    private Double distanceToEnd;

    private String note;

    private String propertiesOfStation;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @Column(columnDefinition = "geometry(Polygon, 4326)")
    private Polygon boundary;
}
