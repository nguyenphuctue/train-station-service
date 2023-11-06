package com.jungdo.trainstationservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameStationResponse {
    private Long id;
    private String nameStation;
}
