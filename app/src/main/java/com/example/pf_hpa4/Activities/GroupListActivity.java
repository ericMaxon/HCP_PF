package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.Adapters.ListViewAdapter_Group;

import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.ApiConstants.Role;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.GroupService;
import com.example.pf_hpa4.services.dto.responses.auth.User;
import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity {
    ListView Listado_Grupos;
    TextView txt_perfil_nombre, txt_perfil_correo;
    CircleImageView img_perfil_imagen;

    ProgressDialog progressDialog;
    User user = new User();
    GroupService groupService = new GroupService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos_list);

        SharedPreferences userSP_JSON = getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
        user = user.GetFromJSON(userSP_JSON.getString("jsonUser", ""));
        Call<List<Group>> service =
                Objects.equals(user.getRole(), Role.teacher) && user.getTeacherId() != null
                        ? groupService.getGroupsByProfesorId(1)
                        : groupService.getGroupsByStudentId(1);

        Inicializar_controles();
        progressDialog.setMessage("Obteniendo listado de grupos...");
        progressDialog.show();
        service
                .enqueue(new Callback<List<Group>>() {
                    @Override
                    public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                        progressDialog.dismiss();
                        if (!response.isSuccessful()) {
                            Toast.makeText(GroupListActivity.this, "No pudimos obtener la informacion de los grupos", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        List<Group> groupList = response.body();
                        if (groupList == null) {
                            Toast.makeText(GroupListActivity.this, "No pudimos registrar el listado de grupos", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        LoadListView_Grupos(groupList);
                    }

                    @Override
                    public void onFailure(Call<List<Group>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(GroupListActivity.this, "Error GLA > " + t.getMessage(), Toast.LENGTH_LONG).show();
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

        Listado_Grupos = (ListView) findViewById(R.id.group_lvGrupos);
        Listado_Grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Group selectedGroup = ((Group) a.getItemAtPosition(position));

                if (Objects.equals(user.getRole(), Role.teacher)) {
                    Intent i = new Intent(GroupListActivity.this, StudentListActivity.class)
                            .putExtra("json_SelectedGroup", selectedGroup.toString());
                    startActivity(i);
                    return;
                }
                if (Objects.equals(user.getRole(), Role.student)) {
                    Intent i = new Intent(GroupListActivity.this, AttendanceListActivity.class)
                            .putExtra("json_SelectedGroup", selectedGroup.toString());
                    startActivity(i);
                }
            }
        });
    }


    ///////////Cargar ListView con los grupos
    private void LoadListView_Grupos(List<Group> groupData) {
        ListViewAdapter_Group adapter = new ListViewAdapter_Group(this, groupData);
        Listado_Grupos.setAdapter(adapter);
    }


}