package com.sistemadequeimadas.service;

import com.sistemadequeimadas.exception.ResourceNotFoundException;
import com.sistemadequeimadas.model.AreaMonitoramento;
import com.sistemadequeimadas.repository.AreaMonitoramentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaMonitoramentoService {

    @Autowired
    private AreaMonitoramentoRepository areaMonitoramentoRepository;

    public AreaMonitoramento save(AreaMonitoramento area) {
        return areaMonitoramentoRepository.save(area);
    }

    public List<AreaMonitoramento> findAll() {
        return areaMonitoramentoRepository.findAll();
    }

    public Optional<AreaMonitoramento> findById(Long id) {
        return areaMonitoramentoRepository.findById(id);
    }

    public AreaMonitoramento update(Long id, AreaMonitoramento areaDetails) {
        return areaMonitoramentoRepository.findById(id).map(area -> {
            area.setNome(areaDetails.getNome());
            area.setDescricao(areaDetails.getDescricao());
            area.setLatitudeCentral(areaDetails.getLatitudeCentral());
            area.setLongitudeCentral(areaDetails.getLongitudeCentral());
            return areaMonitoramentoRepository.save(area);
        }).orElseThrow(() -> new ResourceNotFoundException("AreaMonitoramento not found with id " + id));
    }

    public void deleteById(Long id) {
        areaMonitoramentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AreaMonitoramento not found with id " + id));
        areaMonitoramentoRepository.deleteById(id);
    }
}