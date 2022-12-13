package com.example.pf_hpa4.services.dto.request.admin;

import com.google.gson.annotations.SerializedName;

public class EnrollResponse {
    @SerializedName("titulo")
    String title;
    @SerializedName("mensaje")
    String message;
    @SerializedName("status")
    Integer status;

    public EnrollResponse(String title, String message, Integer status) {
        this.title = title;
        this.message = message;
        this.status = status;
    }
}
