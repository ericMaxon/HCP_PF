package com.example.pf_hpa4.services;

import com.example.pf_hpa4.services.dto.responses.student.Student;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;
import com.example.pf_hpa4.services.interfaces.IStudentService;

import java.util.List;

import retrofit2.Call;

public class StudentService extends AbstractService <IStudentService> implements IStudentService {

    @Override
    public Call<List<Student>> getStudentsByGroup(int id) {
        return this.getApiService().getStudentsByGroup(id);
    }

    @Override
    public Call<List<Attendance>> getStudentsAttendeByGroup(int gaId) {
        return this.getApiService().getStudentsAttendeByGroup(gaId);
    }

    @Override
    public Call<List<Attendance>> getStudentsAttendeByGroup(int gaId, int eId) {
        return this.getApiService().getStudentsAttendeByGroup(gaId, eId);
    }

    @Override
    public Call postStudentsSubject(Attendance body) {
        return this.getApiService().postStudentsSubject(body);
    }
}
