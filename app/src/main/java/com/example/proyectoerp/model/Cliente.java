package com.example.proyectoerp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cliente{
    @SerializedName("id")
    private Long clienteId;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private boolean deleted = false;

    private boolean active = true;
    private List<Servicio> servicios;

    public Cliente(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente(Long clienteId, String nombre,  String direccion, String email, String telefono, boolean deleted, boolean active) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.deleted = deleted;
        this.active = active;
    }

    public Cliente(Long clienteId, String nombre, String direccion, String email, String telefono, boolean deleted, boolean active, List<Servicio> servicios) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.deleted = deleted;
        this.active = active;
        this.servicios = servicios;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    @Override
    public String toString() {
        return "ID: " + getClienteId() + ", Name: " + getNombre();
    }
}
