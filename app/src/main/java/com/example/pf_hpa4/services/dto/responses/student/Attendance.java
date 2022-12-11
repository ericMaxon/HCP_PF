package com.example.pf_hpa4.services.dto.responses.student;

import com.example.pf_hpa4.services.dto.responses.handler.IConvertFromJSON;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Attendance implements IConvertFromJSON<Attendance> {
    @SerializedName("id")
    Integer attendanceId;
    @SerializedName("fecha")
    String date;
    @SerializedName("hora")
    String hour;
    @SerializedName("estudiante_id")
    Integer studentId;
    @SerializedName("grupo_asignatura_id")
    Integer groupSubjectId;
    @SerializedName("estado_asistencia_id")
    Integer subjectStatusId;

    public Attendance() {
    }

    public Attendance(Integer attendanceId, String date, String hour, Integer studentId, Integer groupSubjectId,
                      Integer subjectStatusId) {
        this.attendanceId = attendanceId;
        this.date = date;
        this.hour = hour;
        this.studentId = studentId;
        this.groupSubjectId = groupSubjectId;
        this.subjectStatusId = subjectStatusId;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
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

    @Override
    public String toString() {
        return "{" +
                "\"attendanceId\":" + attendanceId +
                ", \"date\":" + "\"" + date + "\"" +
                ", \"hour\":" + "\"" + hour + "\"" +
                ", \"studentId\":" + studentId +
                ", \"groupSubjectId\":" + groupSubjectId +
                ", \"subjectStatusId\":" + subjectStatusId +
                '}';
    }

    @Override
    public Attendance GetFromJSON(String json) {
        JSONObject jsonHandler;
        Attendance attendance = null;

        try {
            jsonHandler = new JSONObject(json);
            attendance = new Attendance(
                    jsonHandler.getInt("attendanceId"), jsonHandler.getString("date"),
                    jsonHandler.getString("hour"), jsonHandler.getInt("studentId"),
                    jsonHandler.getInt("groupSubjectId"),jsonHandler.getInt("subjectStatusId")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attendance;
    }
}
