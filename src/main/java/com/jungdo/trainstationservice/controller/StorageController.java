package com.jungdo.trainstationservice.controller;

import com.jungdo.trainstationservice.entity.Files;
import com.jungdo.trainstationservice.payload.response.SuccessResponse;
import com.jungdo.trainstationservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class StorageController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public ResponseEntity<?> getImagesByTypeAndRelationId(
            @RequestParam String entityType,
            @RequestParam String fileType,
            @RequestParam Long relationId) {
        List<Files> images = fileService.getImagesByTypeAndRelationId(entityType, fileType, relationId);

        SuccessResponse<List<Files>> successResponse = new SuccessResponse<>();
        successResponse.setMessage("Get images successful.");
        successResponse.setData(images);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
