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
    TextView passwordText;
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
            getAllUsuarios();
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
                        if (usuario.getUsername().toString().equals(usuarioText.getText().toString()) && usuario.getPassword().toString().equals(passwordText.getText().toString())) {
                            callMenu(usuario);
                            return;
                        }else {
                            Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("RecuperarActivity", "Error al obtener usuarios: " + t.getMessage());
            }
        });
    }
}