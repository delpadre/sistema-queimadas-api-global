package com.sistemadequeimadas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_area_monitoramento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaMonitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da área não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, unique = true)
    private String nome;

    @Size(max = 500, message = "Descrição não pode exceder 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "Latitude central não pode ser nula")
    private Double latitudeCentral;

    @NotNull(message = "Longitude central não pode ser nula")
    private Double longitudeCentral;

    public AreaMonitoramento(String nome, String descricao, Double latitudeCentral, Double longitudeCentral) {
        this.nome = nome;
        this.descricao = descricao;
        this.latitudeCentral = latitudeCentral;
        this.longitudeCentral = longitudeCentral;
    }
}