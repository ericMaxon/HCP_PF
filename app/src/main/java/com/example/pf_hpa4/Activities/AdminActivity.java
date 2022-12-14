package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.Adapters.ListViewAdapter_Group;
import com.example.pf_hpa4.Adapters.ListViewAdapter_PassList;
import com.example.pf_hpa4.NFC.NFCManager;
import com.example.pf_hpa4.NFC.WriteTagHelper;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.AdminService;
import com.example.pf_hpa4.services.GroupService;
import com.example.pf_hpa4.services.StudentService;
import com.example.pf_hpa4.services.dto.request.admin.EnrollPayload;
import com.example.pf_hpa4.services.dto.responses.ErrorResponse;
import com.example.pf_hpa4.services.dto.responses.admin.EnrollResponse;
import com.example.pf_hpa4.services.dto.responses.auth.User;
import com.example.pf_hpa4.services.dto.responses.student.Group;
import com.example.pf_hpa4.services.dto.responses.student.Student;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    TextView txt_perfil_nombre, txt_perfil_correo;
    CircleImageView img_perfil_imagen;
    EditText busqueda;
    ListView estudiantes;

    WriteTagHelper writeHelper;
    NFCManager nfcManager;

    Boolean WriteActive = false;

    User user;
    StudentService studentService;
    AdminService adminService;
    ListViewAdapter_PassList adapter;
    List<Student> studentList;

    private PopupMenu popup;

    ProgressDialog progressDialog;

    public AdminActivity() {
        studentService = new StudentService();
        user = new User();
        adminService = new AdminService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        nfcManager = new NFCManager(this);
        nfcManager.onActivityCreate();

        writeHelper = new WriteTagHelper(AdminActivity.this, nfcManager);
        nfcManager.setOnTagWriteErrorListener(writeHelper);
        nfcManager.setOnTagWriteListener(writeHelper);

        SharedPreferences userSP_JSON = getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
        user = user.GetFromJSON(userSP_JSON.getString("jsonUser", ""));
        Inicializar_controles();

        progressDialog.setMessage("Obteniendo el listado de estudiantes");
        progressDialog.show();

        studentService.getStudentsAll()
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        progressDialog.dismiss();
                        if (!response.isSuccessful()) {
                            Toast.makeText(AdminActivity.this, "No pudimos obtener la informacion de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        studentList = response.body();

                        if (studentList == null) {
                            Toast.makeText(AdminActivity.this, "No pudimos registrar el listado de los estudiantes", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        adapter = new ListViewAdapter_PassList(AdminActivity.this, studentList);
                        estudiantes.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(AdminActivity.this, "Error SLA > " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void Inicializar_controles() {
        progressDialog = new ProgressDialog(this);
        txt_perfil_nombre = (TextView) findViewById(R.id.txt_perfil_nombre);
        txt_perfil_nombre.setText(user.getName().trim() + " " + user.getLastName().trim());

        txt_perfil_correo = (TextView) findViewById(R.id.txt_perfil_correo);
        txt_perfil_correo.setText(user.getEmail());

        img_perfil_imagen = (CircleImageView) findViewById(R.id.img_perfil_imagen);
        /////Falta la captura de la url del perfil

        busqueda = findViewById(R.id.edt_admin_filtro);
        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<Student> studentList2 = new ArrayList<Student>();

                for (int x = 0; x < studentList.size(); x++) {
                    String check = (studentList.get(x).getPersonalDocument());
                    if (check.contains(busqueda.getText().toString())) {
                        studentList2.add(new Student(studentList.get(x).getStudentId(),
                                studentList.get(x).getName(),
                                studentList.get(x).getLastName(),
                                studentList.get(x).getPersonalDocument(),
                                studentList.get(x).getEmail(),
                                studentList.get(x).getPhoto()));
                    }
                }
                adapter = new ListViewAdapter_PassList(AdminActivity.this, studentList2);
                estudiantes.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        estudiantes = (ListView) findViewById(R.id.ls_admin_estudiantes);
        estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Student sStudent = ((Student) a.getItemAtPosition(position));

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(busqueda.getWindowToken(), 0);

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(AdminActivity.this);
                dialogo1.setTitle("Selecciona las acciones para: ");
                dialogo1.setMessage(sStudent.getName() + " " + sStudent.getLastName() + " | " + sStudent.getPersonalDocument());
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Matricular", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogo1, int id) {
                        setContentView(R.layout.layout_matricular);

                        progressDialog.setMessage("Obteniendo listado de grupos...");
                        progressDialog.show();

                        TextView m_nombre = findViewById(R.id.txt_admin2_subtitulo);
                        ListView lista_grupos = findViewById(R.id.ls_admin2_grupos);

                        m_nombre.setText(sStudent.getName().trim() + " " + sStudent.getLastName().trim() + "\n" + sStudent.getPersonalDocument());

                        GroupService groupService = new GroupService();

                        Call<List<Group>> service = groupService.getGroupsAll();
                        service
                                .enqueue(new Callback<List<Group>>() {
                                    @Override
                                    public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                                        progressDialog.dismiss();
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(AdminActivity.this, "No pudimos obtener la informacion de los grupos", Toast.LENGTH_SHORT).show();
                                            call.cancel();
                                            return;
                                        }
                                        List<Group> groupList = response.body();
                                        if (groupList == null) {
                                            Toast.makeText(AdminActivity.this, "No pudimos registrar el listado de grupos", Toast.LENGTH_SHORT).show();
                                            call.cancel();
                                            return;
                                        }
                                        ListViewAdapter_Group adapter = new ListViewAdapter_Group(AdminActivity.this, groupList);
                                        lista_grupos.setAdapter(adapter);

                                        lista_grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                                Group selectedGroup = ((Group) a.getItemAtPosition(position));

                                                AlertDialog.Builder matri = new AlertDialog.Builder(AdminActivity.this);
                                                matri.setTitle("[" + sStudent.getPersonalDocument() + "] " + sStudent.getName().trim() + " " + sStudent.getLastName().trim());
                                                matri.setMessage("Matricula para el curso:\n[" +
                                                        selectedGroup.getGroupName() + "] " +
                                                        selectedGroup.getSubject() + "\nPeriodo: " +
                                                        selectedGroup.getSemester() + "\n\n¿Desea continuar?");
                                                matri.setCancelable(false);
                                                matri.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface matri, int id) {

                                                        EnrollPayload enrollPayload = new EnrollPayload(
                                                                sStudent.getStudentId(), selectedGroup.getGroupId());

                                                        progressDialog.setMessage("Matriculando al estudiante...");
                                                        progressDialog.show();
                                                        adminService.postEnrollStudent(enrollPayload)
                                                                .enqueue(new Callback<EnrollResponse>() {
                                                                    @Override
                                                                    public void onResponse(Call<EnrollResponse> call, Response<EnrollResponse> response) {
                                                                        progressDialog.dismiss();
                                                                        if (!response.isSuccessful()) {
                                                                            ErrorResponse errorResponse = ErrorResponse.GetResponseError(response.errorBody());
                                                                            if (errorResponse != null)
                                                                                Toast.makeText(AdminActivity.this, errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                                            else
                                                                                Toast.makeText(AdminActivity.this, "No se pudo matricular al estudiante", Toast.LENGTH_LONG).show();
                                                                            return;
                                                                        }
                                                                        Toast.makeText(AdminActivity.this, "El estudiante fue matriculado exitosamente", Toast.LENGTH_LONG).show();
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<EnrollResponse> call, Throwable t) {
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(AdminActivity.this, "Hubo un error al registrar la asistencia", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });


                                                    }
                                                });
                                                matri.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface matri, int id) {
                                                    }
                                                });

                                                matri.show();
                                            }
                                        });


                                        EditText busqueda_g = findViewById(R.id.edt_admin2_filtro);
                                        busqueda_g.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                List<Group> groupList2 = new ArrayList<Group>();

                                                for (int x = 0; x < groupList.size(); x++) {
                                                    String check = (groupList.get(x).getSubject());
                                                    if (check.contains(busqueda_g.getText().toString())) {
                                                        groupList2.add(new Group(groupList.get(x).getGroupId(),
                                                                groupList.get(x).getGroupName(),
                                                                groupList.get(x).getSubject(),
                                                                groupList.get(x).getSubjectCode(),
                                                                groupList.get(x).getSemester(),
                                                                groupList.get(x).getTeacherId()));
                                                    }
                                                }
                                                ListViewAdapter_Group adapter = new ListViewAdapter_Group(AdminActivity.this, groupList2);
                                                lista_grupos.setAdapter(adapter);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });


                                    }

                                    @Override
                                    public void onFailure(Call<List<Group>> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AdminActivity.this, "Error GLA > " + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
                dialogo1.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.setNegativeButton("Registar tag", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        String text = sStudent.getName().trim() + " " + sStudent.getLastName().trim();
                        String text2 = sStudent.getPersonalDocument();
                        String text3 = (sStudent.getStudentId()).toString();
                        String encodedText = Base64.getEncoder().encodeToString(text.getBytes());
                        String encodedText2 = Base64.getEncoder().encodeToString(text2.getBytes());
                        String encodedText3 = Base64.getEncoder().encodeToString(text3.getBytes());
                        WriteActive = true;
                        writeHelper.writeText(encodedText, encodedText2, encodedText3);
                    }
                });
                dialogo1.show();
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
        if (WriteActive) {
            nfcManager.onActivityNewIntent(intent);
            WriteActive = false;
        }

    }

    public void returnLocalMain(View view) {
        startActivity(
                new Intent(AdminActivity.this, AdminActivity.class)
        );
    }

}