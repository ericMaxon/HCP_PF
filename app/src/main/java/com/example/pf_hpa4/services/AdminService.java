package com.example.pf_hpa4.services;

import com.example.pf_hpa4.services.dto.request.admin.EnrollPayload;
import com.example.pf_hpa4.services.dto.responses.admin.EnrollResponse;
import com.example.pf_hpa4.services.interfaces.IAdminService;

import retrofit2.Call;

public class AdminService extends AbstractService<IAdminService> implements IAdminService {
    @Override
    public Call<EnrollResponse> postEnrollStudent(EnrollPayload payload) {
        return this.getApiService().postEnrollStudent(payload);
    }
}
