package com.example.proyectoerp.activities.Servicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.dto.ServicioDTO;
import com.example.proyectoerp.model.Servicio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateServicioActivity extends AppCompatActivity {

    EditText nameText;
    Button createButton, volverButton;
    CRUDInterface crudInterface;
    CheckBox activoBox;
    Boolean soyAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_servicio);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        nameText = findViewById(R.id.nameText);
        activoBox = findViewById(R.id.activoBox);
        createButton = findViewById(R.id.createButton);
        volverButton = findViewById(R.id.volverButton);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMain();
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        createButton.setEnabled(buttonEnabled());
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicioDTO servicioDto = new ServicioDTO(nameText.getText().toString(), activoBox.isChecked());
                create(servicioDto);
            }
        });
    }

    private void create(ServicioDTO servicioDto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Servicio> call = crudInterface.createServicio(servicioDto);
        call.enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(Call<Servicio> call, Response<Servicio> response) {
                Toast.makeText(getApplicationContext(), "response.message()", Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Servicio servicio = response.body();
                Toast.makeText(getApplicationContext(), "El servicio " + servicio.getNombre() + " ha sido creado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Servicio> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), ServicioMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0;
    }
}
