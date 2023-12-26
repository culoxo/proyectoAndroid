package com.example.proyectoerp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.MainActivity;
import com.example.proyectoerp.R;
import com.example.proyectoerp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class RecuperarActivity extends AppCompatActivity {

    Button recuperarConButton, enviarRespuestaButton, volverButton;
    EditText usuarioText, respuestaPreguntaSeg;
    TextView preguntaSeg, contraseña, preguntaSegTexto;
    Boolean usuarioExiste = false;
    CRUDInterface crudInterface;
    String nombreBuscado;
    String contraseñaAMostrar;
    List<Usuario> usuarios = new ArrayList<>();  // Inicializar la lista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        recuperarConButton = findViewById(R.id.RecuperarConButton);
        usuarioText = findViewById(R.id.UsuarioTex);
        enviarRespuestaButton = findViewById(R.id.EnviarRespuesta);
        respuestaPreguntaSeg = findViewById(R.id.RespuestaPreguntaSeg);
        preguntaSeg = findViewById(R.id.PreguntaSeg);
        contraseña = findViewById(R.id.Contraseña);
        preguntaSegTexto = findViewById(R.id.PreguntaSegTexto);
        volverButton = findViewById(R.id.VolverButton);

        recuperarConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreBuscado = usuarioText.getText().toString();
                getAllUsuarios();
            }
        });

        enviarRespuestaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Usuario usuario : usuarios) {
                    if (usuario.getName().equals(nombreBuscado)) {
                        if (respuestaPreguntaSeg.getText().toString().equals(usuario.getRespuestaSeg())) {
                            contraseñaAMostrar = usuario.getPassword();
                            mostrarContraseña(contraseñaAMostrar);
                        } else {
                            contraseña.setVisibility(View.VISIBLE);
                            contraseña.setText("Esa no es la respuesta correcta");
                            volverButton.setVisibility(View.VISIBLE);
                            preguntaSegTexto.setVisibility(View.GONE);
                            preguntaSeg.setVisibility(View.GONE);
                            respuestaPreguntaSeg.setVisibility(View.GONE);
                            enviarRespuestaButton.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllUsuarios() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Usuario>> call = crudInterface.getAllUsuarios(null, null, null);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    usuarios = response.body();

                    // Itera sobre la lista de usuarios para encontrar coincidencias
                    for (Usuario usuario : usuarios) {
                        if (usuario.getName().equals(nombreBuscado)) {
                            preguntaSegTexto.setVisibility(View.VISIBLE);
                            preguntaSeg.setVisibility(View.VISIBLE);
                            preguntaSeg.setText(usuario.getPreguntaSeg());
                            respuestaPreguntaSeg.setVisibility(View.VISIBLE);
                            enviarRespuestaButton.setVisibility(View.VISIBLE);
                            usuarioText.setVisibility(View.GONE);
                            recuperarConButton.setVisibility(View.GONE);
                            if (usuario.getPassword() != null) {
                                contraseñaAMostrar = usuario.getPassword();
                            }
                            usuarioExiste = true;
                            break; // Puedes salir del bucle si encuentras una coincidencia
                        }
                    }

                    if (!usuarioExiste) {
                        preguntaSeg.setVisibility(View.VISIBLE);
                        preguntaSeg.setText("Este usuario no existe");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("RecuperarActivity", "Error al obtener usuarios: " + t.getMessage());
                Toast.makeText(RecuperarActivity.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarContraseña(String contraseñaAMostrar) {
        // Muestra la contraseña en el TextView
        contraseña.setVisibility(View.VISIBLE);
        contraseña.setText("La contraseña es: " + contraseñaAMostrar);
        volverButton.setVisibility(View.VISIBLE);
        preguntaSegTexto.setVisibility(View.GONE);
        preguntaSeg.setVisibility(View.GONE);
        respuestaPreguntaSeg.setVisibility(View.GONE);
        enviarRespuestaButton.setVisibility(View.GONE);
    }
}
