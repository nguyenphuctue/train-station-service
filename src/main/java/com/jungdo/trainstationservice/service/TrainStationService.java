package com.jungdo.trainstationservice.service;

import com.jungdo.trainstationservice.form.TrainStationFilterForm;
import com.jungdo.trainstationservice.payload.request.TrainStationRequest;
import com.jungdo.trainstationservice.payload.response.NameStationResponse;
import com.jungdo.trainstationservice.payload.response.TrainStationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrainStationService {
    public Page<TrainStationResponse> getAllTrainStations(int page, int size, TrainStationFilterForm trainStationFilterForm);

    public List<NameStationResponse> getAllNameStations();

    public TrainStationResponse getTrainStationById(Long id);

    public TrainStationResponse addTrainStation(TrainStationRequest trainStationRequest);

    public TrainStationResponse updateTrainStation(Long id, TrainStationRequest trainStationRequest);

    public void deleteTrainStationById(Long id);
}
