package com.example.pf_hpa4.interfaces;

import com.example.pf_hpa4.request.Estudiante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApi {
    @GET("estudiantes/grupos/{id}")
    Call<List<Estudiante>> getStudentsByGroup(@Path("id") int id);

    @GET("estudiantes/all")
    Call<List<Estudiante>> getStudents();
}
