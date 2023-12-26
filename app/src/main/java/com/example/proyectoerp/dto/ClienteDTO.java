package com.example.proyectoerp.dto;

public class ClienteDTO {

    private Long clienteId;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private boolean active;
    public ClienteDTO() {}

    public ClienteDTO(Long clienteId, String nombre, String direccion, String email, String telefono, boolean active) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.active = active;
    }

    public ClienteDTO(String nombre, String direccion, String email, String telefono, boolean active) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.active = active ;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
