package com.jungdo.trainstationservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long id;

    private String title;

    private String state;

    private Long passenger;

    private String description;

    private TrainStationResponse trainStationResponse;
}
