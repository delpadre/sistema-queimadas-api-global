package com.sistemadequeimadas.repository;

import com.sistemadequeimadas.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

    List<LeituraSensor> findBySensorId(Long sensorId);

    Optional<LeituraSensor> findTopBySensorIdOrderByTimestampLeituraDesc(Long sensorId);
}