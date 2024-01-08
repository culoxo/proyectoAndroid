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
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.activities.Cliente.DetailClienteActivity;
import com.example.proyectoerp.dto.UsuarioDTO;
import com.example.proyectoerp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditUsuarioActivity extends AppCompatActivity {

    Usuario usuario;
    EditText emailText, nameText, usernameText,surnameText, surname2Text, passwordText, preguntaSegText, respuestaSegText ;
    CRUDInterface crudInterface;
    Button editButton, volverButton;
    Long id;
    String nombre, username,email,apellido1, apellido2, password, preguntaSeg, respuestaSeg;
    CheckBox activoBox;
    Boolean activo, soyAdmin;
    Boolean admin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_usuario);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        id = getIntent().getExtras().getLong("id");
        nombre= getIntent().getExtras().getString("nombre");
        username= getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");
        apellido1 = getIntent().getExtras().getString("apellido1");
        apellido2 = getIntent().getExtras().getString("apellido2");
        password = getIntent().getExtras().getString("password");
        preguntaSeg = getIntent().getExtras().getString("preguntaSeg");
        respuestaSeg = getIntent().getExtras().getString("respuestaSeg");
        activo = getIntent().getExtras().getBoolean("activo");
        usernameText =findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        surnameText = findViewById(R.id.surnameText);
        surname2Text = findViewById(R.id.surname2Text);
        preguntaSegText = findViewById(R.id.preguntaSegText);
        respuestaSegText = findViewById(R.id.respuestaSegText);
        nameText = findViewById(R.id.nameText);
        emailText.setText(email);
        nameText.setText(nombre);
        usernameText.setText(username);
        surnameText.setText(apellido1);
        surname2Text.setText(apellido2);
        preguntaSegText.setText(preguntaSeg);
        respuestaSegText.setText(respuestaSeg);
        activoBox = findViewById(R.id.activoBox);
        editButton = findViewById(R.id.editButton);
        volverButton = findViewById(R.id.volverButton);
        activoBox.setChecked(activo);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVolver();
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        surnameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        surname2Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
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
                editButton.setEnabled(buttonEnabled());
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
                editButton.setEnabled(buttonEnabled());
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
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editButton.setEnabled(buttonEnabled());
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameText.getText().toString().endsWith("admin")) {
                    admin = true;
                };
                UsuarioDTO usuarioDto = new UsuarioDTO(usernameText.getText().toString(),nameText.getText().toString(), surnameText.getText().toString(),surname2Text
                        .getText().toString(),emailText.getText().toString(), password, activoBox.isChecked(), preguntaSegText.getText().toString(), respuestaSegText.getText().toString(), false, admin );
                edit(usuarioDto);
            }
        });
    }

    private void edit (UsuarioDTO usuarioDto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Usuario> call = crudInterface.editUsuario(id, usuarioDto);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Usuario usuarios = response.body();
                Toast.makeText(getApplicationContext(), "El usuario " + nombre + " ha sido editado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), UsuarioMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    private void callVolver() {
        Intent intent = new Intent (getApplicationContext(), DetailUsuarioActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0 && surnameText.getText().toString().trim().length()>0 && surname2Text.getText().toString().trim().length()>0 && emailText.getText().toString().trim().length()>0 && preguntaSegText.getText().toString().trim().length()>0 && respuestaSegText.getText().toString().trim().length()>0;
    }
}