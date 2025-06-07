package com.sistemadequeimadas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadequeimadas.dto.SensorCreateDTO;
import com.sistemadequeimadas.dto.SensorUpdateDTO;
import com.sistemadequeimadas.model.Sensor;
import com.sistemadequeimadas.service.SensorService;

@RestController
@RequestMapping("/api/sensores")
public class SensorController implements SensorControllerDocs {

    @Autowired
    private SensorService sensorService;

    @Override
    public ResponseEntity<Sensor> createSensor(@RequestBody SensorCreateDTO dto) {
        Sensor savedSensor = sensorService.save(dto);
        return new ResponseEntity<>(savedSensor, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Sensor>> getAllSensores() {
        List<Sensor> sensores = sensorService.findAll();
        return new ResponseEntity<>(sensores, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Sensor> getSensorById(Long id) {
        return sensorService.findById(id)
                .map(sensor -> new ResponseEntity<>(sensor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Sensor> updateSensor(Long id, @RequestBody SensorUpdateDTO dto) {
        Sensor updatedSensor = sensorService.update(id, dto);
        return new ResponseEntity<>(updatedSensor, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteSensor(Long id) {
        sensorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Sensor>> getSensoresByAreaMonitoramento(Long areaId) {
        List<Sensor> sensores = sensorService.findByAreaMonitoramento(areaId);
        return new ResponseEntity<>(sensores, HttpStatus.OK);
    }
}
