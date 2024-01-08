package com.example.proyectoerp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoerp.R;
import com.example.proyectoerp.activities.Servicio.DetailServicioActivity;
import com.example.proyectoerp.model.Servicio;

import java.util.List;

public class ServicioAdapter extends BaseAdapter {

List<Servicio> servicios;
Context context;
TextView nameText;
Button viewButton;
Boolean soyAdmin;

    public ServicioAdapter(List<Servicio> servicios, Context context, Boolean soyAdmin) {
        this.servicios = servicios;
        this.context = context;
        this.soyAdmin = soyAdmin;
    }

    public Boolean getSoyAdmin() {
        return soyAdmin;
    }

    public void setSoyAdmin(Boolean soyAdmin) {
        this.soyAdmin = soyAdmin;
    }

    @Override
    public int getCount() {
        return servicios.size();
    }

    @Override
    public Object getItem(int position) {
        return servicios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return servicios.get(position).getServicioId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.clientes_list, parent, false);
        }
        nameText = view.findViewById(R.id.nameText);
        nameText.setText(servicios.get(position).getNombre());
        viewButton = view.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {callDetail(servicios.get(position).getServicioId());         }
        });
        return view;
    }
    private void callDetail(Long id){
        Intent intent = new Intent(context, DetailServicioActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
