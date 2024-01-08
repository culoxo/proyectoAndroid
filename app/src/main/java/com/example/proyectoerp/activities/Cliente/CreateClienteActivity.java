package com.example.proyectoerp.activities.Cliente;

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
import com.example.proyectoerp.MainActivity;
import com.example.proyectoerp.R;
import com.example.proyectoerp.dto.ClienteDTO;
import com.example.proyectoerp.model.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateClienteActivity extends AppCompatActivity {

    EditText nameText;
    EditText direccionText;
    EditText emailText;
    EditText telefono;
    Button createButton, volverButton;
    CRUDInterface crudInterface;
    CheckBox activoBox;
    Boolean soyAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cliente);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        nameText = findViewById(R.id.nameText);
        direccionText = findViewById(R.id.direccionText);
        emailText = findViewById(R.id.emailText);
        telefono = findViewById(R.id.telefono);
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
        direccionText.addTextChangedListener(new TextWatcher() {
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
        emailText.addTextChangedListener(new TextWatcher() {
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
        telefono.addTextChangedListener(new TextWatcher() {
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

                ClienteDTO clienteDto = new ClienteDTO(nameText.getText().toString(), direccionText.getText().toString(), emailText.getText().toString(), telefono.getText().toString(), activoBox.isChecked());
                create(clienteDto);
            }
        });
    }

    private void create(ClienteDTO clienteDto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Cliente> call = crudInterface.createCliente(clienteDto);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                Cliente cliente = response.body();
                Toast.makeText(getApplicationContext(), "El cliente " + cliente.getNombre() + " ha sido creado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), ClienteMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0 && telefono.getText().toString().trim().length()>0;
    }
}