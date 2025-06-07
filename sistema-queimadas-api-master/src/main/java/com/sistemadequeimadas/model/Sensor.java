package com.sistemadequeimadas.model;

import com.sistemadequeimadas.enums.TipoSensor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_sensor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Código de identificação não pode estar em branco")
    @Size(min = 5, max = 50, message = "Código deve ter entre 5 e 50 caracteres")
    @Column(nullable = false, unique = true)
    private String codigoIdentificacao;

    @NotNull(message = "Tipo de sensor não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSensor tipoSensor;

    @NotNull(message = "Latitude não pode ser nula")
    private Double latitude;

    @NotNull(message = "Longitude não pode ser nula")
    private Double longitude;

    @NotNull(message = "Status 'ativo' não pode ser nulo")
    @Column(nullable = false)
    private boolean ativo = true;

    @NotNull(message = "Sensor deve estar associado a uma Área de Monitoramento")
    @ManyToOne
    @JoinColumn(name = "area_monitoramento_id", nullable = false)
    private AreaMonitoramento areaMonitoramento;

    public Sensor(String codigoIdentificacao, TipoSensor tipoSensor, Double latitude, Double longitude, AreaMonitoramento areaMonitoramento) {
        this.codigoIdentificacao = codigoIdentificacao;
        this.tipoSensor = tipoSensor;
        this.latitude = latitude;
        this.longitude = longitude;
        this.areaMonitoramento = areaMonitoramento;
        this.ativo = true;
    }

    public Sensor(String codigoIdentificacao, TipoSensor tipoSensor, Double latitude, Double longitude, AreaMonitoramento areaMonitoramento, boolean ativo) {
        this.codigoIdentificacao = codigoIdentificacao;
        this.tipoSensor = tipoSensor;
        this.latitude = latitude;
        this.longitude = longitude;
        this.areaMonitoramento = areaMonitoramento;
        this.ativo = ativo;
    }

    // Getters e setters manuais para compatibilidade com o padrão JavaBean
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoIdentificacao() {
        return codigoIdentificacao;
    }

    public void setCodigoIdentificacao(String codigoIdentificacao) {
        this.codigoIdentificacao = codigoIdentificacao;
    }

    public TipoSensor getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public AreaMonitoramento getAreaMonitoramento() {
        return areaMonitoramento;
    }

    public void setAreaMonitoramento(AreaMonitoramento areaMonitoramento) {
        this.areaMonitoramento = areaMonitoramento;
    }
}
