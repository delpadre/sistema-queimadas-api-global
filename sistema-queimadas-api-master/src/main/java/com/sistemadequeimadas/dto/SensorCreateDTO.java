package com.sistemadequeimadas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SensorCreateDTO {

    @NotBlank
    private String codigoIdentificacao;
    @NotBlank
    private String tipoSensor;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private Boolean ativo;
    @NotNull
    private Long areaMonitoramentoId;

    // Getters e setters
    public String getCodigoIdentificacao() {
        return codigoIdentificacao;
    }

    public void setCodigoIdentificacao(String codigoIdentificacao) {
        this.codigoIdentificacao = codigoIdentificacao;
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getAreaMonitoramentoId() {
        return areaMonitoramentoId;
    }

    public void setAreaMonitoramentoId(Long areaMonitoramentoId) {
        this.areaMonitoramentoId = areaMonitoramentoId;
    }
}
