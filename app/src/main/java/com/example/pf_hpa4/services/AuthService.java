package com.example.pf_hpa4.services;

import com.example.pf_hpa4.services.dto.request.auth.AuthPayload;
import com.example.pf_hpa4.services.dto.request.auth.RegisterPayload;
import com.example.pf_hpa4.services.dto.responses.auth.Login;
import com.example.pf_hpa4.services.interfaces.IAuthService;

import retrofit2.Call;
import retrofit2.Response;

public class AuthService extends AbstractService<IAuthService> implements IAuthService {
    @Override
    public Call<Login> postLogin(AuthPayload payload) {
        return this.getApiService().postLogin(payload);
    }

    @Override
    public Call<Response<Void>> postRegister(RegisterPayload payload) {
        return this.getApiService().postRegister(payload);
    }
}
