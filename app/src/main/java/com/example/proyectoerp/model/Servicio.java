package com.example.proyectoerp.model;

import com.google.gson.annotations.SerializedName;


public class Servicio  {

    @SerializedName("id")
    private Long servicioId;
    private String nombre;
    private boolean deleted = false;
    private boolean active = true;

    public Servicio(Long servicioId) {
        this.servicioId = servicioId;
    }

    public Servicio(Long servicioId, String nombre, boolean deleted, boolean active) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.deleted = deleted;
        this.active = active;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
