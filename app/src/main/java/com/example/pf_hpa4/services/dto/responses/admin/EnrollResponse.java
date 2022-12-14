package com.example.pf_hpa4.services.dto.responses.admin;

import com.google.gson.annotations.SerializedName;

public class EnrollResponse {
    @SerializedName("estudiante_id")
    Integer studentId;
    @SerializedName("grupo_asignatura_id")
    Integer groupId;
    @SerializedName("id")
    Integer enrollId;

    public EnrollResponse(Integer studentId, Integer groupId, Integer enrollId) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.enrollId = enrollId;
    }
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(Integer enrollId) {
        this.enrollId = enrollId;
    }
}
