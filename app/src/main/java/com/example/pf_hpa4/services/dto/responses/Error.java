package com.example.pf_hpa4.services.dto.responses;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("title")
    String title;
    @SerializedName("mensaje")
    String message;
}
