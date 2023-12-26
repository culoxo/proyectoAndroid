package com.example.proyectoerp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.DialogFragment;
import com.example.proyectoerp.Interfaces.DeleteInterface;

public class DeleteFragment extends DialogFragment {

    private String mensaje;
    private Long id;
    private DeleteInterface deleteInterface;

    public DeleteFragment(String mensaje, Long id, DeleteInterface deleteInterface) {
        this.mensaje = mensaje;
        this.id = id;
        this.deleteInterface = deleteInterface;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mensaje + id + "?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteInterface.delete(id);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Action: ", "cancel" );
                    }
                });
        return builder.create();
    }
}
