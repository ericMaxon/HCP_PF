package com.example.pf_hpa4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pf_hpa4.R;
import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.List;

public class ListViewAdapter_Group extends  ArrayAdapter<Group>{

    private List<Group> opciones;

    public ListViewAdapter_Group(Context context, List<Group> datos){
        super(context, R.layout.activity_grupos_list, datos);

        opciones= datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_listview_content, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.txt_listviewContent_titulo);
        lblTitulo.setText("[" + opciones.get(position).getSubjectCode() + "] " + opciones.get(position).getSubject());

        TextView lblSubTitulo = (TextView)item.findViewById(R.id.txt_listviewContent_subTitulo);
        lblSubTitulo.setText(opciones.get(position).getGroupName());

        TextView lblRightSubTitulo = (TextView)item.findViewById(R.id.txt_listviewContent_rigthSubTitulo);
        lblRightSubTitulo.setText(opciones.get(position).getSemester());


        return(item);
    }

}

