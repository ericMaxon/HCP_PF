package com.example.pf_hpa4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pf_hpa4.R;
import com.example.pf_hpa4.services.dto.responses.student.Student;

import java.util.List;

public class ListViewAdapter_PassList extends ArrayAdapter<Student> {

    private List<Student> opciones ;

    public ListViewAdapter_PassList(Context context, List<Student> datos) {
        super(context, R.layout.activity_pass_list, datos);

        opciones = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_listview_content2, null);

        TextView lblTitulo = (TextView) item.findViewById(R.id.txt_listviewContent2_titulo);
        lblTitulo.setText((opciones.get(position).getName() + " " + opciones.get(position).getLastName()));

        TextView lblSubtitulo = (TextView) item.findViewById(R.id.txt_listviewContent2_subTitulo);
        lblSubtitulo.setText(opciones.get(position).getPersonalDocument());


        return (item);
    }
}
