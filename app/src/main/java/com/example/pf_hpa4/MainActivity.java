package com.example.pf_hpa4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.NFC.NFC_Actitvity;
import com.example.pf_hpa4.request.Estudiante;
import com.example.pf_hpa4.services.ApiService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IniciarControler();


        ///////////////////////////////////////// RETROFIT CONSULTA ///////////////

        Call<List<Estudiante>> listadoEstudiantesGrupo = ApiService.getApiService().getStudentsByGroup(1);

        listadoEstudiantesGrupo.enqueue(new Callback<List<Estudiante>>() {
            @Override
            public void onResponse(Call<List<Estudiante>> call, Response<List<Estudiante>> response) {
                try {
                    if (response.isSuccessful()){
                        //Estudiante listado = (Estudiante) response.body();
                        test.setText(response.body().toString());
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    test.setText("Error : " + e);
                }


            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////
    }

    EditText login_user, login_pass;
    TextView test;
    private void IniciarControler(){
        login_user = (EditText)findViewById(R.id.login_user);
        login_pass = (EditText)findViewById(R.id.login_pass);
        test = (TextView)findViewById(R.id.login_subtitulo);
    }

    public void login(View view){
        int id;
        String cuenta = login_user.getText().toString();
        String pass = login_pass.getText().toString();

        if (!(cuenta.equals("") || pass.equals(""))){
            try {

                id = 1;
                Intent i = new Intent(this, EstudiantesActivity.class);
                i.putExtra("id",id);
                startActivity(i);

            } catch (Exception e) {
                Toast.makeText(this, "Error > "+ e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Debe llenar todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view){

    }

    public void c_registrar(View view){
        findViewById(R.id.include_login).setVisibility(View.GONE);
        findViewById(R.id.include_register).setVisibility(View.VISIBLE);
    }

    public void c_login(View view){
        findViewById(R.id.include_login).setVisibility(View.VISIBLE);
        findViewById(R.id.include_register).setVisibility(View.GONE);
    }

    public void c_pass(View view){

    }
}