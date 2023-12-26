package com.example.proyectoerp.activities.Servicio;

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
import com.example.proyectoerp.R;
import com.example.proyectoerp.fragments.DeleteFragment;
import com.example.proyectoerp.model.Servicio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailServicioActivity extends AppCompatActivity implements DeleteInterface {

    Servicio servicio;
    TextView activoText;
    TextView nameText;
    TextView idText;
    CRUDInterface crudInterface;
    Button deleteButton;
    Button editButton;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_servicio);
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
        Call<Servicio> call = crudInterface.getOneServicio(id);
        call.enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(Call<Servicio> call, Response<Servicio> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.message());
                    return;
                }
                servicio = response.body();
                idText.setText(String.valueOf(servicio.getServicioId()));
                nameText.setText(servicio.getNombre());
                if (servicio.isActive()) {
                    activoText.setText("Activo");
                } else {
                    activoText.setText("Inactivo");
                }
            }
            @Override
            public void onFailure(Call<Servicio> call, Throwable t) {
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
        Intent intent = new Intent(getApplicationContext(), EditServicioActivity.class);
        intent.putExtra("id", servicio.getServicioId());
        intent.putExtra("nombre", servicio.getNombre());
        intent.putExtra("activo", servicio.isActive());
        startActivity(intent);
    }

    @Override
    public void delete(Long id) {
        crudInterface = getCrudInterface();
        Call<Servicio> call = crudInterface.deleteServicio(id);

        call.enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(Call<Servicio> call, Response<Servicio> response) {

                if(!response.isSuccessful()){
                    System.out.println(response.message());
                    return;
                }
                Servicio servicios = response.body();
                Toast.makeText(getApplicationContext(), servicios.getNombre() + " borrado!!", Toast.LENGTH_SHORT).show();
                callMain();
            }

            @Override
            public void onFailure(Call<Servicio> call, Throwable t) {
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
        Intent intent = new Intent (getApplicationContext(), ServicioMainActivity.class);
        startActivity(intent);
    }

}
