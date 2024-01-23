package com.example.proyectoerp.activities.Cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class CreateClienteActivity extends AppCompatActivity {

    EditText nameText;
    EditText direccionText;
    EditText emailText;
    EditText telefono;
    Button createButton, volverButton;
    CRUDInterface crudInterface;
    CheckBox activoBox;
    Boolean soyAdmin;
    List<Servicio> servicios = new ArrayList<>();

    // Flag para indicar si se ha dejado de editar el campo de teléfono
    boolean telefonoEditado = false;

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
        volverButton = findViewById(R.id.boton_Volver);

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMain();
            }
        });

        // Utiliza setOnFocusChangeListener para detectar cambios en el foco del campo de teléfono
        telefono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Se ha dejado de editar el campo de teléfono
                    telefonoEditado = true;
                    // Realiza la validación después de dejar de editar
                    validateAndEnableButton();
                }
            }
        });

        nameText.addTextChangedListener(createTextWatcher());
        direccionText.addTextChangedListener(createTextWatcher());
        emailText.addTextChangedListener(createTextWatcher());

        createButton.setEnabled(buttonEnabled());
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar el número de teléfono antes de realizar la llamada al servidor
                validateAndCreate();
            }
        });
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Realiza la validación y actualiza el botón después de la edición completa del texto
                validateAndEnableButton();
            }
        };
    }

    private void validateAndEnableButton() {
        if (telefonoEditado) {
            // Realiza la validación del número de teléfono solo después de dejar de editar
            String telefonoText = telefono.getText().toString().trim();

            // Validación adicional para el número de teléfono
            boolean telefonoValido = telefonoText.isEmpty() || telefonoText.matches("[6|9]\\d{8}"); // Puede estar vacío o debe empezar por 6 o 9 y tener 9 dígitos

            createButton.setEnabled(buttonEnabled());

            if (!telefonoValido) {
                // Deshabilitar el botón si el teléfono no es válido
                createButton.setEnabled(false);
                // Mostrar un AlertDialog informando al usuario sobre el número de teléfono incorrecto
                showTelefonoInvalidoAlert();
            }
        }
    }

    private void showTelefonoInvalidoAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Número de Teléfono Incorrecto")
                .setMessage("El número de teléfono debe empezar por 6 o 9 y tener 9 dígitos.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Puedes realizar alguna acción adicional si es necesario
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void validateAndCreate() {
        // Validar el número de teléfono antes de realizar la llamada al servidor
        String telefonoText = telefono.getText().toString().trim();
        if (!telefonoText.isEmpty() && !telefonoText.matches("[6|9]\\d{8}")) {
            // Mostrar un AlertDialog informando al usuario sobre el número de teléfono incorrecto
            showTelefonoInvalidoAlert();
        } else {
            // Si el número de teléfono es correcto, proceder con la llamada al servidor
            ClienteDTO clienteDto = new ClienteDTO(nameText.getText().toString(), direccionText.getText().toString(), emailText.getText().toString(), telefonoText, activoBox.isChecked(), servicios);
            create(clienteDto);
        }
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
        Intent intent = new Intent(getApplicationContext(), ClienteMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }

    private boolean buttonEnabled() {
        String telefonoText = telefono.getText().toString().trim();

        // Validación adicional para el número de teléfono
        boolean telefonoValido = telefonoText.isEmpty() || telefonoText.matches("[6|9]\\d{8}"); // Puede estar vacío o debe empezar por 6 o 9 y tener 9 dígitos

        return nameText.getText().toString().trim().length() > 0
                && direccionText.getText().toString().trim().length() > 0
                && emailText.getText().toString().trim().length() > 0
                && telefonoValido;
    }
}
