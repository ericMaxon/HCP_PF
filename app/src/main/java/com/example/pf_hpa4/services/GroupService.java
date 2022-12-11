package com.example.pf_hpa4.services;

import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.example.pf_hpa4.services.interfaces.IGroupService;

import java.util.List;

import retrofit2.Call;

public class GroupService extends AbstractService<IGroupService> implements IGroupService {

    @Override
    public Call<List<Group>> getGroupsByStudentId(int id) {
        return this.getApiService().getGroupsByStudentId(id);
    }

    @Override
    public Call<List<Group>> getGroupsByProfesorId(int pid) {
        return this.getApiService().getGroupsByProfesorId(pid);
    }
}
