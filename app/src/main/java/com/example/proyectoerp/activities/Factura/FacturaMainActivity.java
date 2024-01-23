package com.example.proyectoerp.activities.Factura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.activities.MenuActivity;
import com.example.proyectoerp.adapters.FacturaAdapter;
import com.example.proyectoerp.model.Factura;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacturaMainActivity extends AppCompatActivity {


    List<Factura> facturas;
    CRUDInterface crudInterface;
    ListView listView;
    FloatingActionButton createButton;
    FloatingActionButton backButton;
    Boolean soyAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura_main);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        listView = findViewById(R.id.listView);
        createButton = findViewById(R.id.createButton);
        backButton = findViewById(R.id.volverMenuButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVolver();
            }


        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreate();
            }
        });
        getAllFacturas();
    }

    private void getAllFacturas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<List<Factura>> call = crudInterface.getAllFacturas();
        call.enqueue(new Callback<List<Factura>>() {
            @Override
            public void onResponse(Call<List<Factura>> call, Response<List<Factura>> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }

                facturas = response.body();
                // Log para verificar si los objetos Factura tienen sus identificadores
                for (Factura factura : facturas) {
                    Log.d("Factura ID", String.valueOf(factura.getFacturaId()));
                }
                FacturaAdapter facturaAdapter = new FacturaAdapter(facturas, getApplicationContext(), soyAdmin);
                listView.setAdapter(facturaAdapter);
            }

            @Override
            public void onFailure(Call<List<Factura>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    private void callCreate() {
        Intent intent = new Intent(getApplicationContext(), CreateFacturaActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);

    }
    private void callVolver() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }
}
