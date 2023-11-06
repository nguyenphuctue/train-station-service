package com.jungdo.trainstationservice.service.impl;

import com.jungdo.trainstationservice.exception.ResourceNotFoundException;
import com.jungdo.trainstationservice.entity.Files;
import com.jungdo.trainstationservice.entity.enums.EntityType;
import com.jungdo.trainstationservice.entity.enums.FileType;
import com.jungdo.trainstationservice.repository.FileRepository;
import com.jungdo.trainstationservice.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    @Override
    public List<Files> getImagesByTypeAndRelationId(String entityType, String fileType, Long relationId) {
        FileType fType;
        if (fileType.equals("IMAGE")) {
            fType = FileType.IMAGE;
        } else if (fileType.equals("PDF")) {
            fType = FileType.PDF;
        } else {
            throw new ResourceNotFoundException("File", "type, relation id", fileType + "," + relationId);
        }

        EntityType eType;
        if (entityType.equals("TRAIN_STATION")) {
            eType = EntityType.TRAIN_STATION;
        } else if (entityType.equals("TECHNICAL_STATUS")) {
            eType = EntityType.TECHNICAL_STATUS;
        } else {
            throw new ResourceNotFoundException("File", "type, relation id", fileType + "," + relationId);
        }
        List<Files> files = fileRepository.findFilesByEntityTypeAndFileTypeAndRelationId(eType, fType, relationId);

        if (files.isEmpty()) {
            throw new ResourceNotFoundException("File", "type, relation id", fileType + "," + relationId);
        }

        return files;
    }
}
