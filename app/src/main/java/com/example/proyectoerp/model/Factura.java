package com.example.proyectoerp.model;

import com.google.gson.annotations.SerializedName;

public class Factura {

    @SerializedName("id")
    private Long facturaId;
    private boolean deleted = false;
    private boolean estado;

    public Factura(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Factura(Long facturaId, boolean deleted, boolean estado) {
        this.facturaId = facturaId;
        this.deleted = deleted;
        this.estado = estado;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isActive() {
        return estado;
    }

    public void setActive(boolean active) {
        this.estado = estado;
    }
}
