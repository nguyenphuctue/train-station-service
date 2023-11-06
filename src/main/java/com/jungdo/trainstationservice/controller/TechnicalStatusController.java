package com.jungdo.trainstationservice.controller;

import com.jungdo.trainstationservice.form.TechnicalStatusFilterForm;
import com.jungdo.trainstationservice.payload.request.TechnicalStatusRequest;
import com.jungdo.trainstationservice.payload.response.MessageResponse;
import com.jungdo.trainstationservice.payload.response.SuccessResponse;
import com.jungdo.trainstationservice.payload.response.TechnicalStatusResponse;
import com.jungdo.trainstationservice.service.TechnicalStatusService;
import com.jungdo.trainstationservice.util.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technical-status")
public class TechnicalStatusController {

    @Autowired
    private TechnicalStatusService technicalStatusService;

    @GetMapping
    public ResponseEntity<?> getAllTechnicalStatus(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String sort) {
        TechnicalStatusFilterForm technicalStatusFilterForm = new TechnicalStatusFilterForm(search, state, sort);
        Page<TechnicalStatusResponse> technicalStatusResponses = technicalStatusService.getAllTechnicalStatus(page, size, technicalStatusFilterForm);

        SuccessResponse<Page<TechnicalStatusResponse>> successResponse = new SuccessResponse<>();
        if (technicalStatusResponses.getTotalElements() > 0) {
            successResponse.setMessage("List of technical status");
        } else {
            successResponse.setMessage("List of technical status is null");
        }
        successResponse.setData(technicalStatusResponses);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/request-repair")
    public ResponseEntity<?> getAllTechnicalStatusHaveRequestRepair(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String sort) {
        TechnicalStatusFilterForm technicalStatusFilterForm = new TechnicalStatusFilterForm(search, state, sort);
        Page<TechnicalStatusResponse> technicalStatusResponses = technicalStatusService.getAllTechnicalStatusHaveRequestRepair(page, size, technicalStatusFilterForm);

        SuccessResponse<Page<TechnicalStatusResponse>> successResponse = new SuccessResponse<>();
        if (technicalStatusResponses.getTotalElements() > 0) {
            successResponse.setMessage("List of request repair");
        } else {
            successResponse.setMessage("List of request repair is null");
        }
        successResponse.setData(technicalStatusResponses);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTechnicalStatus(@RequestBody TechnicalStatusRequest technicalStatusRequest) {
        TechnicalStatusResponse technicalStatusResponse = technicalStatusService.addTechnicalStatus(technicalStatusRequest);

        SuccessResponse<TechnicalStatusResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Created technical status successful.");
        successResponse.setData(technicalStatusResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTechnicalStatusById(@PathVariable Long id) {
        TechnicalStatusResponse technicalStatusResponse = technicalStatusService.getTechnicalStatusById(id);

        SuccessResponse<TechnicalStatusResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Get technical status by id.");
        successResponse.setData(technicalStatusResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTechnicalStatus(@PathVariable Long id, @RequestBody TechnicalStatusRequest technicalStatusRequest) {
        TechnicalStatusResponse technicalStatusResponse = technicalStatusService.updateTechnicalStatus(id, technicalStatusRequest);

        SuccessResponse<TechnicalStatusResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Updated technical status successful.");
        successResponse.setData(technicalStatusResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTechnicalStatusById(@PathVariable Long id) {
        technicalStatusService.deleteTechnicalStatusById(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSuccess(true);
        messageResponse.setMessage("Deleted technical status successful with id " + id);

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping("/request-repair/{id}")
    public ResponseEntity<?> deleteRequestRepair(@PathVariable Long id) {
        TechnicalStatusResponse technicalStatusResponse = technicalStatusService.deleteRequestRepair(id);

        SuccessResponse<TechnicalStatusResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Deleted request repair successful.");
        successResponse.setData(technicalStatusResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
