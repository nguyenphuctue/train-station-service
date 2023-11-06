package com.jungdo.trainstationservice.repository;

import com.jungdo.trainstationservice.entity.Files;
import com.jungdo.trainstationservice.entity.enums.EntityType;
import com.jungdo.trainstationservice.entity.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<Files, Long> {
    List<Files> findFilesByEntityTypeAndFileTypeAndRelationId(EntityType entityType, FileType fileType, Long relationId);

    @Transactional
    void deleteFilesByEntityTypeAndFileTypeAndRelationId(EntityType entityType, FileType fileType, Long relationId);
}
