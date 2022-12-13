package com.example.pf_hpa4.services.dto.request.admin;

import com.google.gson.annotations.SerializedName;

public class EnrollPayload {
    @SerializedName("estudiante_id")
    Integer studentId;
    @SerializedName("grupo_asignatura_id")
    Integer groupId;

    public EnrollPayload(Integer studentId, Integer groupId) {
        this.studentId = studentId;
        this.groupId = groupId;
    }
}
