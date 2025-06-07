package com.sistemadequeimadas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemadequeimadas.enums.TipoAlerta;
import com.sistemadequeimadas.exception.ResourceNotFoundException;
import com.sistemadequeimadas.model.AreaMonitoramento;
import com.sistemadequeimadas.model.LeituraSensor;
import com.sistemadequeimadas.model.Sensor;
import com.sistemadequeimadas.repository.LeituraSensorRepository;
import com.sistemadequeimadas.repository.SensorRepository;

@Service
public class LeituraSensorService {

    @Autowired
    private LeituraSensorRepository leituraSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AlertaService alertaService;

    public LeituraSensor save(LeituraSensor leitura) {
        Sensor sensor = sensorRepository.findById(leitura.getSensor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with id " + leitura.getSensor().getId()));
        leitura.setSensor(sensor);

        if (leitura.getTimestampLeitura() == null) {
            leitura.setTimestampLeitura(LocalDateTime.now());
        }

        LeituraSensor savedLeitura = leituraSensorRepository.save(leitura);

        checkAndGenerateAlert(savedLeitura);

        return savedLeitura;
    }

    public List<LeituraSensor> findAll() {
        return leituraSensorRepository.findAll();
    }

    public Optional<LeituraSensor> findById(Long id) {
        return leituraSensorRepository.findById(id);
    }

    public List<LeituraSensor> findBySensorId(Long sensorId) {
        // Não é necessário ResourceNotFoundException aqui, pois um sensor pode existir mas não ter leituras
        return leituraSensorRepository.findBySensorId(sensorId);
    }

    private void checkAndGenerateAlert(LeituraSensor leitura) {
        if (leitura.getTemperatura() > 40.0 && leitura.getUmidade() < 15.0) {
            AreaMonitoramento area = leitura.getSensor().getAreaMonitoramento();
            TipoAlerta tipoAlerta = TipoAlerta.RISCO_ALTO_TEMPERATURA_UMIDADE;
            String mensagem = String.format("Risco alto de queimada detectado na área '%s' pelo sensor '%s'! Temperatura: %.1f°C, Umidade: %.1f%%.",
                    area.getNome(), leitura.getSensor().getCodigoIdentificacao(), leitura.getTemperatura(), leitura.getUmidade());
            alertaService.createAlerta(area, leitura, tipoAlerta, mensagem);
        }
        if (leitura.getDetectouFumaca() != null && leitura.getDetectouFumaca()) {
            AreaMonitoramento area = leitura.getSensor().getAreaMonitoramento();
            TipoAlerta tipoAlerta = TipoAlerta.FUMACA_DETECTADA;
            String mensagem = String.format("Fumaça detectada na área '%s' pelo sensor '%s'!",
                    area.getNome(), leitura.getSensor().getCodigoIdentificacao());
            alertaService.createAlerta(area, leitura, tipoAlerta, mensagem);
        }
    }
}