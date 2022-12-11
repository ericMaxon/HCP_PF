package com.example.pf_hpa4.services.dto.responses.auth;

import androidx.annotation.Nullable;

import com.example.pf_hpa4.services.dto.responses.handler.IConvertFromJSON;
import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class User implements IConvertFromJSON<User> {

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
    @SerializedName("email_verified_at")
    @Nullable
    Calendar emailVerifiedDate;
    @SerializedName("docente_id")
    @Nullable
    Integer teacherId;

    public User() {
    }

    public User(
            Integer userId, String name, String email, String personalDocument,
            Integer role, Integer active, Integer teacherId, String lastName, Calendar emailVerifiedDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.personalDocument = personalDocument;
        this.role = role;
        this.active = active;
        this.teacherId = teacherId;
        this.lastName = lastName;
        this.emailVerifiedDate = emailVerifiedDate;
    }

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

    @Nullable
    public Calendar getEmailVerifiedDate() {
        return emailVerifiedDate;
    }

    public void setEmailVerifiedDate(@Nullable Calendar emailVerifiedDate) {
        this.emailVerifiedDate = emailVerifiedDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + userId +
                ", \"name\":'" + name + '\'' +
                ", \"lastName\":'" + lastName + '\'' +
                ", \"email\":'" + email + '\'' +
                ", \"personalDocument\":'" + personalDocument + '\'' +
                ", \"role\":" + role +
                ", \"active\":" + active +
                ", \"teacherId\":" + teacherId +
                ", \"emailVerifiedDate\": " + (emailVerifiedDate != null ? emailVerifiedDate.getTime().getTime() : null) +
                '}';
    }

    @Override
    public User GetFromJSON(String json) {
        JSONObject jsonHandler;
        User user = null;
        try {
            jsonHandler = new JSONObject(json);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(jsonHandler.getLong("teacherId")));
            user = new User(
                    jsonHandler.getInt("userId"),
                    jsonHandler.getString("name"),
                    jsonHandler.getString("email"),
                    jsonHandler.getString("personalDocument"),
                    jsonHandler.getInt("role"),
                    jsonHandler.getInt("active"),
                    jsonHandler.getInt("teacherId"),
                    jsonHandler.getString("lastName"),
                    cal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
