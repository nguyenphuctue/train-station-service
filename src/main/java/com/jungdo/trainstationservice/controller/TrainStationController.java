package com.jungdo.trainstationservice.controller;

import com.jungdo.trainstationservice.form.TrainStationFilterForm;
import com.jungdo.trainstationservice.payload.request.TrainStationRequest;
import com.jungdo.trainstationservice.payload.response.MessageResponse;
import com.jungdo.trainstationservice.payload.response.NameStationResponse;
import com.jungdo.trainstationservice.payload.response.SuccessResponse;
import com.jungdo.trainstationservice.payload.response.TrainStationResponse;
import com.jungdo.trainstationservice.service.TrainStationService;
import com.jungdo.trainstationservice.util.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/train-stations")
public class TrainStationController {

    @Autowired
    private TrainStationService trainStationService;

    @GetMapping
    public ResponseEntity<?> getAllTrainStations(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String property,
            @RequestParam(required = false) String sort) {
        TrainStationFilterForm trainStationFilterForm = new TrainStationFilterForm(search, property, sort);
        Page<TrainStationResponse> trainStationResponses = trainStationService.getAllTrainStations(page, size, trainStationFilterForm);

        SuccessResponse<Page<TrainStationResponse>> successResponse = new SuccessResponse<>();
        if (trainStationResponses.getTotalElements() > 0) {
            successResponse.setMessage("List of train stations");
        } else {
            successResponse.setMessage("List of train stations is null");
        }
        successResponse.setData(trainStationResponses);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTrainStation(@RequestBody TrainStationRequest trainStationRequest) {
        TrainStationResponse trainStationResponse = trainStationService.addTrainStation(trainStationRequest);

        SuccessResponse<TrainStationResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Created train station successful.");
        successResponse.setData(trainStationResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainStationById(@PathVariable Long id) {
        TrainStationResponse trainStationResponse = trainStationService.getTrainStationById(id);

        SuccessResponse<TrainStationResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Get train station by id.");
        successResponse.setData(trainStationResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<?> getAllNameStation() {
        List<NameStationResponse> nameStationResponses = trainStationService.getAllNameStations();

        SuccessResponse<List<NameStationResponse>> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Get all name train station.");
        successResponse.setData(nameStationResponses);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTrainStation(@PathVariable Long id, @RequestBody TrainStationRequest trainStationRequest) {
        TrainStationResponse trainStationResponse = trainStationService.updateTrainStation(id, trainStationRequest);

        SuccessResponse<TrainStationResponse> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Updated train station successful.");
        successResponse.setData(trainStationResponse);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrainStationById(@PathVariable Long id) {
        trainStationService.deleteTrainStationById(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSuccess(true);
        messageResponse.setMessage("Deleted train station successful with id " + id);

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
