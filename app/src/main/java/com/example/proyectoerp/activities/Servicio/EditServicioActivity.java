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
import com.example.proyectoerp.activities.Cliente.ClienteMainActivity;
import com.example.proyectoerp.activities.Cliente.DetailClienteActivity;
import com.example.proyectoerp.dto.ClienteDTO;
import com.example.proyectoerp.dto.ServicioDTO;
import com.example.proyectoerp.model.Cliente;
import com.example.proyectoerp.model.Servicio;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditServicioActivity extends AppCompatActivity {

    Servicio servicio;
    EditText nameText;
    CRUDInterface crudInterface;
    Button editButton, volverButton;
    Long id;
    String nombre;
    CheckBox activoBox;
    Boolean activo, soyAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_servicio);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        id = getIntent().getExtras().getLong("id");
        nombre= getIntent().getExtras().getString("nombre");
        activo = getIntent().getExtras().getBoolean("activo");
        nameText = findViewById(R.id.nameText);
        activoBox = findViewById(R.id.activoBox);
        nameText.setText(nombre);
        editButton = findViewById(R.id.editButton);
        volverButton = findViewById(R.id.volverButton);
        activoBox.setChecked(activo);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVolver();
            }
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editButton.setEnabled(buttonEnabled());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editButton.setEnabled(buttonEnabled());
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicioDTO servicioDto = new ServicioDTO(id, nameText.getText().toString(), activoBox.isChecked());
                edit(servicioDto);
            }
        });
    }

    private void edit (ServicioDTO servicioDto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Servicio> call = crudInterface.editServicio(id, servicioDto);
        call.enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(Call<Servicio> call, Response<Servicio> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Servicio servicio = response.body();
                Toast.makeText(getApplicationContext(), "El servicio " + nombre + " ha sido editado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Servicio> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), DetailServicioActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    private void callVolver() {
        Intent intent = new Intent (getApplicationContext(), DetailServicioActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0;
    }
}