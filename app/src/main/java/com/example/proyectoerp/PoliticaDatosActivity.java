package com.example.proyectoerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectoerp.activities.Usuario.UsuarioMainActivity;

public class PoliticaDatosActivity extends AppCompatActivity {

    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica_datos);
        TextView textViewPolitica = findViewById(R.id.textView10);
        volver = findViewById(R.id.boton_Volver);
volver.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        callMain();
    }
});

    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}