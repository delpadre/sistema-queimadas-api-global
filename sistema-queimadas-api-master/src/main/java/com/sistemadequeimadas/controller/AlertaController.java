package com.sistemadequeimadas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sistemadequeimadas.model.Alerta;
import com.sistemadequeimadas.service.AlertaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "Operações para gerenciamento de alertas de queimadas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Operation(summary = "Listar alertas", description = "Retorna todos os alertas cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Alerta>> getAllAlertas() {
        List<Alerta> alertas = alertaService.findAll();
        return new ResponseEntity<>(alertas, HttpStatus.OK);
    }

    @Operation(summary = "Buscar alerta por ID", description = "Retorna um alerta específico pelo seu ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alerta encontrado"),
        @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Alerta> getAlertaById(@PathVariable Long id) {
        return alertaService.findById(id)
                .map(alerta -> new ResponseEntity<>(alerta, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Listar alertas por status", description = "Retorna todos os alertas filtrados por status.")
    @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Alerta>> getAlertasByStatus(@PathVariable String status) {
        List<Alerta> alertas = alertaService.findByStatus(status.toUpperCase());
        return new ResponseEntity<>(alertas, HttpStatus.OK);
    }

    @Operation(
            summary = "Atualizar status do alerta",
            description = "Atualiza o status de um alerta existente.",
            requestBody = @RequestBody(
                    description = "Novo status do alerta. Ex: 'RESOLVIDO', 'FALSO_POSITIVO'",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = {
                                @ExampleObject(
                                        name = "Status RESOLVIDO",
                                        value = "\"RESOLVIDO\""
                                ),
                                @ExampleObject(
                                        name = "Status FALSO_POSITIVO",
                                        value = "\"FALSO_POSITIVO\""
                                )
                            }
                    )
            )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status do alerta atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Alerta> updateAlertaStatus(@PathVariable Long id, @RequestBody String newStatus) {
        try {
            Alerta updatedAlerta = alertaService.updateStatus(id, newStatus.toUpperCase());
            return new ResponseEntity<>(updatedAlerta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
