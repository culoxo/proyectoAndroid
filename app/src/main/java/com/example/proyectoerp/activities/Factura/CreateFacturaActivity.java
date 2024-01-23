package com.example.proyectoerp.activities.Factura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.dto.FacturaDTO;
import com.example.proyectoerp.model.Factura;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateFacturaActivity extends AppCompatActivity {


    Button createButton, volverButton;
    CRUDInterface crudInterface;
    CheckBox activoBox;
    Boolean soyAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_factura);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        activoBox = findViewById(R.id.activoBox);
        createButton = findViewById(R.id.createButton);
        volverButton = findViewById(R.id.boton_Volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMain();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacturaDTO facturaDto = new FacturaDTO(activoBox.isChecked());
                create(facturaDto);
            }
        });
    }

    private void create(FacturaDTO facturaDto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Factura> call = crudInterface.createFactura(facturaDto);
        call.enqueue(new Callback<Factura>() {
            @Override
            public void onResponse(Call<Factura> call, Response<Factura> response) {
                Toast.makeText(getApplicationContext(), "response.message()", Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Factura factura = response.body();
                Toast.makeText(getApplicationContext(), "La factura ha sido creado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Factura> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), FacturaMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }


}
