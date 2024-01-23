package com.example.proyectoerp.activities.Cliente;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.R;
import com.example.proyectoerp.dto.ClienteDTO;
import com.example.proyectoerp.model.Cliente;
import com.example.proyectoerp.model.Servicio;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditClienteActivity extends AppCompatActivity {

    Cliente cliente;
    List<Servicio>AllServicios;
    List<Servicio> servicios;
    List<Servicio> ServiciosContratados = new ArrayList<>();
    TextView nameText, direccionText, emailText, telefono;
    CRUDInterface crudInterface;
    Button editButton, volverButton;
    Long id;
    String nombre;
    String direccion;
    String email;
    String telefonoString;
    CheckBox activoBox;
    Boolean activo, soyAdmin;
    Spinner serviciosSpinner;
    TextView serviciosText;
    Servicio ServiciosContratado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cliente);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
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
        volverButton = findViewById(R.id.boton_Volver);
        activoBox.setChecked(activo);
        serviciosText= findViewById(R.id.serviciosText);
        serviciosSpinner= findViewById(R.id.serviciosSpinner);
        getAllServicios();
        getOne(id);

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
                // Crear el objeto ClienteDTO con la información actualizada
                ClienteDTO clienteDto = new ClienteDTO(
                        id,
                        nameText.getText().toString(),
                        direccionText.getText().toString(),
                        emailText.getText().toString(),
                        telefono.getText().toString(),
                        activoBox.isChecked()
                );
                edit(clienteDto);
            }
        });
    }


    private void getOne(Long id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        Call<Cliente> call = crudInterface.getOneCliente(id);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                cliente = response.body();
                //Muestra la lista de servicios
                mostrarServicios(cliente.getServicios());

            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }
    private void callVolver() {
        Intent intent = new Intent (getApplicationContext(), DetailClienteActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private boolean buttonEnabled(){
        return nameText.getText().toString().trim().length()>0 && telefono.getText().toString().trim().length()>0;
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
                if(response.isSuccessful()){
                    AllServicios = new ArrayList<>(response.body());
                    mostrarServiciosDisponibles(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    private void mostrarServiciosDisponibles(List<Servicio> serviciosDisponibles) {
        // Extraer solo los nombres de los servicios
        List<String> nombresServicios = new ArrayList<>();

        // Agregar el elemento por defecto
        nombresServicios.add("Selecciona un servicio para añadir");

        for (Servicio servicio : serviciosDisponibles) {
            nombresServicios.add(servicio.getNombre());
        }

        // Poblar el Spinner con los nombres de los servicios disponibles
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresServicios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviciosSpinner.setAdapter(adapter);

        // Establecer el ítem por defecto como seleccionado
        serviciosSpinner.setSelection(0);
    }

    private void mostrarServicios(List<Servicio> servicios) {
        StringBuilder serviciosStr = new StringBuilder();

        for (Servicio servicio : servicios) {
            serviciosStr.append(servicio.getNombre()).append("\n");


        serviciosText.setText(serviciosStr.toString());
        }
    }
}