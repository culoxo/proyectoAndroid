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
import com.example.proyectoerp.activities.Usuario.DetailUsuarioActivity;
import com.example.proyectoerp.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends BaseAdapter {

    List<Usuario> usuarios;
    Context context;
    TextView nameText;
    Button viewButton;
    Boolean soyAdmin;

    public UsuarioAdapter(List<Usuario> usuarios, Context context, Boolean soyAdmin) {
        this.usuarios = usuarios;
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
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usuarios.get(position).getUsuarioId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.clientes_list, parent, false);
        }
        nameText = view.findViewById(R.id.nameText);
        nameText.setText(usuarios.get(position).getUsername());

        viewButton = view.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "id  " + usuarios.get(position).getUsuarioId(), Toast.LENGTH_SHORT).show();
                callDetail(usuarios.get(position).getUsuarioId());
            }
        });
        return view;
    }
    private void callDetail(Long id){
        Intent intent = new Intent(context, DetailUsuarioActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
