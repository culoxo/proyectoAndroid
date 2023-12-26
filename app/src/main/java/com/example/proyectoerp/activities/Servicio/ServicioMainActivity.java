package com.example.proyectoerp.activities.Servicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.adapters.ServicioAdapter;
import com.example.proyectoerp.model.Servicio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioMainActivity extends AppCompatActivity {


    List<Servicio> servicios;
    CRUDInterface crudInterface;
    ListView listView;
    FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_main);
        listView = findViewById(R.id.listView);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreate();
            }
        });
        getAllServicios();
    }

    private void getAllServicios(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Servicio>> call = crudInterface.getAllServicios();
        call.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Log.d("Server Response", response.body().toString());
                servicios = response.body();
                // Log para verificar si los objetos Cliente tienen sus identificadores
                for (Servicio servicio : servicios) {
                    Log.d("Servicio ID", String.valueOf(servicio.getServicioId()));
                }
                ServicioAdapter servicioAdapter = new ServicioAdapter(servicios, getApplicationContext());
                listView.setAdapter(servicioAdapter);
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    private void callCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateServicioActivity.class);
        startActivity(intent);

    }



}
