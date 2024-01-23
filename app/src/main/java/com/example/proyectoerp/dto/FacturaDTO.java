package com.example.proyectoerp.dto;

import com.google.gson.annotations.SerializedName;

public class FacturaDTO {

    @SerializedName("id")
    private Long servicioId;
    private boolean estado = true;

    public FacturaDTO(Long servicioId) {
        this.servicioId = servicioId;
    }

    public FacturaDTO(Long servicioId, boolean estado) {
        this.servicioId = servicioId;
        this.estado = estado;
    }

    public FacturaDTO(boolean estado) {
        this.estado = estado;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public boolean isActive() {
        return estado;
    }

    public void setActive(boolean estado) {
        this.estado = estado;
    }
}
