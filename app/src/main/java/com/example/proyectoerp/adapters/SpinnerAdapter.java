package com.example.proyectoerp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.proyectoerp.model.Servicio;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Servicio> {

    public SpinnerAdapter(Context context, List<Servicio> servicios) {
        super(context, 0, servicios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Obtener el servicio en la posición actual
        Servicio servicio = getItem(position);

        // Personalizar la visualización según tus necesidades
        TextView textView = convertView.findViewById(android.R.id.text1);
        if (servicio != null) {
            textView.setText(servicio.getNombre());
        }

        return convertView;
    }
}
