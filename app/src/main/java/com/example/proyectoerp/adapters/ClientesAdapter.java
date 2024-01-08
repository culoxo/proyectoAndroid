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
import com.example.proyectoerp.activities.Cliente.DetailClienteActivity;
import com.example.proyectoerp.model.Cliente;

import java.util.List;

public class ClientesAdapter extends BaseAdapter {

List<Cliente> clientes;
Context context;
TextView nameText;
Button viewButton;
Boolean soyAdmin;

    public ClientesAdapter(List<Cliente> clientes, Context context, Boolean soyAdmin) {
        this.clientes = clientes;
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
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clientes.get(position).getClienteId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.clientes_list, parent, false);
        }
        nameText = view.findViewById(R.id.nameText);
        nameText.setText(clientes.get(position).getNombre());
        viewButton = view.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDetail(clientes.get(position).getClienteId());
            }
        });
        return view;
    }
    private void callDetail(Long id){
        Intent intent = new Intent(context, DetailClienteActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
