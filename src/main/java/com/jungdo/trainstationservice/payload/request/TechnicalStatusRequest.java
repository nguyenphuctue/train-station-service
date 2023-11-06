package com.jungdo.trainstationservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalStatusRequest {
    private Integer periodOfChecking;

    private LocalDateTime dateChecking;

    private String state;

    private String description;

    private boolean requestRepair;

    private Long trainStation;

    private List<String> imageUrls;
}
