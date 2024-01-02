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
import com.example.proyectoerp.R;
import com.example.proyectoerp.dto.ClienteDTO;
import com.example.proyectoerp.model.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditClienteActivity extends AppCompatActivity {

    Cliente cliente;
    EditText nameText;
    EditText direccionText;
    EditText emailText;
    EditText telefono;
    CRUDInterface crudInterface;
    Button editButton, volverButton;
    Long id;
    String nombre;
    String direccion;
    String email;
    String telefonoString;
    CheckBox activoBox;
    Boolean activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cliente);
        id = getIntent().getExtras().getLong("id");
        nombre= getIntent().getExtras().getString("nombre");
        direccion= getIntent().getExtras().getString("direccion");
        email = getIntent().getExtras().getString("email");
        telefonoString = getIntent().getExtras().getString("telefono");
        activo = getIntent().getExtras().getBoolean("activo");
        nameText = findViewById(R.id.nameText);
        direccionText = findViewById(R.id.direccionText);
        activoBox = findViewById(R.id.activoBox);
        nameText.setText(nombre);
        direccionText.setText(direccion);
        emailText = findViewById(R.id.emailText);
        telefono = findViewById(R.id.telefono);
        emailText.setText(email);
        telefono.setText(telefonoString);
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
        direccionText.addTextChangedListener(new TextWatcher() {
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
        emailText.addTextChangedListener(new TextWatcher() {
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
        telefono.addTextChangedListener(new TextWatcher() {
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
                ClienteDTO clienteDto = new ClienteDTO(id, nameText.getText().toString(), direccionText.getText().toString(), emailText.getText().toString(), telefono.getText().toString(), activoBox.isChecked());
                edit(clienteDto);
            }
        });
    }

    private void edit (ClienteDTO clienteDto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Cliente> call = crudInterface.editCliente(id, clienteDto);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                Cliente clientes = response.body();
                Toast.makeText(getApplicationContext(), "El cliente " + nombre + " ha sido editado", Toast.LENGTH_SHORT).show();
                callMain();
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), DetailClienteActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    private void callVolver() {
        Intent intent = new Intent (getApplicationContext(), DetailClienteActivity.class);

        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0 && telefono.getText().toString().trim().length()>0;
    }
}