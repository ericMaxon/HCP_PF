package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.Adapters.ListViewAdapter_PassList;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Students;
import com.example.pf_hpa4.NFC.NFCManager;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.ApiConstants;
import com.example.pf_hpa4.services.StudentService;
import com.example.pf_hpa4.services.dto.responses.ErrorResponse;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;
import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.example.pf_hpa4.services.dto.responses.student.Student;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassListActivity extends AppCompatActivity {

    NFCManager nfcManager;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

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

        nfcAdapter = NfcAdapter.getDefaultAdapter(PassListActivity.this);

        try {
            pendingIntent = PendingIntent.getActivity(PassListActivity.this, 0,
                    new Intent(PassListActivity.this, PassListActivity.this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }catch (Exception e){
            AlertDialog.Builder notNFC = new AlertDialog.Builder(PassListActivity.this);
            notNFC.setTitle("Su dispositivo no cuenta con NFC");
            notNFC.setMessage("Use el sistema manual para registrar la asistencia");
            notNFC.show();

            TextView sub = (TextView) findViewById(R.id.txt_pass_tagTitulo);
            sub.setText("No cuenta con NFC para registrar la asistencia por Tag NFC");

            ImageView img = findViewById(R.id.img_pass_nfc);
            img.setImageResource(R.drawable.user_dp);
        }

        progressDialog.setTitle("Obteniendo el listado de estudiantes...");
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

                        EditText busqueda_g = findViewById(R.id.edt_pass_filtro);
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
                                ListViewAdapter_Students adapter = new ListViewAdapter_Students(PassListActivity.this, studentList2);
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
                        cargar_asistencia(sStudent.getStudentId(), (sStudent.getName().trim() + " " + sStudent.getLastName().trim()), sStudent.getPersonalDocument());
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

    private void handleTagListener() {
        nfcManager.setOnTagReadListener(new NFCManager.TagReadListener() {
            @Override
            public void onTagRead(String[] tagRead) {
                String nombre = tagRead[0];
                String cedula = tagRead[1];
                String idUser = tagRead[2];

                Boolean verified = false;
                for (int i = 0; i < studentList_temp.size(); i++) {
                    String check = (studentList_temp.get(i).getStudentId()).toString();
                    if (check.equals(idUser)) {
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
        progressDialog.setTitle("Marcando asistencia...");

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
                .enqueue(new Callback<Attendance>() {
                    @Override
                    public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                        progressDialog.dismiss();
                        if (!response.isSuccessful()) {
                            ErrorResponse errorResponse = ErrorResponse.GetResponseError(response.errorBody());
                            if (errorResponse != null)
                                Toast.makeText(PassListActivity.this, errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(PassListActivity.this, "No se puedo registrar la asistencia", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String Asis_T = ApiConstants.AttendeeStatus.getStatus(Asis);
                        Toast.makeText(PassListActivity.this, "Se registro como " + Asis_T + " para:  " + name + " | " + ced, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Attendance> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(PassListActivity.this, "Hubo un error al registrar la asistencia", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void finalizar_asistencia(View view) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(PassListActivity.this);
        dialogo1.setTitle("Cerrar listado de asistencia");
        dialogo1.setMessage("Al cerrar el listado todos los estudiantes que no se registraron seran marcados con ausencia. ¿Desea continuar?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

            Date currentDate = new Date();

            public void onClick(DialogInterface dialogo1, int id) {
                for (int i = 0; i < studentList_temp.size(); i++) {
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
                            .enqueue(new Callback<Attendance>() {
                                @Override
                                public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                                    if (!response.isSuccessful()) {
                                        ErrorResponse errorResponse = ErrorResponse.GetResponseError(response.errorBody());
                                        if (errorResponse != null){
                                            if (!errorResponse.getMessage().contains("ya existe registro")){
                                                Toast.makeText(PassListActivity.this, errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                                Toast.makeText(PassListActivity.this, "No se puedo registrar la asistencia", Toast.LENGTH_LONG).show();
                                            }

                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Attendance> call, Throwable t) {
                                    Toast.makeText(PassListActivity.this, "Hubo un error al registrar la asistencia", Toast.LENGTH_SHORT).show();
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