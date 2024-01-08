package com.example.proyectoerp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectoerp.MainActivity;
import com.example.proyectoerp.R;
import com.example.proyectoerp.activities.Cliente.ClienteMainActivity;
import com.example.proyectoerp.activities.Factura.FacturaMainActivity;
import com.example.proyectoerp.activities.Servicio.ServicioMainActivity;
import com.example.proyectoerp.activities.Usuario.UsuarioMainActivity;

public class MenuActivity extends AppCompatActivity {

    Button clienteBtn;
    Button servicioBtn;
    Button usuarioBtn;
    Button facturaBtn;
    Button salirBtn;
    Boolean soyAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        soyAdmin = getIntent().getBooleanExtra("soyAdmin", false);
        clienteBtn = findViewById(R.id.ClienteButton);
        salirBtn = findViewById(R.id.SalirButton);
        salirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        clienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCliente();
            }
        });
        servicioBtn = findViewById(R.id.ServicioButton);
        servicioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServicio();
            }
        });
        usuarioBtn = findViewById(R.id.UsuariosButton);
        if (!soyAdmin){
            usuarioBtn.setVisibility(View.GONE);
        }
        usuarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUsuario();
            }
        });
        facturaBtn = findViewById(R.id.FacturaButton);
        facturaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFactura();
            }
        });

    }

    private void callCliente() {
        Intent intent = new Intent(getApplicationContext(), ClienteMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }
    private void callServicio() {
        Intent intent = new Intent(getApplicationContext(), ServicioMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }
    private void callUsuario() {
        Intent intent = new Intent(getApplicationContext(), UsuarioMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }
    private void callFactura() {
        Intent intent = new Intent(getApplicationContext(), FacturaMainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin);
        startActivity(intent);
    }
    private void salir() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("soyAdmin", soyAdmin= false);
        startActivity(intent);
    }
}