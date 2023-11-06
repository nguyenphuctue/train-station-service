package com.jungdo.trainstationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_technical_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalStatus extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer periodOfChecking;

    private LocalDateTime dateChecking;

    private String state;

    private String description;

    private boolean requestRepair;

    @ManyToOne
    @JoinColumn(name = "train_station_id")
    private TrainStation trainStation;
}
