package com.example.pf_hpa4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.activities.GruposListActivity;
import com.example.pf_hpa4.constants.ApiConstants;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.AuthService;
import com.example.pf_hpa4.services.dto.request.auth.AuthPayload;
import com.example.pf_hpa4.services.dto.responses.auth.Login;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    EditText login_user, login_pass;
    TextView test;
    Button cirLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IniciarControler();
    }


    private void IniciarControler() {
        login_user = (EditText) findViewById(R.id.login_user);
        login_pass = (EditText) findViewById(R.id.login_pass);
        test = (TextView) findViewById(R.id.login_subtitulo);
        cirLoginButton = findViewById(R.id.cirLoginButton);
    }

    public void login(View view) {
        int id;
        String cuenta = login_user.getText().toString();
        String pass = login_pass.getText().toString();
        AuthService authService = new AuthService();
        cirLoginButton.setEnabled(false);
        if (!(cuenta.equals("") || pass.equals(""))) {
            try {
                authService.postLogin(new AuthPayload(cuenta, pass))
                        .enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                    call.cancel();
                                    return;
                                }

                                Login loginObj = response.body();
                                if (loginObj.getUsuario().getActive() == 0) {
                                    Toast.makeText(MainActivity.this, "Tu usuario se encuentra inactivo", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                SharedPreferences usuario = getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
                                usuario.edit()
                                        //TODO: agregar los keys
                                        .putInt("idUser", loginObj.getUsuario().getUserId())
                                        .putInt("role", loginObj.getUsuario().getRole())
                                        .putString("nombres", loginObj.getUsuario().getName())
                                        .putString("apellidos", loginObj.getUsuario().getLastName())
                                        .putString("cedula", loginObj.getUsuario().getPersonalDocument())
                                        .putString("email", loginObj.getUsuario().getEmail())
                                        .putInt("active", loginObj.getUsuario().getActive())
                                        ///////Necesito el email_verified_at en el retrofit   .putInt("email_verified_at", loginObj.getUsuario().getEmail())
                                        .apply();


                                if (Objects.equals(loginObj.getUsuario().getRole(), ApiConstants.Roles.admin)){
                                    Toast.makeText(MainActivity.this, "Falta implementar el admin > ", Toast.LENGTH_LONG).show();
                                    return;

                                } else {
                                    usuario = getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
                                    usuario.edit()
                                            .putInt("docente_id", loginObj.getUsuario().getTeacherId())
                                            .apply();
                                    startActivity(
                                            new Intent(MainActivity.this, GruposListActivity.class)
                                    );
                                    return;

                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Error > " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                                cirLoginButton.setEnabled(true);
                            }
                        });

            } catch (Exception e) {
                Toast.makeText(this, "Error > " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        }
        cirLoginButton.setEnabled(true);
    }

    public void register(View view) {

    }

    public void c_registrar(View view) {
        findViewById(R.id.include_login).setVisibility(View.GONE);
        findViewById(R.id.include_register).setVisibility(View.VISIBLE);
    }

    public void c_login(View view) {
        findViewById(R.id.include_login).setVisibility(View.VISIBLE);
        findViewById(R.id.include_register).setVisibility(View.GONE);
    }

    public void c_pass(View view) {

    }
}