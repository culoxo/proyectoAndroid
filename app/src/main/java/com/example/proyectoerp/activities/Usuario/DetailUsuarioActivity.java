package com.example.proyectoerp.activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyectoerp.Interfaces.CRUDInterface;
import com.example.proyectoerp.Interfaces.DeleteInterface;
import com.example.proyectoerp.MainActivity;
import com.example.proyectoerp.R;
import com.example.proyectoerp.fragments.DeleteFragment;
import com.example.proyectoerp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailUsuarioActivity extends AppCompatActivity implements DeleteInterface {

    TextView activoText, emailText, nameText, idText, usernameText,surnameText, surname2Text, passwordText;
    CRUDInterface crudInterface;
    Usuario usuario;
    Button deleteButton;
    Button editButton;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_usuario);
        usernameText =findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        surnameText = findViewById(R.id.surnameText);
        surname2Text = findViewById(R.id.surname2Text);
        passwordText = findViewById(R.id.passwordText);
        nameText = findViewById(R.id.nameText);
        idText = findViewById(R.id.idText);
        activoText = findViewById(R.id.activo);
        id = getIntent().getExtras().getLong("id");
        Toast.makeText(DetailUsuarioActivity.this, "Id: " + id, Toast.LENGTH_SHORT).show();
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEdit();
            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(id);
            }
        });
        getOne(id);
    }

    private void getOne(Long id){
        crudInterface = getCrudInterface();
        Call<Usuario> call = crudInterface.getOneUsuario(id);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                usuario = response.body();

                idText.setText(String.valueOf(usuario.getUsuarioId()));
                usernameText.setText(usuario.getUsername());
                nameText.setText(usuario.getName());
                emailText.setText(usuario.getEmail());
                surnameText.setText(usuario.getSurname());
                surname2Text.setText(usuario.getSurname2());
                passwordText.setText(usuario.getPassword());
                if (usuario.getActive()) {
                    activoText.setText("Activo");
                } else {
                    activoText.setText("Inactivo");
                }

            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void showDeleteDialog(Long id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DeleteFragment deleteFragment = new DeleteFragment("Borrar usuario ", id, this);
        deleteFragment.show(fragmentManager, "Alert");
    }

    private void callEdit(){
        Intent intent = new Intent(getApplicationContext(), EditUsuarioActivity.class);
        intent.putExtra("id", usuario.getUsuarioId());
        intent.putExtra("nombre", usuario.getName());
        intent.putExtra("username", usuario.getUsername());
        intent.putExtra("email", usuario.getEmail());
        intent.putExtra("apellido1", usuario.getSurname());
        intent.putExtra("apellido2", usuario.getSurname2());
        intent.putExtra("password", usuario.getPassword());
        intent.putExtra("preguntaSeg", usuario.getPreguntaSeg());
        intent.putExtra("respuestaSeg", usuario.getRespuestaSeg());
        intent.putExtra("activo", usuario.getActive());
        startActivity(intent);
    }

    @Override
    public void delete(Long id) {
        crudInterface = getCrudInterface();
        Call<Usuario> call = crudInterface.deleteUsuario(id);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Usuario usuarios = response.body();
                Toast.makeText(getApplicationContext(), usuarios.getUsername() + " borrado!!", Toast.LENGTH_SHORT).show();
                callMain();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "t.getMessage()", Toast.LENGTH_LONG).show();

            }
        });
    }
    private CRUDInterface getCrudInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.74:9898/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(CRUDInterface.class);
        return crudInterface;
    }

    private void callMain() {
        Intent intent = new Intent (getApplicationContext(), UsuarioMainActivity.class);
        startActivity(intent);
    }

}