package com.example.proyectoerp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proyectoerp.R;
import com.example.proyectoerp.activities.Cliente.ClienteMainActivity;
import com.example.proyectoerp.activities.Servicio.ServicioMainActivity;
import com.example.proyectoerp.activities.Usuario.UsuarioMainActivity;

public class MenuActivity extends AppCompatActivity {

    Button clienteBtn;
    Button servicioBtn;
    Button usuarioBtn;
    Button facturaBtn;
    Boolean admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        admin = getIntent().getBooleanExtra("admin", false);
        clienteBtn = findViewById(R.id.ClienteButton);
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
        if (!admin){
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
        startActivity(intent);
    }
    private void callServicio() {
        Intent intent = new Intent(getApplicationContext(), ServicioMainActivity.class);
        startActivity(intent);
    }
    private void callUsuario() {
        Intent intent = new Intent(getApplicationContext(), UsuarioMainActivity.class);
        startActivity(intent);
    }
    private void callFactura() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}