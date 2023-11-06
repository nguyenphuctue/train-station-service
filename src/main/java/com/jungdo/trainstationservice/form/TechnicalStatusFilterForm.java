package com.jungdo.trainstationservice.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalStatusFilterForm {
    private String search;
    private String state;
    private String sort;
}
