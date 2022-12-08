package com.example.pf_hpa4.ListViewTemplates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.MainActivity;
import com.example.pf_hpa4.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter_Grupos extends  ArrayAdapter<Grupos>{

    private List<Grupos> opciones = new ArrayList<>();

    public ListViewAdapter_Grupos(Context context, List<Grupos> datos){
        super(context, R.layout.activity_group_list, datos);

        opciones= datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_asignaciones, null);

        TextView lblAsignatura = (TextView)item.findViewById(R.id.txt_asignaciones_asignatura);
        lblAsignatura.setText("[" + opciones.get(position).getCodigoAsignatura() + "] " + opciones.get(position).getAsignatura());

        TextView lblGrupo = (TextView)item.findViewById(R.id.txt_asignaciones_grupo);
        lblGrupo.setText(opciones.get(position).getGrupo());

        TextView lblSemestre = (TextView)item.findViewById(R.id.txt_asignaciones_semestre);
        lblSemestre.setText(opciones.get(position).getPeriodo());


        return(item);
    }

}
