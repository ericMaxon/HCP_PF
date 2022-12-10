package com.example.pf_hpa4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pf_hpa4.Adapters.Asistencias;
import com.example.pf_hpa4.Adapters.Estudiantes;
import com.example.pf_hpa4.Adapters.Grupos;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Asistencias;
import com.example.pf_hpa4.Adapters.ListViewAdapter_Estudiantes;
import com.example.pf_hpa4.R;

import java.util.ArrayList;
import java.util.List;

public class EstudiantesListActivity extends AppCompatActivity {

    ListView Listado_Estudiantes;
    TextView txtAsignatura;
    String Asignatura, idGrupo, Grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes_list);

        Intent i = getIntent();
        if (i != null){
            RecibirDatos(i);
        }


        Inicializar_controles();
        LoadListView_Estudiantes();
    }

    private void Inicializar_controles(){

        Listado_Estudiantes = (ListView)findViewById(R.id.estudiantes_lvGrupos);
        txtAsignatura = (TextView)findViewById(R.id.txt_estudiante_titulo);
        txtAsignatura.setText("[" + Grupo + "] " + Asignatura);

        Listado_Estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                int id_Estudiante = ((Estudiantes)a.getItemAtPosition(position)).getId();
                String nombre  = (((Estudiantes)a.getItemAtPosition(position)).getNombre() + " " + ((Estudiantes)a.getItemAtPosition(position)).getApellido());
                String cedula = ((Estudiantes)a.getItemAtPosition(position)).getCedula();

                Intent i = new Intent(EstudiantesListActivity.this, AsistenciasListActivity.class);
                i.putExtra("id_estudiante", id_Estudiante);
                i.putExtra("id_grupo", idGrupo);
                i.putExtra("asignatura", "[" + Grupo + "] " + Asignatura);
                i.putExtra("nombre", nombre);
                i.putExtra("cedula", cedula);

                startActivity(i);
            }
        });

    }

    private void RecibirDatos(Intent i) {
        idGrupo = (i.getStringExtra("idgrupo"));
        Grupo = (i.getStringExtra("grupo"));
        Asignatura = (i.getStringExtra("asignatura"));
    }

    ////////////// Cargar ListView con la lista de Estudiantes

    private void LoadListView_Estudiantes()
    {

        List<Estudiantes> opciones = this.GetElementsToListView_Estudiantes();

        ListViewAdapter_Estudiantes adapter = new ListViewAdapter_Estudiantes(this, opciones);

        Listado_Estudiantes.setAdapter(adapter);
    }

    private List<Estudiantes> GetElementsToListView_Estudiantes()
    {
        List<Estudiantes> opciones = new ArrayList<Estudiantes>();

        ///////////Aqui se consulta el retrofit estudiantes/grupos/{idGrupo} y se envian los resultados al listview

        opciones.add(new Estudiantes(1,  "VIVEK", "AHIR", "20-0039-004171", "vivek.ahir@utp.ac.pa", ""));
        opciones.add(new Estudiantes(2,  "AGUSTÍN", "ALVIA", "03-0731-001188", "agustín.alvia@utp.ac.pa", "1"));
        opciones.add(new Estudiantes(3,  "ANTHONY", "AVILA", "20-0070-004203", "anthony.avila@utp.ac.pa", "2"));

        return  opciones;

    }
}