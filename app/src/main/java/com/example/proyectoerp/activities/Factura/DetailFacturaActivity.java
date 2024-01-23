package com.example.proyectoerp.activities.Factura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.Interfaces.DeleteInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.fragments.DeleteFragment;
import com.example.proyectoerp.model.Factura;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailFacturaActivity extends AppCompatActivity implements DeleteInterface {

    Factura factura;
    TextView activoText;
    TextView nameText;
    CRUDInterface crudInterface;
    Button deleteButton;
    Button editButton, volverButton;
    Long id;
    Boolean soyAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_factura);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        nameText = findViewById(R.id.nameText);
        activoText = findViewById(R.id.activo);
        id = getIntent().getExtras().getLong("id");
        volverButton = findViewById(R.id.boton_Volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMain();
            }
        });
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEdit();
            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(id);
            }
        });
        getOne(id);
    }

    private void getOne(Long id){
        crudInterface = getCrudInterface();
        Call<Factura> call = crudInterface.getOneFactura(id);
        call.enqueue(new Callback<Factura>() {
            @Override
            public void onResponse(Call<Factura> call, Response<Factura> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                factura = response.body();
                nameText.setText(factura.getFacturaId().toString());
                if (factura.isActive()) {
                    activoText.setText("Pagada");
                } else {
                    activoText.setText("Pendiente");
                }
            }
            @Override
            public void onFailure(Call<Factura> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void showDeleteDialog(Long id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DeleteFragment deleteFragment = new DeleteFragment("Borrar factura ", id, this);
        deleteFragment.show(fragmentManager, "Alert");
    }

    private void callEdit(){
        Intent intent = new Intent(getApplicationContext(), EditFacturaActivity.class);
        intent.putExtra("id", factura.getFacturaId());
        intent.putExtra("activo", factura.isActive());
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }

    @Override
    public void delete(Long id) {
        crudInterface = getCrudInterface();
        Call<Factura> call = crudInterface.deleteFactura(id);

        call.enqueue(new Callback<Factura>() {
            @Override
            public void onResponse(Call<Factura> call, Response<Factura> response) {

                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Factura facturas = response.body();
                Toast.makeText(getApplicationContext(), facturas.getFacturaId().toString() + " borrado!!", Toast.LENGTH_SHORT).show();
                callMain();
            }

            @Override
            public void onFailure(Call<Factura> call, Throwable t) {
            }
        });
    }
    private CRUDInterface getCrudInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        return crudInterface;
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), FacturaMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }

}
