package com.sistemadequeimadas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadequeimadas.model.LeituraSensor;
import com.sistemadequeimadas.service.LeituraSensorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leituras")
@Tag(name = "Leituras de Sensor", description = "Operações para gerenciamento das leituras dos sensores")
public class LeituraSensorController {

    @Autowired
    private LeituraSensorService leituraSensorService;

    @Operation(
        summary = "Criar nova leitura de sensor",
        description = "Registra uma nova leitura para um sensor.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da leitura do sensor",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LeituraSensor.class),
                examples = @ExampleObject(name = "Exemplo de Leitura", value = """
                    {
                        "sensor": {
                            "id": 21
                        },
                        "temperatura": 38.5,
                        "umidade": 25.3,
                        "detectouFumaca": false,
                        "timestampLeitura": "2025-06-06T14:30:00"
                    }
                """)
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Leitura criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<LeituraSensor> createLeituraSensor(
            @Valid @RequestBody LeituraSensor leitura) {
        LeituraSensor savedLeitura = leituraSensorService.save(leitura);
        return new ResponseEntity<>(savedLeitura, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Listar leituras de sensores",
        description = "Retorna todas as leituras de sensores cadastradas."
    )
    @ApiResponse(responseCode = "200", description = "Lista de leituras retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<LeituraSensor>> getAllLeiturasSensor() {
        List<LeituraSensor> leituras = leituraSensorService.findAll();
        return new ResponseEntity<>(leituras, HttpStatus.OK);
    }

    @Operation(
        summary = "Buscar leitura por ID",
        description = "Retorna uma leitura específica pelo seu ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leitura encontrada"),
        @ApiResponse(responseCode = "404", description = "Leitura não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LeituraSensor> getLeituraSensorById(@PathVariable Long id) {
        return leituraSensorService.findById(id)
                .map(leitura -> new ResponseEntity<>(leitura, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
        summary = "Listar leituras por sensor",
        description = "Retorna todas as leituras de um sensor específico."
    )
    @ApiResponse(responseCode = "200", description = "Lista de leituras retornada com sucesso")
    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<List<LeituraSensor>> getLeiturasBySensor(@PathVariable Long sensorId) {
        List<LeituraSensor> leituras = leituraSensorService.findBySensorId(sensorId);
        return new ResponseEntity<>(leituras, HttpStatus.OK);
    }
}
