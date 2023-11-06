package com.jungdo.trainstationservice.service.impl;

import com.jungdo.trainstationservice.entity.Files;
import com.jungdo.trainstationservice.entity.TechnicalStatus;
import com.jungdo.trainstationservice.entity.TrainStation;
import com.jungdo.trainstationservice.entity.enums.EntityType;
import com.jungdo.trainstationservice.entity.enums.FileType;
import com.jungdo.trainstationservice.exception.ResourceNotFoundException;
import com.jungdo.trainstationservice.form.TechnicalStatusFilterForm;
import com.jungdo.trainstationservice.payload.request.TechnicalStatusRequest;
import com.jungdo.trainstationservice.payload.response.TechnicalStatusResponse;
import com.jungdo.trainstationservice.repository.FileRepository;
import com.jungdo.trainstationservice.repository.TechnicalStatusRepository;
import com.jungdo.trainstationservice.repository.TrainStationRepository;
import com.jungdo.trainstationservice.service.TechnicalStatusService;
import com.jungdo.trainstationservice.specification.RequestRepairSpecification;
import com.jungdo.trainstationservice.specification.TechnicalStatusSpecification;
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
public class TechnicalStatusServiceImpl implements TechnicalStatusService {

    private TechnicalStatusRepository technicalStatusRepository;

    private TrainStationRepository trainStationRepository;

    private FileRepository fileRepository;

    private ModelMapper modelMapper;

    @Override
    public Page<TechnicalStatusResponse> getAllTechnicalStatus(int page, int size, TechnicalStatusFilterForm filterForm) {
        PaginationUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Specification<TechnicalStatus> where = TechnicalStatusSpecification.buildWhere(filterForm);
        Page<TechnicalStatus> technicalStatusPage = technicalStatusRepository.findAll(where, pageable);

        modelMapper.typeMap(TechnicalStatus.class, TechnicalStatusResponse.class).addMapping(TechnicalStatus::getTrainStation, TechnicalStatusResponse::setTrainStationResponse);

        List<TechnicalStatusResponse> technicalStatusResponses = technicalStatusPage.stream().map(technicalStatus -> modelMapper.map(technicalStatus, TechnicalStatusResponse.class)).collect(Collectors.toList());

        return new PageImpl<>(technicalStatusResponses, pageable, technicalStatusPage.getTotalElements());
    }

    @Override
    public Page<TechnicalStatusResponse> getAllTechnicalStatusHaveRequestRepair(int page, int size, TechnicalStatusFilterForm filterForm) {
        PaginationUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Specification<TechnicalStatus> where = RequestRepairSpecification.buildWhere(filterForm);
        Page<TechnicalStatus> technicalStatusPage = technicalStatusRepository.findAll(where, pageable);

        modelMapper.typeMap(TechnicalStatus.class, TechnicalStatusResponse.class).addMapping(TechnicalStatus::getTrainStation, TechnicalStatusResponse::setTrainStationResponse);

        List<TechnicalStatusResponse> technicalStatusResponses = technicalStatusPage.stream().map(technicalStatus -> modelMapper.map(technicalStatus, TechnicalStatusResponse.class)).collect(Collectors.toList());

        return new PageImpl<>(technicalStatusResponses, pageable, technicalStatusPage.getTotalElements());
    }

    @Override
    public TechnicalStatusResponse getTechnicalStatusById(Long id) {
        TechnicalStatus technicalStatus = technicalStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technical status", "id", String.valueOf(id)));
        modelMapper.typeMap(TechnicalStatus.class, TechnicalStatusResponse.class).addMapping(TechnicalStatus::getTrainStation, TechnicalStatusResponse::setTrainStationResponse);

        return modelMapper.map(technicalStatus, TechnicalStatusResponse.class);
    }

    @Override
    public TechnicalStatusResponse addTechnicalStatus(TechnicalStatusRequest technicalStatusRequest) {
        TechnicalStatus technicalStatus = modelMapper.map(technicalStatusRequest, TechnicalStatus.class);
        TrainStation trainStation = trainStationRepository.findById(technicalStatusRequest.getTrainStation())
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(technicalStatusRequest.getTrainStation())));
        technicalStatus.setTrainStation(trainStation);
        TechnicalStatus savedTechnicalStatus = technicalStatusRepository.save(technicalStatus);

        for (String url : technicalStatusRequest.getImageUrls()) {
            Files files = new Files();
            files.setName("Technical_Status_" + savedTechnicalStatus.getId() + "_" + RandomStringGenerator.generateRandomString());
            files.setEntityType(EntityType.TECHNICAL_STATUS);
            files.setFileType(FileType.IMAGE);
            files.setRelationId(savedTechnicalStatus.getId());
            files.setUrl(url);
            fileRepository.save(files);
        }

        modelMapper.typeMap(TechnicalStatus.class, TechnicalStatusResponse.class).addMapping(TechnicalStatus::getTrainStation, TechnicalStatusResponse::setTrainStationResponse);

        return modelMapper.map(savedTechnicalStatus, TechnicalStatusResponse.class);
    }

    @Override
    public TechnicalStatusResponse updateTechnicalStatus(Long id, TechnicalStatusRequest technicalStatusRequest) {
        TechnicalStatus technicalStatus = technicalStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technical status", "id", String.valueOf(id)));
        modelMapper.map(technicalStatusRequest, technicalStatus);
        TrainStation trainStation = trainStationRepository.findById(technicalStatusRequest.getTrainStation())
                .orElseThrow(() -> new ResourceNotFoundException("Train station", "id", String.valueOf(technicalStatusRequest.getTrainStation())));
        technicalStatus.setTrainStation(trainStation);
        TechnicalStatus updatedTechnicalStatus = technicalStatusRepository.save(technicalStatus);

        fileRepository.deleteFilesByEntityTypeAndFileTypeAndRelationId(EntityType.TECHNICAL_STATUS, FileType.IMAGE, updatedTechnicalStatus.getId());

        for (String url : technicalStatusRequest.getImageUrls()) {
            Files files = new Files();
            files.setName("Technical_Status_" + updatedTechnicalStatus.getId() + "_" + RandomStringGenerator.generateRandomString());
            files.setEntityType(EntityType.TECHNICAL_STATUS);
            files.setFileType(FileType.IMAGE);
            files.setRelationId(updatedTechnicalStatus.getId());
            files.setUrl(url);
            fileRepository.save(files);
        }

        modelMapper.typeMap(TechnicalStatus.class, TechnicalStatusResponse.class).addMapping(TechnicalStatus::getTrainStation, TechnicalStatusResponse::setTrainStationResponse);

        return modelMapper.map(updatedTechnicalStatus, TechnicalStatusResponse.class);
    }

    @Override
    public void deleteTechnicalStatusById(Long id) {
        TechnicalStatus technicalStatus = technicalStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technical status", "id", String.valueOf(id)));

        technicalStatusRepository.delete(technicalStatus);
    }

    @Override
    public TechnicalStatusResponse deleteRequestRepair(Long id) {
        TechnicalStatus technicalStatus = technicalStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technical status", "id", String.valueOf(id)));
        technicalStatus.setRequestRepair(false);
        TechnicalStatus updatedTechnicalStatus = technicalStatusRepository.save(technicalStatus);

        modelMapper.typeMap(TechnicalStatus.class, TechnicalStatusResponse.class).addMapping(TechnicalStatus::getTrainStation, TechnicalStatusResponse::setTrainStationResponse);

        return modelMapper.map(updatedTechnicalStatus, TechnicalStatusResponse.class);
    }
}
