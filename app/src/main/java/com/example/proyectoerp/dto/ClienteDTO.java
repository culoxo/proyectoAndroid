package com.example.proyectoerp.dto;

import com.example.proyectoerp.model.Servicio;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClienteDTO {

    @SerializedName("id")
    private Long clienteId;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private boolean active;
    private List<Servicio> servicios;
    public ClienteDTO() {}

    public ClienteDTO(String nombre, String direccion, String email, String telefono, boolean active, List<Servicio> servicios) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.active = active;
        this.servicios = servicios;
    }

    public ClienteDTO(Long clienteId, String nombre, String direccion, String email, String telefono, boolean active, List<Servicio> servicios) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.active = active;
        this.servicios = servicios;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public ClienteDTO(Long clienteId, String nombre, String direccion, String email, String telefono, boolean active) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.active = active;
    }
/*
    public ClienteDTO(String nombre, String direccion, String email, String telefono, boolean active) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.active = active ;
    }
*/
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
