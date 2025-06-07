package com.sistemadequeimadas.repository;

import com.sistemadequeimadas.model.Sensor;
import com.sistemadequeimadas.model.AreaMonitoramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Optional<Sensor> findByCodigoIdentificacao(String codigoIdentificacao);

    List<Sensor> findByAreaMonitoramento(AreaMonitoramento area);
}