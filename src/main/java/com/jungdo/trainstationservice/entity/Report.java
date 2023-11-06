package com.jungdo.trainstationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String state;

    private Long passenger;

    private String description;

    @ManyToOne
    @JoinColumn(name = "train_station_id")
    private TrainStation trainStation;
}
