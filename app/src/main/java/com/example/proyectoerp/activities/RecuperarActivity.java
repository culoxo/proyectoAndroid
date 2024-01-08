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
import com.example.proyectoerp.activities.Usuario.UsuarioMainActivity;
import com.example.proyectoerp.dto.UsuarioDTO;
import com.example.proyectoerp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class RecuperarActivity extends AppCompatActivity {

    Button recuperarConButton, enviarRespuestaButton, volverButton, enviarNuevoPasswordButton;
    EditText usuarioText, respuestaPreguntaSeg, nuevoPassword;
    TextView preguntaSeg, preguntaSegTexto, cambiarPassword;
    Boolean usuarioExiste = false;
    CRUDInterface crudInterface;
    String nombreBuscado;
    String contraseñaAMostrar;
    List<Usuario> usuarios = new ArrayList<>();  // Inicializar la lista
    UsuarioDTO usuarioDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        recuperarConButton = findViewById(R.id.RecuperarConButton);
        usuarioText = findViewById(R.id.UsuarioTex);
        enviarRespuestaButton = findViewById(R.id.EnviarRespuesta);
        respuestaPreguntaSeg = findViewById(R.id.RespuestaPreguntaSeg);
        preguntaSeg = findViewById(R.id.PreguntaSeg);
        preguntaSegTexto = findViewById(R.id.PreguntaSegTexto);
        volverButton = findViewById(R.id.VolverButton);
        enviarNuevoPasswordButton = findViewById(R.id.EnviarNuevoPassword);
        nuevoPassword = findViewById(R.id.nuevoPassword);
        cambiarPassword = findViewById(R.id.cambiarPassword);
        enviarNuevoPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioDto.setPassword(nuevoPassword.getText().toString());
                edit(usuarioDto.getUsuarioId(), usuarioDto);
                Toast.makeText(RecuperarActivity.this, "Contraseña de "  + usuarioDto.getUsername().toString() + " ha cambiado", Toast.LENGTH_SHORT).show();
                callMain();
            }
        });
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
                    if (usuario.getUsername().equals(nombreBuscado)) {
                        usuarioDto = new UsuarioDTO(usuario.getUsuarioId(), usuario.getUsername(),usuario.getName(), usuario.getSurname(),usuario.getSurname2(),
                                usuario.getEmail(), usuario.getPassword(), usuario.getActive(), usuario.getPreguntaSeg(), usuario.getRespuestaSeg(), usuario.getDeleted(), usuario.getAdmin());
                        if (respuestaPreguntaSeg.getText().toString().equals(usuario.getRespuestaSeg())) {
                            cambiarContraseña();
                        } else {
                            preguntaSegTexto.setText("Esa no es la respuesta correcta");
                            Toast.makeText(RecuperarActivity.this, "Esa no es la respuesta correcta", Toast.LENGTH_SHORT).show();
                            volverButton.setVisibility(View.VISIBLE);
                            preguntaSeg.setVisibility(View.GONE);
                            respuestaPreguntaSeg.setVisibility(View.GONE);
                            enviarRespuestaButton.setVisibility(View.GONE);
                            callMain();
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
                        if (usuario.getUsername().equals(nombreBuscado)) {
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
                        Toast.makeText(RecuperarActivity.this, "Este usuario no existe", Toast.LENGTH_SHORT).show();
                        callMain();
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

    private void cambiarContraseña() {
        preguntaSegTexto.setVisibility(View.GONE);
        preguntaSeg.setVisibility(View.GONE);
        respuestaPreguntaSeg.setVisibility(View.GONE);
        enviarRespuestaButton.setVisibility(View.GONE);
        cambiarPassword.setVisibility(View.VISIBLE);
        nuevoPassword.setVisibility(View.VISIBLE);  //Cojo el nuevo password
        enviarNuevoPasswordButton.setVisibility(View.VISIBLE);
    }
    private void edit (Long id, UsuarioDTO usuarioDto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Usuario> call = crudInterface.editUsuario(id, usuarioDto);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Usuario usuarios = response.body();
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    /*private String hashPassword(String password) {
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
    }

    */
    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
