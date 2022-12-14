package com.example.pf_hpa4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pf_hpa4.Activities.AdminActivity;
import com.example.pf_hpa4.Activities.GroupListActivity;
import com.example.pf_hpa4.constants.ApiConstants.Role;
import com.example.pf_hpa4.constants.SPreferencesKeys;
import com.example.pf_hpa4.services.AuthService;
import com.example.pf_hpa4.services.dto.request.auth.AuthPayload;
import com.example.pf_hpa4.services.dto.request.auth.RegisterPayload;
import com.example.pf_hpa4.services.dto.responses.auth.Login;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText login_user, login_pass, register_nombre, register_apellido, register_cedula, register_pass, register_pass2, register_correo;
    TextView test;
    Button cirLoginButton;

    AuthService authService;

    public LoginActivity() {
        authService = new AuthService();
    }

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

        register_nombre = findViewById(R.id.register_nombre);
        register_apellido = findViewById(R.id.register_apellido);
        register_cedula = findViewById(R.id.register_cedula);
        register_pass = findViewById(R.id.register_pass);
        register_pass2 = findViewById(R.id.register_pass2);
        register_correo = findViewById(R.id.register_correo);

        progressDialog = new ProgressDialog(LoginActivity.this);
    }

    public void login(View view) {
        progressDialog.setMessage("Iniciando sesion...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String cuenta = login_user.getText().toString();
        String pass = login_pass.getText().toString();
        cirLoginButton.setEnabled(false);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(login_pass.getWindowToken(), 0);

        if (cuenta.equals("") || pass.equals("")) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
            cirLoginButton.setEnabled(true);
        } else {
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
                                if (loginObj == null) {
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
                                    startActivity(
                                            new Intent(LoginActivity.this, AdminActivity.class)
                                    );
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
                                Toast.makeText(LoginActivity.this, "Error LA-L> " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

            } catch (Exception e) {
                progressDialog.dismiss();
                Toast.makeText(this, "Error LA-L> " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void register(View view) {

        String _nombre = register_nombre.getText().toString();
        String _apellido = register_apellido.getText().toString();
        String _cedula = register_cedula.getText().toString();
        String _pass = register_pass.getText().toString();
        String _pass2 = register_pass2.getText().toString();
        String _correo = register_correo.getText().toString();

        if (_nombre.equals("") || _apellido.equals("") || _cedula.equals("") || _pass.equals("") || _pass2.equals("") || _correo.equals("")) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        } else {
            if (_pass.equals(_pass2)) {
                if (_pass.length() > 5 ){
                    progressDialog.setMessage("Registrando cuenta...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    RegisterPayload registerPayload = new RegisterPayload(
                            _nombre,
                            _apellido,
                            _cedula,
                            _correo,
                            _pass
                    );
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(register_pass2.getWindowToken(), 0);

                    authService.postRegister(registerPayload)
                            .enqueue(new Callback<Response<Void>>() {
                                @Override
                                public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                                    progressDialog.dismiss();
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "No logramos registrar tu usuario", Toast.LENGTH_SHORT).show();
                                        call.cancel();
                                        return;
                                    }
                                    Toast.makeText(LoginActivity.this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                                    findViewById(R.id.include_login).setVisibility(View.VISIBLE);
                                    findViewById(R.id.include_register).setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<Response<Void>> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Error LA-R> " + t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "Las contraseña debe tener 6 o mas caracteres", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        }

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
        //TODO: No se puede hacer nada aqui
    }
}