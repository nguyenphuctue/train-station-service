package com.jungdo.trainstationservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalStatusResponse {
    private Long id;

    private Integer periodOfChecking;

    private LocalDateTime dateChecking;

    private String state;

    private String description;

    private boolean requestRepair;

    private TrainStationResponse trainStationResponse;
}
