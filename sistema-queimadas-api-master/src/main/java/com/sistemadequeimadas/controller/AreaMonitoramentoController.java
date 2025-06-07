package com.sistemadequeimadas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sistemadequeimadas.model.AreaMonitoramento;
import com.sistemadequeimadas.service.AreaMonitoramentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/areas")
@Tag(name = "Áreas de Monitoramento", description = "Operações para gerenciamento das áreas de monitoramento")
public class AreaMonitoramentoController {

    @Autowired
    private AreaMonitoramentoService areaMonitoramentoService;

    @Operation(
            summary = "Criar nova área de monitoramento",
            description = "Cadastra uma nova área de monitoramento.",
            requestBody = @RequestBody(
                    description = "Dados da nova área",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AreaMonitoramento.class),
                            examples = @ExampleObject(name = "Exemplo de Área", value = """
                    {
                        "nome": "Reserva Natural Vale Verde",
                        "descricao": "Área de preservação ambiental com mata atlântica nativa",
                        "latitudeCentral": -23.5505,
                        "longitudeCentral": -46.6333,
                        "tamanhoHectares": 150.5,
                        "risco": "MODERADO",
                        "ultimaVistoria": "2024-01-15"
                    }
                """)
                    )
            )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Área criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<AreaMonitoramento> createAreaMonitoramento(
            @Valid @RequestBody AreaMonitoramento area) {
        AreaMonitoramento savedArea = areaMonitoramentoService.save(area);
        return new ResponseEntity<>(savedArea, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar áreas de monitoramento", description = "Retorna todas as áreas de monitoramento cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de áreas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AreaMonitoramento>> getAllAreasMonitoramento() {
        List<AreaMonitoramento> areas = areaMonitoramentoService.findAll();
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }

    @Operation(summary = "Buscar área por ID", description = "Retorna uma área de monitoramento específica pelo seu ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Área encontrada"),
        @ApiResponse(responseCode = "404", description = "Área não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AreaMonitoramento> getAreaMonitoramentoById(@PathVariable Long id) {
        return areaMonitoramentoService.findById(id)
                .map(area -> new ResponseEntity<>(area, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Atualizar área de monitoramento",
            description = "Atualiza os dados de uma área existente.",
            requestBody = @RequestBody(
                    description = "Dados atualizados da área",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AreaMonitoramento.class),
                            examples = @ExampleObject(name = "Exemplo de Atualização", value = """
                    {
                        "nome": "Fazenda São João - Setor Norte",
                        "descricao": "Área de preservação ambiental com monitoramento intensivo",
                        "latitudeCentral": -23.5508,
                        "longitudeCentral": -46.6335,
                        "tamanhoHectares": 170.0,
                        "risco": "ALTO",
                        "ultimaVistoria": "2024-06-01"
                    }
                """)
                    )
            )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Área atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Área não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AreaMonitoramento> updateAreaMonitoramento(
            @PathVariable Long id,
            @Valid @RequestBody AreaMonitoramento areaDetails) {
        AreaMonitoramento updatedArea = areaMonitoramentoService.update(id, areaDetails);
        return new ResponseEntity<>(updatedArea, HttpStatus.OK);
    }

    @Operation(summary = "Excluir área de monitoramento", description = "Remove uma área de monitoramento pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Área removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Área não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAreaMonitoramento(@PathVariable Long id) {
        areaMonitoramentoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
