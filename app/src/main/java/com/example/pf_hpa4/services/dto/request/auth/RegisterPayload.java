package com.example.pf_hpa4.services.dto.request.auth;

import com.google.gson.annotations.SerializedName;

public class RegisterPayload {
    @SerializedName("nombre")
    String name;
    @SerializedName("apellido")
    String lastName;
    @SerializedName("cedula")
    String personalDocument;
    @SerializedName("correo")
    String email;
    @SerializedName("contrasena")
    String password;

    public RegisterPayload(String name, String lastName, String personalDocument, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.personalDocument = personalDocument;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalDocument() {
        return personalDocument;
    }

    public void setPersonalDocument(String personalDocument) {
        this.personalDocument = personalDocument;
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
}
