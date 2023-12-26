package com.example.proyectoerp.dto;

public class UsuarioDTO {
    private Long usuarioId;
    private String username;
    private String name;
    private String surname;
    private String surname2;
    private String email;
    private String password;
    private Boolean active= true;
    private String preguntaSeg;
    private String respuestaSeg;
    private Boolean delete = false;
    private Boolean admin = false;

    public UsuarioDTO(String username, String name, String surname, String surname2, String email, String password, Boolean active, String preguntaSeg, String respuestaSeg, Boolean delete, Boolean admin) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.surname2 = surname2;
        this.email = email;
        this.password = password;
        this.active = active;
        this.preguntaSeg = preguntaSeg;
        this.respuestaSeg = respuestaSeg;
        this.delete = delete;
        this.admin = admin;
    }

    public UsuarioDTO(Long usuarioId, String username, String name, String surname, String surname2, String email, String password, Boolean active, String preguntaSeg, String respuestaSeg, Boolean delete, Boolean admin) {
        this.usuarioId = usuarioId;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.surname2 = surname2;
        this.email = email;
        this.password = password;
        this.active = active;
        this.preguntaSeg = preguntaSeg;
        this.respuestaSeg = respuestaSeg;
        this.delete = delete;
        this.admin = admin;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPreguntaSeg() {
        return preguntaSeg;
    }

    public void setPreguntaSeg(String preguntaSeg) {
        this.preguntaSeg = preguntaSeg;
    }

    public String getRespuestaSeg() {
        return respuestaSeg;
    }

    public void setRespuestaSeg(String respuestaSeg) {
        this.respuestaSeg = respuestaSeg;
    }
}
