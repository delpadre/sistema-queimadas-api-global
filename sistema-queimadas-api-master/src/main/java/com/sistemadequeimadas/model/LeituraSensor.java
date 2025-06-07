package com.sistemadequeimadas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_leitura_sensor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Leitura deve estar associada a um Sensor")
    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    @JsonIgnore
    private Sensor sensor;

    @NotNull(message = "Temperatura não pode ser nula")
    @Min(value = -50, message = "Temperatura mínima é -50°C")
    @Max(value = 100, message = "Temperatura máxima é 100°C")
    @Column(nullable = false)
    private Double temperatura;

    @NotNull(message = "Umidade não pode ser nula")
    @Min(value = 0, message = "Umidade mínima é 0%")
    @Max(value = 100, message = "Umidade máxima é 100%")
    @Column(nullable = false)
    private Double umidade;

    private Boolean detectouFumaca;

    @Column(nullable = false)
    private LocalDateTime timestampLeitura;

    public LeituraSensor(Sensor sensor, Double temperatura, Double umidade, Boolean detectouFumaca) {
        this.sensor = sensor;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.detectouFumaca = detectouFumaca;
        this.timestampLeitura = LocalDateTime.now();
    }
}
