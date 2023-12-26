package com.example.proyectoerp.activities.Cliente;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.adapters.ClientesAdapter;
import com.example.proyectoerp.model.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteMainActivity extends AppCompatActivity {


    List<Cliente> clientes;
    CRUDInterface crudInterface;
    ListView listView;
    FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_main);
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
        Call<List<Cliente>>call = crudInterface.getAllClientes();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Log.d("Server Response", response.body().toString());
                clientes = response.body();
                // Log para verificar si los objetos Cliente tienen sus identificadores
                for (Cliente cliente : clientes) {
                    Log.d("Cliente ID", String.valueOf(cliente.getClienteId()));
                }
                ClientesAdapter clientesAdapter = new ClientesAdapter(clientes, getApplicationContext());
                listView.setAdapter(clientesAdapter);
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    private void callCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateClienteActivity.class);
        startActivity(intent);

    }
}
