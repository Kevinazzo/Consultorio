package com.example.nuevo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class UsersAdapter extends ArrayAdapter<PacienteORM> {

    public UsersAdapter(Context context, ArrayList<PacienteORM> pacientes) {
        super(context, 0, pacientes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PacienteORM paciente = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pacientes_lista, parent, false);
        }
        // Lookup view for data population

        TextView tv_nombre = (TextView) convertView.findViewById(R.id.tv_nombre);


        // Populate the data into the template view using the data object
        tv_nombre.setText(paciente.NombrePaciente);

        // Return the completed view to render on screen
        return convertView;
    }
}