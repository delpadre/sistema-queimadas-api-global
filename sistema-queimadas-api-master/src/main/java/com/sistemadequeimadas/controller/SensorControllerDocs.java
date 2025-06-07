package com.sistemadequeimadas.controller;

import com.sistemadequeimadas.dto.SensorCreateDTO;
import com.sistemadequeimadas.dto.SensorUpdateDTO;
import com.sistemadequeimadas.model.Sensor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sensores", description = "Operações para gerenciamento de sensores")
public interface SensorControllerDocs {

	@Operation(summary = "Criar novo sensor", description = "Cadastra um novo sensor na base de dados.", requestBody = @RequestBody(description = "Dados do novo sensor", required = true, content = @Content(schema = @Schema(implementation = SensorCreateDTO.class), examples = @ExampleObject(name = "Exemplo de Criação", value = """
			    {
			        "codigoIdentificacao": "TEMP-FAZ-SP-001",
			        "tipoSensor": "TEMPERATURA",
			        "latitude": -23.5486,
			        "longitude": -46.6384,
			        "precisao": 0.1,
			        "intervaloLeitura": 300,
			        "limiteInferior": 15.0,
			        "limiteSuperior": 45.0,
			        "ativo": true,
			        "areaMonitoramentoId": 21
			    }
			"""))))
	@ApiResponse(responseCode = "201", description = "Sensor criado com sucesso")
	@PostMapping
	ResponseEntity<Sensor> createSensor(@Valid @RequestBody SensorCreateDTO dto);

	@Operation(summary = "Listar sensores", description = "Retorna todos os sensores cadastrados.")
	@ApiResponse(responseCode = "200", description = "Lista de sensores retornada com sucesso", content = @Content(schema = @Schema(implementation = Sensor.class), examples = @ExampleObject(name = "Exemplo de Lista", value = """
			    {
			        "content": [
			            {
			                "id": 21,
			                "codigoIdentificacao": "TEMP-FAZ-SP-001",
			                "tipoSensor": "TEMPERATURA",
			                "latitude": -23.5486,
			                "longitude": -46.6384,
			                "ativo": true,
			                "areaMonitoramento": {
			                    "id": 1,
			                    "nome": "Fazenda São João",
			                    "risco": "MODERADO"
			                }
			            },
			            {
			                "id": 22,
			                "codigoIdentificacao": "UMID-FAZ-SP-001",
			                "tipoSensor": "UMIDADE",
			                "latitude": -23.5490,
			                "longitude": -46.6380,
			                "ativo": true,
			                "areaMonitoramento": {
			                    "id": 1,
			                    "nome": "Fazenda São João",
			                    "risco": "MODERADO"
			                }
			            }
			        ]
			    }
			""")))
	@GetMapping
	ResponseEntity<List<Sensor>> getAllSensores();

	@Operation(summary = "Buscar sensor por ID", description = "Retorna um sensor específico pelo seu ID.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Sensor encontrado", content = @Content(schema = @Schema(implementation = Sensor.class), examples = @ExampleObject(name = "Exemplo de Sensor", value = """
					    {
					        "id": 21,
					        "codigoIdentificacao": "TEMP-FAZ-SP-001",
					        "tipoSensor": "TEMPERATURA",
					        "latitude": -23.5486,
					        "longitude": -46.6384,
					        "ativo": true,
					        "areaMonitoramento": {
					            "id": 1,
					            "nome": "Fazenda São João",
					            "risco": "MODERADO"
					        }
					    }
					"""))),
			@ApiResponse(responseCode = "404", description = "Sensor não encontrado") })
	@GetMapping("/{id}")
	ResponseEntity<Sensor> getSensorById(@PathVariable Long id);

	@Operation(summary = "Atualizar sensor", description = "Atualiza os dados de um sensor existente.", requestBody = @RequestBody(description = "Detalhes do sensor a ser atualizado", required = true, content = @Content(schema = @Schema(implementation = SensorUpdateDTO.class), examples = @ExampleObject(name = "Exemplo de Atualização", value = """
			    {
			        "codigoIdentificacao": "TEMP-001-FAZ-SP-ATU",
			        "tipoSensor": "TEMPERATURA",
			        "latitude": -23.5492,
			        "longitude": -46.6389,
			        "ativo": true,
			        "areaMonitoramentoId": 21
			    }
			"""))))
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Sensor atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Sensor não encontrado") })
	@PutMapping("/{id}")
	ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @Valid @RequestBody SensorUpdateDTO dto);

	@Operation(summary = "Excluir sensor", description = "Remove um sensor do sistema pelo ID.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Sensor removido com sucesso"),
			@ApiResponse(responseCode = "404", description = "Sensor não encontrado") })
	@DeleteMapping("/{id}")
	ResponseEntity<HttpStatus> deleteSensor(@PathVariable Long id);

	@Operation(summary = "Listar sensores por área", description = "Retorna todos os sensores de uma área de monitoramento.")
	@ApiResponse(responseCode = "200", description = "Lista de sensores retornada com sucesso")
	@GetMapping("/area/{areaId}")
	ResponseEntity<List<Sensor>> getSensoresByAreaMonitoramento(@PathVariable Long areaId);
}
