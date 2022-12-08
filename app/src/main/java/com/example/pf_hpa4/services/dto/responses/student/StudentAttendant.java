package com.example.pf_hpa4.services.dto.responses.student;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StudentAttendant {
    @SerializedName("id")
    Integer id;
    @SerializedName("fecha")
    Date date;
    @SerializedName("hora")
    Date hour;
    @SerializedName("estudiante_id")
    Integer studentId;
    @SerializedName("grupo_asignatura_id")
    Integer groupSubjectId;
    @SerializedName("estado_asistencia_id")
    Integer subjectStatusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getGroupSubjectId() {
        return groupSubjectId;
    }

    public void setGroupSubjectId(Integer groupSubjectId) {
        this.groupSubjectId = groupSubjectId;
    }

    public Integer getSubjectStatusId() {
        return subjectStatusId;
    }

    public void setSubjectStatusId(Integer subjectStatusId) {
        this.subjectStatusId = subjectStatusId;
    }
}
