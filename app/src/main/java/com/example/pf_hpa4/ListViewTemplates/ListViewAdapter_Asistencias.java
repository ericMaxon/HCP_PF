package com.example.pf_hpa4.ListViewTemplates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pf_hpa4.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter_Asistencias extends ArrayAdapter<Asistencias> {
    private List<Asistencias> opciones = new ArrayList<>();

    public ListViewAdapter_Asistencias(Context context, List<Asistencias> datos) {
        super(context, R.layout.activity_asistencias_list, datos);

        opciones = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_listview_content, null);

        TextView lblTitulo = (TextView) item.findViewById(R.id.txt_listviewContent_titulo);
        lblTitulo.setText(opciones.get(position).getFecha());

        TextView lblSubtitulo = (TextView) item.findViewById(R.id.txt_listviewContent_subTitulo);
        lblSubtitulo.setText(opciones.get(position).getHora());

        TextView lblRightSubTitulo = (TextView) item.findViewById(R.id.txt_listviewContent_rigthSubTitulo);

        if (opciones.get(position).getEstado_Asistencia() == 1){
            lblRightSubTitulo.setText("Presente");
        }
        else if (opciones.get(position).getEstado_Asistencia() == 2){
            lblRightSubTitulo.setText("Tardanza");
        }
        else if (opciones.get(position).getEstado_Asistencia() == 3){
            lblRightSubTitulo.setText("Ausente");
        }
        else if (opciones.get(position).getEstado_Asistencia() == 4){
            lblRightSubTitulo.setText("Ausente con excusa");
        }

        return (item);
    }
}
