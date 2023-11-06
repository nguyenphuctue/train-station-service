package com.jungdo.trainstationservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private String title;

    private String state;

    private Long passenger;

    private String description;

    private Long trainStation;
}
