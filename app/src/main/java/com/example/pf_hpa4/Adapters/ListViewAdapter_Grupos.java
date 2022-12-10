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

public class ListViewAdapter_Grupos extends  ArrayAdapter<Grupos>{

    private List<Grupos> opciones = new ArrayList<>();

    public ListViewAdapter_Grupos(Context context, List<Grupos> datos){
        super(context, R.layout.activity_grupos_list, datos);

        opciones= datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_listview_content, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.txt_listviewContent_titulo);
        lblTitulo.setText("[" + opciones.get(position).getCodigoAsignatura() + "] " + opciones.get(position).getAsignatura());

        TextView lblSubTitulo = (TextView)item.findViewById(R.id.txt_listviewContent_subTitulo);
        lblSubTitulo.setText(opciones.get(position).getGrupo());

        TextView lblRightSubTitulo = (TextView)item.findViewById(R.id.txt_listviewContent_rigthSubTitulo);
        lblRightSubTitulo.setText(opciones.get(position).getPeriodo());


        return(item);
    }

}

