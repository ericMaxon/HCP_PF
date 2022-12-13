package com.example.pf_hpa4.services.interfaces;

import com.example.pf_hpa4.services.dto.request.admin.EnrollPayload;
import com.example.pf_hpa4.services.dto.request.admin.EnrollResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAdminService {
    @POST("matricular")
    Call<EnrollResponse> postEnrollStudent(@Body EnrollPayload payload);
}
