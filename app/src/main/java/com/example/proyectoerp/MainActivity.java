package com.example.proyectoerp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.activities.MenuActivity;
import com.example.proyectoerp.activities.RecuperarActivity;
import com.example.proyectoerp.activities.RegistroActivity;
import com.example.proyectoerp.model.Usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.EditText;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CRUDInterface crudInterface;
    EditText passwordText;
    Button loginButton;
    Button recuperarContraButton, registrarUsuario;
    EditText usuarioText;
    List<Usuario> usuarios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrarUsuario = findViewById(R.id.RegistrarUsuarioButton);
        recuperarContraButton = findViewById(R.id.RecuperarConButton);
        usuarioText = findViewById(R.id.UsuarioText);
        passwordText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.LoginButton);
    loginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String hashedPassword = hashPassword(passwordText.getText().toString());
            getAllUsuarios(hashedPassword);
        }
    });
    recuperarContraButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callRecuperar();
        }
    });
    registrarUsuario.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callRegistrar();
        }
    });
    }
    private void callMenu(Usuario usuario) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("admin", usuario.getAdmin());
        startActivity(intent);
    }
    private void callRecuperar() {
        Intent intent = new Intent(getApplicationContext(), RecuperarActivity.class);
        startActivity(intent);
    }
    private void callRegistrar() {
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(intent);
    }

    private void getAllUsuarios(String hashedPassword) {
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
                    boolean usuarioEncontrado = false;

                    for (Usuario usuario : usuarios) {


                        if (usuario.getUsername().equals(usuarioText.getText().toString()) && usuario.getPassword().equals(hashedPassword)) {
                            // Si encuentras un usuario que coincide, realiza la acción correspondiente
                            callMenu(usuario);
                            usuarioEncontrado = true;
                            break;  // Termina el bucle ya que se encontró un usuario
                        }
                    }

                    if (!usuarioEncontrado) {
                        // Si el bucle termina y no se encontró un usuario, muestra el mensaje
                        Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        usuarioText.getText().clear();
                        passwordText.getText().clear();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("RecuperarActivity", "Error al obtener usuarios: " + t.getMessage());
            }
        });
    }
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
    }
}