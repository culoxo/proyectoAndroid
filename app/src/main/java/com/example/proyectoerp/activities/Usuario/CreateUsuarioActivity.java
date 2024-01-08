package com.example.proyectoerp.activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.MainActivity;
import com.example.proyectoerp.R;
import com.example.proyectoerp.dto.UsuarioDTO;
import com.example.proyectoerp.model.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUsuarioActivity extends AppCompatActivity {

    EditText usuarioText, nameText,apellido1Text,apellido2Text,emailText, passwordText, preguntaSegText, respuestaSegText;
    Button createButton, volverButton;
    CRUDInterface crudInterface;
    CheckBox activoBox;
    Boolean admin = false;

    Boolean soyAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_usuario);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        passwordText = findViewById(R.id.PasswordText);
        usuarioText = findViewById(R.id.Usuario);
        nameText = findViewById(R.id.NameText);
        apellido1Text = findViewById(R.id.Apellido1Text);
        apellido2Text = findViewById(R.id.Apellido2Text);
        emailText = findViewById(R.id.EmailText);
        preguntaSegText = findViewById(R.id.PreguntaSegText);
        respuestaSegText = findViewById(R.id.RespuestaSegText);
        activoBox = findViewById(R.id.activoBox);
        createButton = findViewById(R.id.createButton);
        volverButton = findViewById(R.id.volverButton);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMain();
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        apellido1Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        apellido2Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        preguntaSegText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        respuestaSegText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        createButton.setEnabled(buttonEnabled());
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usuarioText.getText().toString().endsWith("admin")) {
                    admin = true;
                };
       //     String pass = hashPassword(passwordText.getText().toString());
                UsuarioDTO usuarioDto = new UsuarioDTO(usuarioText.getText().toString(),nameText.getText().toString(), apellido1Text.getText().toString(),apellido2Text
                        .getText().toString(),emailText.getText().toString(), passwordText.getText().toString(), activoBox.isChecked(), preguntaSegText.getText().toString(), respuestaSegText.getText().toString(), false, admin );
                create(usuarioDto);
            }
        });
    }

    private void create(UsuarioDTO usuarioDto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Usuario> call = crudInterface.createUsuario(usuarioDto);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Usuario usuario = response.body();
                Toast.makeText(getApplicationContext(), "El usuario " + usuario.getName() + " ha sido creado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), UsuarioMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0 && apellido1Text.getText().toString().trim().length()>0 && apellido1Text.getText().toString().trim().length()>0 && emailText.getText().toString().trim().length()>0 && passwordText.getText().toString().trim().length()>0 && preguntaSegText.getText().toString().trim().length()>0 && respuestaSegText.getText().toString().trim().length()>0;
    }
/*
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            // Convierte el hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}