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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.pf_hpa4.Adapters.ListViewAdapter_PassList;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Students;
import com.example.pf_hpa4.NFC.NFCManager;
import com.example.pf_hpa4.NFC.WriteTagHelper;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.ApiConstants;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.StudentService;
import com.example.pf_hpa4.services.dto.responses.auth.User;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;
import com.example.pf_hpa4.services.dto.responses.student.Student;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
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

    User user = new User();
    StudentService studentService = new StudentService();
    ListViewAdapter_PassList adapter;
    List<Student> studentList;

    private PopupMenu popup;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        nfcManager = new NFCManager(this);
        nfcManager.onActivityCreate();

        writeHelper= new WriteTagHelper(AdminActivity.this, nfcManager);
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
        txt_perfil_nombre.setText(user.getName() + " " + user.getLastName());

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

                Boolean verified = false;
                for (int x = 0; x < studentList.size(); x++){
                    String check = (studentList.get(x).getPersonalDocument());
                    if (check.contains(busqueda.getText().toString())){
                        verified = true;
                        studentList2.add(new Student(studentList.get(x).getStudentId(), studentList.get(x).getName(), studentList.get(x).getLastName(), studentList.get(x).getPersonalDocument(), studentList.get(x).getEmail(), studentList.get(x).getPhoto()));
                    }
                }
                if (verified) {
                    adapter = new ListViewAdapter_PassList(AdminActivity.this, studentList2);
                    estudiantes.setAdapter(adapter);
                }
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

                    }
                });
                dialogo1.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.setNegativeButton("Registar tag", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        String text = sStudent.getName() + " " + sStudent.getLastName();
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

}