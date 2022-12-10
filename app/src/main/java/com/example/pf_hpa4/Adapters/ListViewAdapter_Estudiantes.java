package com.example.pf_hpa4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pf_hpa4.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter_Estudiantes  extends ArrayAdapter<Estudiantes> {

    private List<Estudiantes> opciones = new ArrayList<>();

    public ListViewAdapter_Estudiantes(Context context, List<Estudiantes> datos) {
        super(context, R.layout.activity_estudiantes_list, datos);

        opciones = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_listview_content, null);

        TextView lblTitulo = (TextView) item.findViewById(R.id.txt_listviewContent_titulo);
        lblTitulo.setText((opciones.get(position).getNombre() + " " + opciones.get(position).getApellido()));

        TextView lblSubtitulo = (TextView) item.findViewById(R.id.txt_listviewContent_subTitulo);
        lblSubtitulo.setText(opciones.get(position).getCorreo());

        TextView lblRightSubTitulo = (TextView) item.findViewById(R.id.txt_listviewContent_rigthSubTitulo);
        lblRightSubTitulo.setText(opciones.get(position).getCedula());

        return (item);
    }
}
