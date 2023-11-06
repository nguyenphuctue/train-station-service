package com.jungdo.trainstationservice.repository;

import com.jungdo.trainstationservice.entity.TechnicalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalStatusRepository extends JpaRepository<TechnicalStatus, Long>, JpaSpecificationExecutor<TechnicalStatus> {
}
