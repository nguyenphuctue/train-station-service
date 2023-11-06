package com.jungdo.trainstationservice.service;

import com.jungdo.trainstationservice.form.TechnicalStatusFilterForm;
import com.jungdo.trainstationservice.payload.request.TechnicalStatusRequest;
import com.jungdo.trainstationservice.payload.response.TechnicalStatusResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TechnicalStatusService {
    public Page<TechnicalStatusResponse> getAllTechnicalStatus(int page, int size, TechnicalStatusFilterForm technicalStatusFilterForm);

    public Page<TechnicalStatusResponse> getAllTechnicalStatusHaveRequestRepair(int page, int size, TechnicalStatusFilterForm technicalStatusFilterForm);

    public TechnicalStatusResponse getTechnicalStatusById(Long id);

    public TechnicalStatusResponse addTechnicalStatus(TechnicalStatusRequest technicalStatusRequest);

    public TechnicalStatusResponse updateTechnicalStatus(Long id, TechnicalStatusRequest technicalStatusRequest);

    public void deleteTechnicalStatusById(Long id);

    public TechnicalStatusResponse deleteRequestRepair(Long id);

}
