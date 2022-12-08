package com.example.pf_hpa4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pf_hpa4.Adapters.Grupos;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Grupos;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.services.StudentGroupService;
import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity {

    int teacherId;
    ListView Listado_Grupos;

    int idGrupo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        setContentView(R.layout.activity_group_list);

        Inicializar_controles();
        LoadListViewTemplate();
    }

    private void Inicializar_controles(){

        Listado_Grupos = (ListView)findViewById(R.id.group_lvGrupos);
        Listado_Grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                int opcionSeleccionada =
                        ((Grupos)a.getItemAtPosition(position)).getId();

                idGrupo = opcionSeleccionada;

                /////cambiar el layaout y llamar al retrofit dependiendo del tipo de usuario
            }
        });
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


    ///////////Cargar ListView con los grupos
    private void LoadListViewTemplate()
    {
        List<Grupos> opciones = this.GetElementsToListViewGrupos();

        ListViewAdapter_Grupos adapter = new ListViewAdapter_Grupos(this, opciones);

        Listado_Grupos.setAdapter(adapter);
    }

    private List<Grupos> GetElementsToListViewGrupos()
    {
        //TODO: cambiar a la estructura del retrofit que esta en services -> dto -> responses -> student -> group
        List<Grupos> opciones = new ArrayList<Grupos>();

        opciones.add(new Grupos(1, "HPA 4", "324", 2, "1IL142", "II Semestre 2022"));
        opciones.add(new Grupos(2, "Base de Datos", "2341", 2, "1IL112", "II Semestre 2022"));

        return  opciones;

    }

}