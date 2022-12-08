package com.example.pf_hpa4.services.dto.request.auth;

public class AuthPayload {
    String usuario;

    public AuthPayload(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    String contrasena;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
