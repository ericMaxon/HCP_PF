package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.Adapters.ListViewAdapter_Attendance;
import com.example.pf_hpa4.LoginActivity;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.StudentService;
import com.example.pf_hpa4.services.dto.responses.auth.User;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;
import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.example.pf_hpa4.services.dto.responses.student.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceListActivity extends AppCompatActivity {

    ListView Listado_Asistencias;

    TextView txtSubtitulo, txtTitulo, txtCedula, Asistencias, Tardanzas, Ausencias;

    Group selectedGroup = new Group();
    Student selectedStudent = new Student();

    StudentService studentService = new StudentService();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencias_list);

        Intent i = getIntent();
        if (i != null){
            MapInfoFromIntent(i);
        }

        Inicializar_controles();
        progressDialog.setMessage("Obteniendo el listado de las asistencias");
        progressDialog.show();
        studentService.getStudentsAttendeByGroup(selectedGroup.getGroupId(), selectedStudent.getStudentId())
                .enqueue(new Callback<List<Attendance>>() {
                    @Override
                    public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                        progressDialog.dismiss();
                        if (!response.isSuccessful()) {
                            Toast.makeText(AttendanceListActivity.this, "No pudimos obtener la informacion de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        List<Attendance> attendanceList = response.body();
                        if (attendanceList == null) {
                            Toast.makeText(AttendanceListActivity.this, "No pudimos registrar el listado de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        LoadListView_Attendance(attendanceList);

                        int as = 0;
                        int tar = 0;
                        int aus = 0;

                        for (int i = 0; i < attendanceList.size(); i++){
                            int check = (attendanceList.get(i).getSubjectStatusId());
                            if (check == 1 || check == 4){
                                as++;
                            } else if (check == 2){
                                tar++;
                            }
                            else if (check == 3){
                                aus++;
                            }
                        }

                        Asistencias.setText("Asistencias\n" + as);
                        Tardanzas.setText("Tardanzas\n" + tar);
                        Ausencias.setText("Ausencias\n" + aus);

                    }

                    @Override
                    public void onFailure(Call<List<Attendance>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(AttendanceListActivity.this, "Error SLA > " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void Inicializar_controles(){
        progressDialog = new ProgressDialog(this);
        Listado_Asistencias = findViewById(R.id.asistencia_lvGrupos);

        txtTitulo = findViewById(R.id.txt_asistencia_titulo);
        txtTitulo.setText(selectedStudent.getName().trim() + " " + selectedStudent.getLastName().trim());

        txtCedula = findViewById(R.id.txt_asistencia_cedula);
        txtCedula.setText(selectedStudent.getPersonalDocument());

        txtSubtitulo = findViewById(R.id.txt_asistencia_subtitulo);
        txtSubtitulo.setText(selectedGroup.getSubject());

        Asistencias = findViewById(R.id.txt_asistencia_presente);
        Tardanzas = findViewById(R.id.txt_asistencia_tardanza);
        Ausencias = findViewById(R.id.txt_asistencia_ausencia);
    }

    private void MapInfoFromIntent(Intent i) {
        selectedGroup = selectedGroup.GetFromJSON(i.getStringExtra("json_SelectedGroup"));
        selectedStudent = selectedStudent.GetFromJSON(i.getStringExtra("json_SelectedStudent"));
    }

    ////////////// Cargar ListView con las Asistencias
    private void LoadListView_Attendance(List<Attendance> attendanceList)
    {

        ListViewAdapter_Attendance adapter = new ListViewAdapter_Attendance(this, attendanceList);

        Listado_Asistencias.setAdapter(adapter);
    }

    public void returnStudents(View view){
        Intent i = new Intent(AttendanceListActivity.this, StudentListActivity.class)
                .putExtra("json_SelectedGroup", selectedGroup.toString());
        startActivity(i);
    }

}