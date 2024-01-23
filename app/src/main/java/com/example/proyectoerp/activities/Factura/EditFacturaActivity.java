package com.example.proyectoerp.activities.Factura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
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

public class EditFacturaActivity extends AppCompatActivity {

        Factura factura;
        CRUDInterface crudInterface;
        Button editButton, volverButton;
        Long id;
        String nombre;
        CheckBox activoBox;
        Boolean activo, soyAdmin;
        TextView nameText;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_factura);
            soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
            id = getIntent().getExtras().getLong("id");
            activo = getIntent().getExtras().getBoolean("activo");
            nameText = findViewById(R.id.nameText);
            activoBox = findViewById(R.id.activoBox);
            nameText.setText(id.toString());
            editButton = findViewById(R.id.editButton);
            volverButton = findViewById(R.id.boton_Volver);
            activoBox.setChecked(activo);
            volverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callVolver();
                }
            });

            editButton.setEnabled(buttonEnabled());
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FacturaDTO facturaDto = new FacturaDTO(id,  activoBox.isChecked());
                    edit(facturaDto);
                }
            });
        }

        private void edit (FacturaDTO facturaDto){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.74:9898/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            crudInterface = retrofit.create(CRUDInterface.class);
            Call<Factura> call = crudInterface.editFactura(id, facturaDto);
            call.enqueue(new Callback<Factura>() {
                @Override
                public void onResponse(Call<Factura> call, Response<Factura> response) {
                    if (!response.isSuccessful()) {
                        System.out.println(response.message());
                        return;
                    }
                    Factura factura = response.body();
                    Toast.makeText(getApplicationContext(), "El factura " + id + " ha sido editado", Toast.LENGTH_SHORT).show();
                    callMain();
                }
                @Override
                public void onFailure(Call<Factura> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        private void callMain() {
            Intent intent = new Intent (getApplicationContext(), DetailFacturaActivity.class);
            intent.putExtra("soyAdmin", soyAdmin);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        private void callVolver() {
            Intent intent = new Intent (getApplicationContext(), DetailFacturaActivity.class);
            intent.putExtra("soyAdmin", soyAdmin);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        private boolean buttonEnabled(){
            return nameText.getText().toString().trim().length()>0;
        }
    }