package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.pf_hpa4.Adapters.ListViewAdapter_Group;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Students;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.services.StudentService;
import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.example.pf_hpa4.services.dto.responses.student.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    ListView Listado_Estudiantes;

    Group selectedGroup = new Group();
    StudentService studentService = new StudentService();

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes_list);

        Intent i = getIntent();

        if (i != null) {
            MapInfoFromIntent(i);
        }
        InitControllers();

        progressDialog.setMessage("Obteniendo el listado de estudiantes");
        progressDialog.show();

        studentService.getStudentsByGroup(selectedGroup.getGroupId())
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        progressDialog.dismiss();
                        if (!response.isSuccessful()) {
                            Toast.makeText(StudentListActivity.this, "No pudimos obtener la informacion de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        List<Student> studentList = response.body();
                        if (studentList == null) {
                            Toast.makeText(StudentListActivity.this, "No pudimos registrar el listado de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        LoadListView_Students(studentList);

                        EditText busqueda_g = findViewById(R.id.edt_estudiante_filtro);
                        busqueda_g.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                List<Student> studentList2 = new ArrayList<Student>();

                                for (int x = 0; x < studentList.size(); x++) {
                                    String check = (studentList.get(x).getPersonalDocument() + " " +
                                            studentList.get(x).getName() + " " +
                                            studentList.get(x).getLastName()).toLowerCase();
                                    if (check.contains(busqueda_g.getText().toString().toLowerCase())) {
                                        studentList2.add(new Student(studentList.get(x).getStudentId(),
                                                studentList.get(x).getName(),
                                                studentList.get(x).getLastName(),
                                                studentList.get(x).getPersonalDocument(),
                                                studentList.get(x).getEmail(),
                                                studentList.get(x).getPhoto()));
                                    }
                                }
                                ListViewAdapter_Students adapter = new ListViewAdapter_Students(StudentListActivity.this, studentList2);
                                Listado_Estudiantes.setAdapter(adapter);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(StudentListActivity.this, "Error SLA > " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void InitControllers() {
        progressDialog = new ProgressDialog(this);
        Listado_Estudiantes = (ListView) findViewById(R.id.estudiantes_lvGrupos);
        Toolbar txtAsignatura = (Toolbar) findViewById(R.id.tlb_estudiante_titulo);
        txtAsignatura.setTitle("[" + selectedGroup.getGroupName() + "] " + selectedGroup.getSubject());

        Listado_Estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Student sStudent = ((Student) a.getItemAtPosition(position));

                Intent i = new Intent(StudentListActivity.this, AttendanceListActivity.class);
                i.putExtra("json_SelectedStudent", sStudent.toString());
                i.putExtra("json_SelectedGroup", selectedGroup.toString());

                startActivity(i);
            }
        });


        txtAsignatura.inflateMenu(R.menu.menu);
        txtAsignatura.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ///////////// Enviar id del grupo al activity pasar lista
                if (item.getItemId() == R.id.menu_pasar_asistencia) {
                    Intent i = new Intent(StudentListActivity.this, PassListActivity.class);
                    i.putExtra("json_SelectedGroup", selectedGroup.toString());
                    startActivity(i);
                } else if (item.getItemId() == R.id.menu_regresar){
                    Intent i = new Intent(StudentListActivity.this, GroupListActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });

    }

    private void MapInfoFromIntent(Intent i) {
        selectedGroup = selectedGroup.GetFromJSON(i.getStringExtra("json_SelectedGroup"));
    }

    ////////////// Cargar ListView con la lista de Estudiantes
    private void LoadListView_Students(List<Student> studentList) {
        ListViewAdapter_Students adapter = new ListViewAdapter_Students(this, studentList);
        Listado_Estudiantes.setAdapter(adapter);
    }
}