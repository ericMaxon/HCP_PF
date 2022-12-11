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

import com.example.pf_hpa4.Activities.GroupListActivity;
import com.example.pf_hpa4.constants.ApiConstants.Role;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.AuthService;
import com.example.pf_hpa4.services.dto.request.auth.AuthPayload;
import com.example.pf_hpa4.services.dto.responses.auth.Login;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText login_user, login_pass;
    TextView test;
    Button cirLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IniciarControler();
    }


    private void IniciarControler() {
        login_user = (EditText) findViewById(R.id.login_user);
        login_pass = (EditText) findViewById(R.id.login_pass);
        test = (TextView) findViewById(R.id.login_subtitulo);
        cirLoginButton = findViewById(R.id.cirLoginButton);
    }

    public void login(View view) {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Iniciando sesion...");
        progressDialog.show();
        String cuenta = login_user.getText().toString();
        String pass = login_pass.getText().toString();
        AuthService authService = new AuthService();
        cirLoginButton.setEnabled(false);
        if (cuenta.equals("") || pass.equals("")) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
            cirLoginButton.setEnabled(true);
        }

        try {
            authService.postLogin(new AuthPayload(cuenta, pass))
                    .enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            progressDialog.dismiss();
                            cirLoginButton.setEnabled(true);
                            if (!response.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                call.cancel();
                                return;
                            }

                            Login loginObj = response.body();
                            if(loginObj == null){
                                Toast.makeText(LoginActivity.this, "Hubo un error al obtener tu información, intenta nuevamente mas tarde", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (loginObj.getUsuario().getActive() == 0) {
                                Toast.makeText(LoginActivity.this, "Tu usuario se encuentra inactivo", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SharedPreferences usuario = getSharedPreferences(SPreferencesKeys.usuario, Context.MODE_PRIVATE);
                            usuario.edit()
                                    .putString("jsonUser", loginObj.getUsuario().toString())
                                    .apply();
                            if (Objects.equals(loginObj.getUsuario().getRole(), Role.admin)) {
                                Toast.makeText(LoginActivity.this, "Falta implementar el admin > ", Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(
                                        new Intent(LoginActivity.this, GroupListActivity.class)
                                );
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            progressDialog.dismiss();
                            cirLoginButton.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "Error > " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(this, "Error > " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

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