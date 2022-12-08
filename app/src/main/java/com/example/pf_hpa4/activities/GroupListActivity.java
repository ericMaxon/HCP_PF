package com.example.pf_hpa4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.pf_hpa4.R;
import com.example.pf_hpa4.services.StudentGroupService;
import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity {
    int teacherId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        setContentView(R.layout.activity_group_list);
    }

    private void getIntentData(@NonNull Intent intent) {
        teacherId = intent.getIntExtra("teacherId", -1);
        if(teacherId != -1){
            StudentGroupService studentGroupService = new StudentGroupService();
            studentGroupService.getGroupsByProfesorId(teacherId)
                    .enqueue(new Callback<List<Group>>() {
                        @Override
                        public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                            return;
                        }

                        @Override
                        public void onFailure(Call<List<Group>> call, Throwable t) {
                            return;
                        }
                    });
        }
    }
}