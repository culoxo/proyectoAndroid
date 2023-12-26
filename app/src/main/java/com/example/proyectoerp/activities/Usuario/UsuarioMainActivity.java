package com.example.proyectoerp.activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.adapters.UsuarioAdapter;
import com.example.proyectoerp.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioMainActivity extends AppCompatActivity {


    List<Usuario> usuarios;
    CRUDInterface crudInterface;
    ListView listView;
    FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);
        listView = findViewById(R.id.listView);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreate();
            }
        });
        getAll();
    }

    private void getAll(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Usuario>>call = crudInterface.getAllUsuarios(null, null, null);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Log.d("Server Response", response.toString());
                usuarios = response.body();
                // Log para verificar si los objetos Cliente tienen sus identificadores
                for (Usuario usuario : usuarios) {
                    Log.d("Usuario ID", String.valueOf(usuario.getUsuarioId()));
                }
                UsuarioAdapter usuarioAdapter = new UsuarioAdapter(usuarios, getApplicationContext());
                listView.setAdapter(usuarioAdapter);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    private void callCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateUsuarioActivity.class);
        startActivity(intent);

    }
}