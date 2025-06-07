package com.sistemadequeimadas.service;

import com.sistemadequeimadas.exception.ResourceNotFoundException;
import com.sistemadequeimadas.model.Alerta;
import com.sistemadequeimadas.model.AreaMonitoramento;
import com.sistemadequeimadas.model.LeituraSensor;
import com.sistemadequeimadas.enums.TipoAlerta;
import com.sistemadequeimadas.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    public Alerta createAlerta(AreaMonitoramento area, LeituraSensor leitura, TipoAlerta tipoAlerta, String mensagem) {
        if (alertaRepository.findByLeituraSensorId(leitura.getId()).isPresent()) {
            System.out.println("Alerta já existe para a leitura " + leitura.getId() + ". Não será criado novamente.");
            return alertaRepository.findByLeituraSensorId(leitura.getId()).get();
        }

        Alerta novoAlerta = new Alerta(area, leitura, tipoAlerta, mensagem);
        System.out.println(">>> ALERTA GERADO: " + mensagem);
        return alertaRepository.save(novoAlerta);
    }

    public List<Alerta> findAll() {
        return alertaRepository.findAll();
    }

    public Optional<Alerta> findById(Long id) {
        return alertaRepository.findById(id);
    }

    public List<Alerta> findByStatus(String status) {
        return alertaRepository.findByStatus(status);
    }

    public Alerta updateStatus(Long id, String newStatus) {
        return alertaRepository.findById(id).map(alerta -> {
            alerta.setStatus(newStatus);
            return alertaRepository.save(alerta);
        }).orElseThrow(() -> new ResourceNotFoundException("Alerta not found with id " + id));
    }
}