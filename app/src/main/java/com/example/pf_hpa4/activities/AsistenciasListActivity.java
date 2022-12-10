package com.example.pf_hpa4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pf_hpa4.Adapters.Asistencias;
import com.example.pf_hpa4.Adapters.Grupos;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Asistencias;
import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.SPreferencesKeys;

import java.util.ArrayList;
import java.util.List;


public class AsistenciasListActivity extends AppCompatActivity {

    ListView Listado_Asistencias;
    String idGrupo, idUser, Asignatura, Nombre, Cedula;
    TextView txtAsignatura, txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencias_list);

        Intent i = getIntent();
        if (i != null){
            RecibirDatos(i);
        }

        Inicializar_controles();
    }

    private void Inicializar_controles(){

        Listado_Asistencias = (ListView)findViewById(R.id.asistencia_lvGrupos);

        txtTitulo = (TextView)findViewById(R.id.txt_asistencia_titulo);
        txtTitulo.setText(Nombre);

        txtAsignatura = (TextView)findViewById(R.id.txt_asistencia_subtitulo);
        txtAsignatura.setText(Asignatura);
        LoadListView_Asistencias();
    }

    private void RecibirDatos(Intent i) {
        idUser = (i.getStringExtra("id_estudiante"));
        idGrupo = (i.getStringExtra("id_grupo"));
        Asignatura = (i.getStringExtra("asignatura"));
        Nombre = (i.getStringExtra("nombre"));
        Cedula = (i.getStringExtra("cedula"));
    }

    ////////////// Cargar ListView con las Asistencias

    private void LoadListView_Asistencias()
    {

        List<Asistencias> opciones = this.GetElementsToListView_Asistencias();

        ListViewAdapter_Asistencias adapter = new ListViewAdapter_Asistencias(this, opciones);

        Listado_Asistencias.setAdapter(adapter);
    }

    private List<Asistencias> GetElementsToListView_Asistencias()
    {
        List<Asistencias> opciones = new ArrayList<Asistencias>();

        ///////////Aqui se consulta el retrofit estudiante/asistencia/{idGrupo}/{idUser} y se envian los resultados al listview

        opciones.add(new Asistencias(1,  "12/12/2022", "13:45", 1, 2, 1));
        opciones.add(new Asistencias(2,  "11/12/2022", "9:45", 1, 2, 1));
        opciones.add(new Asistencias(3,  "10/12/2022", "7:15", 1, 2, 2));
        opciones.add(new Asistencias(4,  "12/12/2022", "15:18", 1, 2, 3));

        return  opciones;

    }

}