package com.jungdo.trainstationservice.controller;


import com.jungdo.trainstationservice.form.ReportFilterForm;
import com.jungdo.trainstationservice.payload.request.ReportRequest;
import com.jungdo.trainstationservice.payload.response.MessageResponse;
import com.jungdo.trainstationservice.payload.response.ReportResponse;
import com.jungdo.trainstationservice.payload.response.SuccessResponse;
import com.jungdo.trainstationservice.service.ReportService;
import com.jungdo.trainstationservice.util.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getAllReport(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String sort) {
        ReportFilterForm reportFilterForm = new ReportFilterForm(search, state, sort);
        Page<ReportResponse> reportResponses = reportService.getAllReport(page, size, reportFilterForm);

        SuccessResponse<Page<ReportResponse>> successResponse = new SuccessResponse<>();
        if (reportResponses.getTotalElements() > 0) {
            successResponse.setMessage("List of report");
        } else {
            successResponse.setMessage("List of report is null");
        }
        successResponse.setData(reportResponses);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addReport(@RequestBody ReportRequest reportRequest) {
        ReportResponse reportResponse = reportService.addReport(reportRequest);

        SuccessResponse<ReportResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Created report successful.");
        successResponse.setData(reportResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        ReportResponse reportResponse = reportService.getReportById(id);

        SuccessResponse<ReportResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Get report by id.");
        successResponse.setData(reportResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editReport(@PathVariable Long id, @RequestBody ReportRequest reportRequest) {
        ReportResponse reportResponse = reportService.updateReport(id, reportRequest);

        SuccessResponse<ReportResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Updated report successful.");
        successResponse.setData(reportResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReportById(@PathVariable Long id) {
        reportService.deleteReportById(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSuccess(true);
        messageResponse.setMessage("Deleted report successful with id " + id);

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
