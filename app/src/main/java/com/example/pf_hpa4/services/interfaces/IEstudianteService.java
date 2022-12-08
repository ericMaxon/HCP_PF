package com.example.pf_hpa4.services.interfaces;

import com.example.pf_hpa4.services.dto.responses.student.Student;
import com.example.pf_hpa4.services.dto.responses.student.StudentAttendant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IEstudianteService {

    @GET("estudiantes/grupos/{id}")
    Call<List<Student>> getStudentsByGroup(@Path("id") int id);

    @GET("estudiantes/asistencia/{gaId}")
    Call<List<StudentAttendant>> getStudentsAttendeByGroup(@Path("gaId") int gaId);

    @GET("estudiantes/asistencia/{gaId}/{eId}")
    Call<List<StudentAttendant>> getStudentsAttendeByGroup(@Path("gaId") int gaId, @Path("eId") int eId);

    @POST("estudiantes/asistencia")
    Call<Integer> postStudentsSubject(@Body StudentAttendant body);
}
