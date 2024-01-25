package com.example.proyectoerp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.proyectoerp.R;
import com.example.proyectoerp.activities.Factura.DetailFacturaActivity;
import com.example.proyectoerp.model.Factura;

import java.util.List;

public class FacturaAdapter extends BaseAdapter {

    List<Factura> facturas;
    Context context;
    TextView nameText;
    Button viewButton;
    Boolean soyAdmin;

    public FacturaAdapter(List<Factura> facturas, Context context, Boolean soyAdmin) {
        this.facturas = facturas;
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
        return facturas.size();
    }

    @Override
    public Object getItem(int position) {
        return facturas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return facturas.get(position).getFacturaId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.clientes_list, parent, false);
        }
        nameText = view.findViewById(R.id.nameText);
        nameText.setText(facturas.get(position).getFacturaId().toString());
        viewButton = view.findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {callDetail(facturas.get(position).getFacturaId());         }
        });
        return view;
    }
    private void callDetail(Long id){
        Intent intent = new Intent(context, DetailFacturaActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("soyAdmin", soyAdmin);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
