package com.example.pf_hpa4.services.dto.responses;

import android.content.Context;
import android.widget.Toast;

import com.example.pf_hpa4.Activities.PassListActivity;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class ErrorResponse {
    @SerializedName("titulo")
    String title;
    @SerializedName("mensaje")
    String message;
    @SerializedName("status")
    Integer status;

    public ErrorResponse(String title, String message, Integer status) {
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static ErrorResponse GetResponseError(ResponseBody body) {
        if (body == null)
            return null;

        Gson gson = new Gson();
        ErrorResponse errorResponse = null;
        try {
            errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorResponse;
    }
}
