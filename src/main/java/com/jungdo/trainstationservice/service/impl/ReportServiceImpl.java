package com.jungdo.trainstationservice.service.impl;

import com.jungdo.trainstationservice.entity.Report;
import com.jungdo.trainstationservice.entity.TechnicalStatus;
import com.jungdo.trainstationservice.entity.TrainStation;
import com.jungdo.trainstationservice.exception.ResourceNotFoundException;
import com.jungdo.trainstationservice.form.ReportFilterForm;
import com.jungdo.trainstationservice.payload.request.ReportRequest;
import com.jungdo.trainstationservice.payload.response.ReportResponse;
import com.jungdo.trainstationservice.payload.response.TechnicalStatusResponse;
import com.jungdo.trainstationservice.repository.ReportRepository;
import com.jungdo.trainstationservice.repository.TrainStationRepository;
import com.jungdo.trainstationservice.service.ReportService;
import com.jungdo.trainstationservice.specification.ReportSpecification;
import com.jungdo.trainstationservice.util.PaginationUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    private TrainStationRepository trainStationRepository;

    private ModelMapper modelMapper;

    @Override
    public Page<ReportResponse> getAllReport(int page, int size, ReportFilterForm filterForm) {
        PaginationUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Specification<Report> where = ReportSpecification.buildWhere(filterForm);
        Page<Report> reportPage = reportRepository.findAll(where, pageable);

        modelMapper.typeMap(Report.class, ReportResponse.class).addMapping(Report::getTrainStation, ReportResponse::setTrainStationResponse);

        List<ReportResponse> reportResponses = reportPage.stream().map(report -> modelMapper.map(report, ReportResponse.class)).collect(Collectors.toList());

        return new PageImpl<>(reportResponses, pageable, reportPage.getTotalElements());
    }

    @Override
    public ReportResponse getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", String.valueOf(id)));
        modelMapper.typeMap(Report.class, ReportResponse.class).addMapping(Report::getTrainStation, ReportResponse::setTrainStationResponse);

        return modelMapper.map(report, ReportResponse.class);
    }

    @Override
    public ReportResponse addReport(ReportRequest reportRequest) {
        Report report = modelMapper.map(reportRequest, Report.class);
        TrainStation trainStation = trainStationRepository.findById(reportRequest.getTrainStation())
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(reportRequest.getTrainStation())));
        report.setTrainStation(trainStation);
        Report savedReport = reportRepository.save(report);

        modelMapper.typeMap(Report.class, ReportResponse.class).addMapping(Report::getTrainStation, ReportResponse::setTrainStationResponse);

        return modelMapper.map(savedReport, ReportResponse.class);
    }

    @Override
    public ReportResponse updateReport(Long id, ReportRequest reportRequest) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", String.valueOf(id)));
        modelMapper.map(reportRequest, report);
        TrainStation trainStation = trainStationRepository.findById(reportRequest.getTrainStation())
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(reportRequest.getTrainStation())));
        report.setTrainStation(trainStation);
        Report updatedReport = reportRepository.save(report);

        modelMapper.typeMap(Report.class, ReportResponse.class).addMapping(Report::getTrainStation, ReportResponse::setTrainStationResponse);

        return modelMapper.map(updatedReport, ReportResponse.class);
    }

    @Override
    public void deleteReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", String.valueOf(id)));

        reportRepository.delete(report);
    }
}
