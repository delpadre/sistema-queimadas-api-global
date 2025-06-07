package com.sistemadequeimadas.service;

import com.sistemadequeimadas.exception.ResourceNotFoundException;
import com.sistemadequeimadas.model.Sensor;
import com.sistemadequeimadas.repository.SensorRepository;
import com.sistemadequeimadas.repository.AreaMonitoramentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.sistemadequeimadas.model.AreaMonitoramento;
import com.sistemadequeimadas.dto.SensorCreateDTO;
import com.sistemadequeimadas.dto.SensorUpdateDTO;
import com.sistemadequeimadas.enums.TipoSensor;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AreaMonitoramentoRepository areaMonitoramentoRepository;

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> findById(Long id) {
        return sensorRepository.findById(id);
    }

    public Sensor save(SensorCreateDTO dto) {
        if (dto.getAreaMonitoramentoId() == null) {
            throw new IllegalArgumentException(
                    "Sensor must be associated with an existing AreaMonitoramento with an ID.");
        }

        var area = areaMonitoramentoRepository.findById(dto.getAreaMonitoramentoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                "AreaMonitoramento not found with id " + dto.getAreaMonitoramentoId()));

        // Corrigido para evitar unboxing de null
        boolean ativo = Boolean.TRUE.equals(dto.getAtivo());
        Sensor sensor = new Sensor(
                dto.getCodigoIdentificacao(),
                TipoSensor.valueOf(dto.getTipoSensor()),
                dto.getLatitude(),
                dto.getLongitude(),
                area,
                ativo
        );

        return sensorRepository.save(sensor);
    }

    public Sensor update(Long id, SensorUpdateDTO dto) {
        return sensorRepository.findById(id).map(sensor -> {
            sensor.setCodigoIdentificacao(dto.getCodigoIdentificacao());
            sensor.setTipoSensor(TipoSensor.valueOf(dto.getTipoSensor()));
            sensor.setLatitude(dto.getLatitude());
            sensor.setLongitude(dto.getLongitude());
            sensor.setAtivo(Boolean.TRUE.equals(dto.getAtivo()));
            if (dto.getAreaMonitoramentoId() != null) {
                AreaMonitoramento area = areaMonitoramentoRepository.findById(dto.getAreaMonitoramentoId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                        "AreaMonitoramento not found with id " + dto.getAreaMonitoramentoId()));
                sensor.setAreaMonitoramento(area);
            }
            return sensorRepository.save(sensor);
        }).orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id " + id));
    }

    public void deleteById(Long id) {
        sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id " + id));
        sensorRepository.deleteById(id);
    }

    public List<Sensor> findByAreaMonitoramento(Long areaId) {
        AreaMonitoramento area = areaMonitoramentoRepository.findById(areaId)
                .orElseThrow(() -> new ResourceNotFoundException("AreaMonitoramento not found with id " + areaId));
        return sensorRepository.findByAreaMonitoramento(area);
    }

}
