package com.jungdo.trainstationservice.service;

import com.jungdo.trainstationservice.entity.Files;

import java.util.List;

public interface FileService {
    public List<Files> getImagesByTypeAndRelationId(String entityFile, String fileType, Long relationId);
}
