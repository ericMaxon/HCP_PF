package com.example.pf_hpa4.services.dto.responses.student;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    Integer studentId;
    @SerializedName("nombre")
    String name;
    @SerializedName("apellido")
    String lastName;
    @SerializedName("cedula")
    String personalDocument;
    @SerializedName("correo")
    String email;
    @SerializedName("foto_url")
    String photo;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + studentId +
                ", \"nombre\":'" + name + '\'' +
                ", \"apellido\":'" + lastName + '\'' +
                ", \"cedula\":'" + personalDocument + '\'' +
                ", \"correo\":'" + email + '\'' +
                ", \"foto\":'" + photo + '\'' +
                '}';
    }
}
