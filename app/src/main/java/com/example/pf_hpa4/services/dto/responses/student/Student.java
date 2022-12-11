package com.example.pf_hpa4.services.dto.responses.student;

import com.example.pf_hpa4.services.dto.responses.handler.IConvertFromJSON;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Student implements IConvertFromJSON<Student> {
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

    public Student(){

    }
    public Student(Integer studentId, String name, String lastName, String personalDocument, String email, String photo) {
        this.studentId = studentId;
        this.name = name;
        this.lastName = lastName;
        this.personalDocument = personalDocument;
        this.email = email;
        this.photo = photo;
    }

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
                "\"studentId\":" + studentId +
                ", \"name\":'" + name + '\'' +
                ", \"lastName\":'" + lastName + '\'' +
                ", \"personalDocument\":'" + personalDocument + '\'' +
                ", \"email\":'" + email + '\'' +
                ", \"photo\":'" + photo + '\'' +
                '}';
    }

    @Override
    public Student GetFromJSON(String json) {
        JSONObject jsonHandler;
        Student student = null;
        try {
            jsonHandler = new JSONObject(json);
            student = new Student(
                    jsonHandler.getInt("studentId"),
                    jsonHandler.getString("name"),
                    jsonHandler.getString("lastName"),
                    jsonHandler.getString("personalDocument"),
                    jsonHandler.getString("email"),
                    jsonHandler.getString("photo")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return student;
    }
}
