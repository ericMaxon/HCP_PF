package com.example.pf_hpa4.services.dto.responses.auth;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("title")
    String title;
    @SerializedName("mensaje")
    String message;
    @SerializedName("usuario")
    User usuario;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
