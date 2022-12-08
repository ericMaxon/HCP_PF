package com.example.pf_hpa4.services;

import com.example.pf_hpa4.services.dto.responses.student.Student;
import com.example.pf_hpa4.services.dto.responses.student.StudentAttendant;
import com.example.pf_hpa4.services.interfaces.IEstudianteService;

import java.util.List;

import retrofit2.Call;

public class EstudiantesService extends AbstractService <IEstudianteService> implements IEstudianteService {

    @Override
    public Call<List<Student>> getStudentsByGroup(int id) {
        return this.getApiService().getStudentsByGroup(id);
    }

    @Override
    public Call<List<StudentAttendant>> getStudentsAttendeByGroup(int gaId) {
        return this.getApiService().getStudentsAttendeByGroup(gaId);
    }

    @Override
    public Call<List<StudentAttendant>> getStudentsAttendeByGroup(int gaId, int eId) {
        return this.getApiService().getStudentsAttendeByGroup(gaId, eId);
    }

    @Override
    public Call postStudentsSubject(StudentAttendant body) {
        return this.getApiService().postStudentsSubject(body);
    }
}
