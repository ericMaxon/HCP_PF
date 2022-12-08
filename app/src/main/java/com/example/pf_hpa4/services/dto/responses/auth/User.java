package com.example.pf_hpa4.services.dto.responses.auth;

import com.google.gson.annotations.SerializedName;

public class User {
    public User(
            Integer userId, String name, String email, String personalDocument,
            Integer role, Integer active, Integer teacherId, String lastName) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.personalDocument = personalDocument;
        this.role = role;
        this.active = active;
        this.teacherId = teacherId;
        this.lastName = lastName;
    }

    @SerializedName("id")
    Integer userId;
    @SerializedName("nombres")
    String name;
    @SerializedName("apellidos")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("cedula")
    String personalDocument;
    @SerializedName("role")
    Integer role;
    @SerializedName("active")
    Integer active;
    @SerializedName("docente_id")
    Integer teacherId;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalDocument() {
        return personalDocument;
    }

    public void setPersonalDocument(String personalDocument) {
        this.personalDocument = personalDocument;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
