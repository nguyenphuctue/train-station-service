package com.jungdo.trainstationservice.service.impl;

import com.jungdo.trainstationservice.exception.ResourceNotFoundException;
import com.jungdo.trainstationservice.form.TrainStationFilterForm;
import com.jungdo.trainstationservice.entity.Files;
import com.jungdo.trainstationservice.entity.TrainStation;
import com.jungdo.trainstationservice.entity.enums.EntityType;
import com.jungdo.trainstationservice.entity.enums.FileType;
import com.jungdo.trainstationservice.payload.request.TrainStationRequest;
import com.jungdo.trainstationservice.payload.response.NameStationResponse;
import com.jungdo.trainstationservice.payload.response.TrainStationResponse;
import com.jungdo.trainstationservice.repository.FileRepository;
import com.jungdo.trainstationservice.repository.TrainStationRepository;
import com.jungdo.trainstationservice.service.TrainStationService;
import com.jungdo.trainstationservice.specification.TrainStationSpecification;
import com.jungdo.trainstationservice.util.PaginationUtils;
import com.jungdo.trainstationservice.util.RandomStringGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainStationServiceImpl implements TrainStationService {

    private TrainStationRepository trainStationRepository;

    private FileRepository fileRepository;

    private ModelMapper modelMapper;

    @Override
    public Page<TrainStationResponse> getAllTrainStations(int page, int size, TrainStationFilterForm filterForm) {
        PaginationUtils.validatePageNumberAndSize(page, size);

        String sort = "id";
        if (!filterForm.getSort().equals("")) sort = filterForm.getSort();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, sort);

        Specification<TrainStation> where = TrainStationSpecification.buildWhere(filterForm);
        Page<TrainStation> trainStationPage = trainStationRepository.findAll(where, pageable);

        List<TrainStationResponse> trainStationResponses = trainStationPage.stream()
                .map(trainStation -> modelMapper.map(trainStation, TrainStationResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(trainStationResponses, pageable, trainStationPage.getTotalElements());
    }

    @Override
    public List<NameStationResponse> getAllNameStations() {
        List<TrainStation> trainStations = trainStationRepository.findAll();

        return trainStations.stream()
                .map(trainStation -> modelMapper.map(trainStation, NameStationResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public TrainStationResponse getTrainStationById(Long id) {
        TrainStation trainStation = trainStationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(id)));

        return modelMapper.map(trainStation, TrainStationResponse.class);
    }

    @Override
    public TrainStationResponse addTrainStation(TrainStationRequest trainStationRequest) {
        TrainStation trainStation = modelMapper.map(trainStationRequest, TrainStation.class);
        TrainStation savedTrainStation = trainStationRepository.save(trainStation);

        for (String url : trainStationRequest.getImageUrls()) {
            Files files = new Files();
            files.setName("Train_Station_" + savedTrainStation.getId() + "_"
                    + RandomStringGenerator.generateRandomString());
            files.setEntityType(EntityType.TRAIN_STATION);
            files.setFileType(FileType.IMAGE);
            files.setRelationId(savedTrainStation.getId());
            files.setUrl(url);
            fileRepository.save(files);
        }

        return modelMapper.map(savedTrainStation, TrainStationResponse.class);
    }

    @Override
    public TrainStationResponse updateTrainStation(Long id, TrainStationRequest trainStationRequest) {
        TrainStation trainStation = trainStationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(id)));
        modelMapper.map(trainStationRequest, trainStation);
        TrainStation updatedTrainStation = trainStationRepository.save(trainStation);

        fileRepository.deleteFilesByEntityTypeAndFileTypeAndRelationId(
                EntityType.TRAIN_STATION, FileType.IMAGE, updatedTrainStation.getId());

        for (String url : trainStationRequest.getImageUrls()) {
            Files files = new Files();
            files.setName("Train_Station_" + updatedTrainStation.getId() + "_"
                    + RandomStringGenerator.generateRandomString());
            files.setEntityType(EntityType.TRAIN_STATION);
            files.setFileType(FileType.IMAGE);
            files.setRelationId(updatedTrainStation.getId());
            files.setUrl(url);
            fileRepository.save(files);
        }

        return modelMapper.map(updatedTrainStation, TrainStationResponse.class);
    }

    @Override
    public void deleteTrainStationById(Long id) {
        TrainStation trainStation = trainStationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(id)));
        trainStationRepository.delete(trainStation);
    }
}
