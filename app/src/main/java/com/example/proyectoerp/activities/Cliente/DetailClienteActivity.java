package com.example.proyectoerp.activities.Cliente;

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
import com.example.proyectoerp.model.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailClienteActivity extends AppCompatActivity implements DeleteInterface {

    TextView activoText;
    TextView emailText;
    TextView telefono;
    TextView direccionText;
    TextView nameText;
    TextView idText;
    CRUDInterface crudInterface;
    Cliente cliente;
    Button deleteButton;
    Button editButton;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cliente);
        direccionText =findViewById(R.id.direccionText);
        emailText = findViewById(R.id.emailText);
        telefono = findViewById(R.id.telefono);
        nameText = findViewById(R.id.nameText);
        idText = findViewById(R.id.idText);
        activoText = findViewById(R.id.activo);
        id = getIntent().getExtras().getLong("id");
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
        Call<Cliente> call = crudInterface.getOneCliente(id);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                cliente = response.body();
                idText.setText(String.valueOf(cliente.getClienteId()));
                direccionText.setText(cliente.getDireccion());
                nameText.setText(cliente.getNombre());
                emailText.setText(cliente.getEmail());
                telefono.setText(cliente.getTelefono());
                if (cliente.isActive()) {
                    activoText.setText("Activo");
                } else {
                    activoText.setText("Inactivo");
                }
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void showDeleteDialog(Long id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DeleteFragment deleteFragment = new DeleteFragment("Borrar cliente ", id, this);
        deleteFragment.show(fragmentManager, "Alert");
    }

    private void callEdit(){
        Intent intent = new Intent(getApplicationContext(), EditClienteActivity.class);
        intent.putExtra("id", cliente.getClienteId());
        intent.putExtra("nombre", cliente.getNombre());
        intent.putExtra("direccion", cliente.getDireccion());
        intent.putExtra("email", cliente.getEmail());
        intent.putExtra("telefono", cliente.getTelefono());
        intent.putExtra("activo", cliente.isActive());
        startActivity(intent);
    }

    @Override
    public void delete(Long id) {
        crudInterface = getCrudInterface();
        Call<Cliente> call = crudInterface.deleteCliente(id);

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Cliente clientes = response.body();
                Toast.makeText(getApplicationContext(), clientes.getNombre() + " borrado!!", Toast.LENGTH_SHORT).show();
                callMain();
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
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
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}