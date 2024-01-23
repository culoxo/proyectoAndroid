package com.example.proyectoerp.dto;

import com.google.gson.annotations.SerializedName;

public class ServicioDTO {

    @SerializedName("id")
    private Long servicioId;
    private String nombre;
    private boolean active = true;

    public ServicioDTO(Long servicioId) {
        this.servicioId = servicioId;
    }

    public ServicioDTO(String nombre, boolean active) {
        this.nombre = nombre;
        this.active = active;
    }

    public ServicioDTO(Long servicioId, String nombre, boolean active) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.active = active;
    }

    public ServicioDTO() {
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
