package com.sistemadequeimadas.repository;

import com.sistemadequeimadas.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByStatus(String status);
    List<Alerta> findByAreaMonitoramentoId(Long areaMonitoramentoId);
    Optional<Alerta> findByLeituraSensorId(Long leituraSensorId);
}