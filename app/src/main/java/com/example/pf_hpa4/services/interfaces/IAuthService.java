package com.example.pf_hpa4.services.interfaces;

import com.example.pf_hpa4.services.dto.request.auth.AuthPayload;
import com.example.pf_hpa4.services.dto.request.auth.RegisterPayload;
import com.example.pf_hpa4.services.dto.responses.auth.Login;
import com.example.pf_hpa4.services.dto.responses.auth.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthService {
    @POST("login")
    Call<Login> postLogin(@Body AuthPayload payload);
    @POST("registrar")
    @Headers({"Accept: application/json"})
    Call<Response<Void>> postRegister(@Body RegisterPayload payload);
}
