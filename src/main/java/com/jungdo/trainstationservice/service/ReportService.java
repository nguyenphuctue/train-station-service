package com.jungdo.trainstationservice.service;

import com.jungdo.trainstationservice.form.ReportFilterForm;
import com.jungdo.trainstationservice.payload.request.ReportRequest;
import com.jungdo.trainstationservice.payload.response.ReportResponse;
import org.springframework.data.domain.Page;

public interface ReportService {
    public Page<ReportResponse> getAllReport(int page, int size, ReportFilterForm reportFilterForm);

    public ReportResponse getReportById(Long id);

    public ReportResponse addReport(ReportRequest reportRequest);

    public ReportResponse updateReport(Long id, ReportRequest reportRequest);

    public void deleteReportById(Long id);
}
