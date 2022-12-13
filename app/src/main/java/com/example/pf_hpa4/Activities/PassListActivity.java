package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.pf_hpa4.Adapters.ListViewAdapter_PassList;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Students;
import com.example.pf_hpa4.NFC.NFC_Actitvity;
import com.example.pf_hpa4.NFC.util.NFCManager;
import com.example.pf_hpa4.NFC.util.WriteTagHelper;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.ApiConstants;
import com.example.pf_hpa4.services.StudentService;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;
import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.example.pf_hpa4.services.dto.responses.student.Student;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassListActivity extends AppCompatActivity {

    NFCManager nfcManager;
    ListView Listado_Estudiantes;

    Group selectedGroup = new Group();
    StudentService studentService = new StudentService();

    ProgressDialog progressDialog;

    ListViewAdapter_PassList adapter_temp;
    List<Student> studentList_temp;

    LocalDateTime Starttime = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);

        Intent i = getIntent();

        if (i != null) {
            MapInfoFromIntent(i);
        }
        InitControllers();

        progressDialog.setMessage("Obteniendo el listado de estudiantes...");
        progressDialog.show();

        nfcManager = new NFCManager(this);
        nfcManager.onActivityCreate();

        studentService.getStudentsByGroup(selectedGroup.getGroupId())
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        progressDialog.dismiss();
                        if (!response.isSuccessful()) {
                            Toast.makeText(PassListActivity.this, "No pudimos obtener la informacion de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        List<Student> studentList = response.body();
                        if (studentList == null) {
                            Toast.makeText(PassListActivity.this, "No pudimos registrar el listado de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        LoadListView_Students(studentList);
                        studentList_temp = studentList;
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PassListActivity.this, "Error SLA > " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        nfcManager.onActivityResume();
    }

    @Override
    protected void onPause() {
        nfcManager.onActivityPause();
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        nfcManager.onActivityNewIntent(intent);
    }

    private void InitControllers() {
        progressDialog = new ProgressDialog(this);
        Listado_Estudiantes = (ListView) findViewById(R.id.ls_pass_estudiantes);
        TextView txtAsignatura = (TextView) findViewById(R.id.txt_pass_titulo);
        txtAsignatura.setText("[" + selectedGroup.getGroupName() + "] " + selectedGroup.getSubject());
    }

    private void MapInfoFromIntent(Intent i) {
        selectedGroup = selectedGroup.GetFromJSON(i.getStringExtra("json_SelectedGroup"));
    }

    private void LoadListView_Students(List<Student> studentList) {
        ListViewAdapter_PassList adapter = new ListViewAdapter_PassList(this, studentList);
        Listado_Estudiantes.setAdapter(adapter);
        adapter_temp = adapter;
        Listado_Estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Student sStudent = ((Student) a.getItemAtPosition(position));

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(PassListActivity.this);
                dialogo1.setTitle("Marcar asistencia para");
                dialogo1.setMessage(sStudent.getName() + " " + sStudent.getLastName());
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        studentList_temp.remove(position);
                        adapter_temp.notifyDataSetChanged();
                        cargar_asistencia(sStudent.getStudentId(), (sStudent.getName() + " " + sStudent.getLastName()), sStudent.getPersonalDocument());
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });

                dialogo1.show();
            }
        });
        handleTagListener();
    }
    private void handleTagListener(){
        nfcManager.setOnTagReadListener(new NFCManager.TagReadListener() {
            @Override
            public void onTagRead(String[] tagRead) {
                String nombre = tagRead[0];
                String cedula = tagRead[1];
                String idUser = tagRead[3];

                Boolean verified = false;
                for (int i = 0; i < studentList_temp.size(); i++){
                    String check = (studentList_temp.get(i).getStudentId()).toString();
                    if (check.equals(idUser)){
                        studentList_temp.remove(i);
                        adapter_temp.notifyDataSetChanged();
                        verified = true;
                        cargar_asistencia(Integer.parseInt(idUser), nombre, cedula);
                    }
                }
                if (!verified) {
                    Toast.makeText(PassListActivity.this, "El estudiante ya fue listado o no esta matriculado en el grupo", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void cargar_asistencia(Integer idUser, String name, String ced){
        progressDialog.setMessage("Marcando asistencia...");
        progressDialog.show();

        Date currentDate = new Date();
        int Asis = ApiConstants.AttendeeStatus.getStatusId(LocalDateTime.now(), Starttime);

        Attendance payload = new Attendance(
                null,//No es un campo necesario
                DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es-ES")).format(currentDate),//Da el formato YYYY-MM-DD
                DateFormat.getTimeInstance(DateFormat.SHORT).format(currentDate),//Da el formato HH:MM A
                idUser,
                selectedGroup.getGroupId(),
                Asis
        );
        studentService.postStudentsSubject(payload)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        progressDialog.dismiss();
                        String Asis_T = ApiConstants.AttendeeStatus.getStatus(Asis);
                        Toast.makeText(PassListActivity.this, "Se registro como " + Asis_T + " para:  " + name + " | " + ced, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PassListActivity.this, "No pudimos registrar la asistencia", Toast.LENGTH_SHORT).show();
                    }
                });



    }

    public void finalizar_asistencia(View view){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(PassListActivity.this);
        dialogo1.setTitle("Cerrar listado de asistencia");
        dialogo1.setMessage("Al cerrar el listado todos los estudiantes que no se registraron seran marcados con ausencia. ¿Desea continuar?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

            Date currentDate = new Date();

            public void onClick(DialogInterface dialogo1, int id) {
                for (int i = 0; i < studentList_temp.size(); i++){
                    String idStudent = (studentList_temp.get(i).getStudentId()).toString();

                    int posActual = i;

                    Attendance payload = new Attendance(
                            null,//No es un campo necesario
                            DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es-ES")).format(currentDate),//Da el formato YYYY-MM-DD
                            DateFormat.getTimeInstance(DateFormat.SHORT).format(currentDate),//Da el formato HH:MM A
                            Integer.parseInt(idStudent),
                            selectedGroup.getGroupId(),
                            ApiConstants.EAttendeeStatus.absentId
                    );
                    studentService.postStudentsSubject(payload)
                            .enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    //////No se requiere ninguna accion
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PassListActivity.this, "No pudimos registrar la asistencia", Toast.LENGTH_SHORT).show();
                                }
                            });



                }
                progressDialog.dismiss();
                Toast.makeText(PassListActivity.this, "Asistencia finalizada", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PassListActivity.this, StudentListActivity.class)
                        .putExtra("json_SelectedGroup", selectedGroup.toString());
                startActivity(i);
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });

        dialogo1.show();
    }
}