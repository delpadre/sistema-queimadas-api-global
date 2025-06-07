package com.sistemadequeimadas.repository;

import com.sistemadequeimadas.model.AreaMonitoramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaMonitoramentoRepository extends JpaRepository<AreaMonitoramento, Long> {
    // Optional<AreaMonitoramento> findByNome(String nome);
}