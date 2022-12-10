package com.example.pf_hpa4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pf_hpa4.ListViewTemplates.Grupos;
import com.example.pf_hpa4.ListViewTemplates.ListViewAdapter_Grupos;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.StudentGroupService;
import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GruposListActivity extends AppCompatActivity {
    ListView Listado_Grupos;
    TextView txt_perfil_nombre, txt_perfil_correo;
    CircleImageView img_perfil_imagen;

    int idGrupo_Select;

    ////Datos de usuario
    String nombres, apellidos, email, cedula;
    int idUser, email_verified_at, role, active, docente_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos_list);

        Inicializar_controles();
        LoadListView_Grupos();
    }

    private void Inicializar_controles(){

        SharedPreferences preferences=getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
        idUser = preferences.getInt("idUser", -1);
        nombres = preferences.getString("nombres", null);
        apellidos = preferences.getString("apellidos", null);
        email = preferences.getString("email", null);
        cedula = preferences.getString("cedula", null);
        email_verified_at = preferences.getInt("email_verified_at", -1);
        role = preferences.getInt("role", -1);
        active = preferences.getInt("active", -1);
        docente_id = preferences.getInt("docente_id", -1);

        txt_perfil_nombre = (TextView)findViewById(R.id.txt_perfil_nombre);
        txt_perfil_nombre.setText(nombres + " " + apellidos);

        txt_perfil_correo = (TextView)findViewById(R.id.txt_perfil_correo);
        txt_perfil_correo.setText(email);

        img_perfil_imagen = (CircleImageView)findViewById(R.id.img_perfil_imagen);
        /////Falta la captura de la url del perfil

        Listado_Grupos = (ListView)findViewById(R.id.group_lvGrupos);
        Listado_Grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                int id_grupoSeleccionado = ((Grupos)a.getItemAtPosition(position)).getId();
                String nombreGrupo = ((Grupos)a.getItemAtPosition(position)).getAsignatura();

                idGrupo_Select = id_grupoSeleccionado;

                if (role == 2){

                    Intent i = new Intent(GruposListActivity.this, AsistenciasListActivity.class);
                    i.putExtra("grupo", id_grupoSeleccionado);
                    i.putExtra("estudiante", idUser);
                    i.putExtra("asignatura", nombreGrupo);
                    startActivity(i);

                } else if (role == 3){

                }
            }
        });
    }


    ///////////Cargar ListView con los grupos
    private void LoadListView_Grupos()
    {
        List<Grupos> opciones = this.GetElementsToListView_Grupos();

        ListViewAdapter_Grupos adapter = new ListViewAdapter_Grupos(this, opciones);

        Listado_Grupos.setAdapter(adapter);
    }

    private List<Grupos> GetElementsToListView_Grupos()
    {


        if (role == 2){
            ///////////Aqui se consulta el retrofit grupos/profesor/{idUser} si {role = 2} y se envian los resultados al listview
        } else if (role == 3){
            ///////////Aqui se consulta el retrofit grupos/estudiante/{idUser} si {role = 3} y se envian los resultados al listview
        }

        List<Grupos> opciones = new ArrayList<Grupos>();

        opciones.add(new Grupos(1, "Herramientas de Programacion Aplicada IV", "324", 2, "1IL142", "II Semestre 2022"));
        opciones.add(new Grupos(2, "Base de Datos", "2341", 2, "1IL112", "II Semestre 2022"));

        return  opciones;

    }


}