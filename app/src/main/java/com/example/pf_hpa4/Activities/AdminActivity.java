package com.example.pf_hpa4.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.R;
import com.example.pf_hpa4.constants.ApiConstants;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.dto.responses.auth.User;
import com.example.pf_hpa4.services.dto.responses.student.Group;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminActivity extends AppCompatActivity {

    TextView txt_perfil_nombre, txt_perfil_correo;
    CircleImageView img_perfil_imagen;

    User user = new User();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        SharedPreferences userSP_JSON = getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
        user = user.GetFromJSON(userSP_JSON.getString("jsonUser", ""));
        ///////////TODO: PROBLEMA CON RECIBIR LOS DATOS DEL USUARIO DEL JSON, RETORNA NULL


        Inicializar_controles();
    }

    private void Inicializar_controles() {
        progressDialog = new ProgressDialog(this);
        txt_perfil_nombre = (TextView) findViewById(R.id.txt_perfil_nombre);
        //txt_perfil_nombre.setText(user.getName() + " " + user.getLastName());
        txt_perfil_nombre.setText("NOMBRE");

        txt_perfil_correo = (TextView) findViewById(R.id.txt_perfil_correo);
        //txt_perfil_correo.setText(user.getEmail());
        txt_perfil_correo.setText("CORREO");

        img_perfil_imagen = (CircleImageView) findViewById(R.id.img_perfil_imagen);
        /////Falta la captura de la url del perfil


    }
}