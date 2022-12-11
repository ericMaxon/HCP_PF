package com.example.pf_hpa4.services.dto.responses.student;

import com.example.pf_hpa4.services.dto.responses.handler.IConvertFromJSON;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Group implements IConvertFromJSON<Group> {
    @SerializedName("id")
    Integer groupId;
    @SerializedName("grupo")
    String groupName;
    @SerializedName("asignatura")
    String subject;
    @SerializedName("codigo_asignatura")
    String subjectCode;
    @SerializedName("periodo")
    String semester;
    @SerializedName("docente_id")
    Integer teacherId;

    public Group() {

    }

    public Group(Integer groupId, String groupName, String subject, String subjectCode, String semester, Integer teacherId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.subject = subject;
        this.subjectCode = subjectCode;
        this.semester = semester;
        this.teacherId = teacherId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"groupId\":" + groupId +
                ", \"groupName\":'" + groupName + '\'' +
                ", \"subject\":'" + subject + '\'' +
                ", \"subjectCode\":'" + subjectCode + '\'' +
                ", \"semester\":'" + semester + '\'' +
                ", \"teacherId\":" + teacherId +
                '}';
    }

    public Group GetFromJSON(String json) {
        JSONObject jsonHandler;
        Group group = null;
        try {
            jsonHandler = new JSONObject(json);
            group = new Group(
                    jsonHandler.getInt("groupId"),
                    jsonHandler.getString("groupName"),
                    jsonHandler.getString("subject"),
                    jsonHandler.getString("subjectCode"),
                    jsonHandler.getString("semester"),
                    jsonHandler.getInt("teacherId")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return group;
    }
}
