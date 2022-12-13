package com.example.pf_hpa4.services.interfaces;

import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGroupService {

    @GET("grupos/estudiante/{eid}")
    Call<List<Group>> getGroupsByStudentId(@Path("eid") int id);
    @GET("grupos/profesor/{pid}")
    Call<List<Group>> getGroupsByProfesorId(@Path("pid") int pid);
    @GET("grupos/all")
    Call<List<Group>> getGroupsAll();
}
