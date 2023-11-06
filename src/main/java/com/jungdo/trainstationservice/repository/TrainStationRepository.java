package com.jungdo.trainstationservice.repository;

import com.jungdo.trainstationservice.entity.TrainStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainStationRepository extends JpaRepository<TrainStation, Long>, JpaSpecificationExecutor<TrainStation> {
    @Query("select t from TrainStation t where (:name is null or t.nameStation like %:name%)")
    List<TrainStation> findByName(@Param("name") String name);
}
