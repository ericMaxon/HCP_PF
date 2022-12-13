package com.example.pf_hpa4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.ApiConstants.AttendeeStatus;
import com.example.pf_hpa4.services.dto.responses.student.Attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListViewAdapter_Attendance extends ArrayAdapter<Attendance> {
    private List<Attendance> opciones = new ArrayList<>();

    public ListViewAdapter_Attendance(Context context, List<Attendance> datos) {
        super(context, R.layout.activity_asistencias_list, datos);

        opciones = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.layout_listview_content, null);

        TextView lblTitulo = (TextView) item.findViewById(R.id.txt_listviewContent_titulo);
        lblTitulo.setText(opciones.get(position).getDate().toString());

        TextView lblSubtitulo = (TextView) item.findViewById(R.id.txt_listviewContent_subTitulo);
        lblSubtitulo.setText(opciones.get(position).getHour());

        TextView lblRightSubTitulo = (TextView) item.findViewById(R.id.txt_listviewContent_rigthSubTitulo);

        lblRightSubTitulo.setText(AttendeeStatus.getStatus(opciones.get(position).getSubjectStatusId()));

        RelativeLayout content = item.findViewById(R.id.rl_listview_content);
        if (opciones.get(position).getSubjectStatusId() == 1) {
            content.setBackgroundResource(R.drawable.green_circular_bk);
        } else if (opciones.get(position).getSubjectStatusId() == 2){
            content.setBackgroundResource(R.drawable.yelow_circular_bk);
        }else if (opciones.get(position).getSubjectStatusId() == 3){
            content.setBackgroundResource(R.drawable.red_circular_bk);
        }else {
            content.setBackgroundResource(R.drawable.yelow_circular_bk);
        }

        return (item);
    }
}
