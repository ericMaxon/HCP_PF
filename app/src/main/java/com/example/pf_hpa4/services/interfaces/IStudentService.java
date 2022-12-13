package com.example.pf_hpa4.services.interfaces;

import com.example.pf_hpa4.services.dto.responses.student.Student;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IStudentService {

    @GET("estudiantes/grupos/{gaId}")
    Call<List<Student>> getStudentsByGroup(@Path("gaId") int gaId);

    @GET("estudiantes/all")
    Call<List<Student>> getStudentsAll();

    @GET("estudiantes/asistencia/{gaId}")
    Call<List<Attendance>> getStudentsAttendeByGroup(@Path("gaId") int gaId);

    @GET("estudiante/asistencia/{gaId}/{eId}")
    Call<List<Attendance>> getStudentsAttendeByGroup(@Path("gaId") int gaId, @Path("eId") int eId);

    @POST("estudiante/asistencia")
    Call<Integer> postStudentsSubject(@Body Attendance body);
}
