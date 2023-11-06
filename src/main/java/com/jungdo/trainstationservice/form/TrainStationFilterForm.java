package com.jungdo.trainstationservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainStationFilterForm {
    private String search;
    private String property;
    private String sort;
}
