package com.Milton.Consultorio.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Milton.Consultorio.Network.FacturaRequest;
import com.Milton.Consultorio.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Pacientes_Tab2 extends Fragment {

	EditText edtTxt_Pa, edtTxt_Email, edtTxt_RFC, edtTxt_Dom, edtTxt_Otro;
	Button btn_AddFactura;

	RequestQueue queue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pacientes_tab2,container,false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState){
		edtTxt_Email = view.findViewById(R.id.Tab2_EditT_Correo);
		edtTxt_Dom = view.findViewById(R.id.Tab2_EditT_Dom);
		edtTxt_RFC = view.findViewById(R.id.Tab2_EditT_RFC);
		edtTxt_Otro = view.findViewById(R.id.Tab2_EditT_Otro);
		edtTxt_Pa = view.findViewById(R.id.Tab2_EditT_NombrePaciente);
		btn_AddFactura = view.findViewById(R.id.Tab2_Btn_AgregarFactura);

		edtTxt_Pa.setTag("Tab2_EdtTxt_Paciente");
		edtTxt_Email.setTag("Tab2_EdtTxt_Email");
		edtTxt_Dom.setTag("Tab2_EdtTxt_Dom");
		edtTxt_Dom.setTag("Tab2_EdtTxt_RFC");
		edtTxt_Otro.setTag("Tab2_EdtTxt_Otro");
		btn_AddFactura.setTag("Tab2_Btn_AgregarFactura");

		//region Botón agregar factura onClick listener
		btn_AddFactura.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Response.Listener<String> respoListener = new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {

							JSONObject jsonResponse = new JSONObject(response);
							//Devuelve respuesta si el Registro se hizo correctamente
							boolean success = jsonResponse.getBoolean("success");
							if (success) {
								Toast.makeText(getActivity(), "¡¡Factura Registrada Exitosamente!!", Toast.LENGTH_SHORT).show();
							} else {
								//Mensaje a mostrar en caso que no se haga el Registro
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setMessage("Error con Factura")
										.setNegativeButton("Reintentar", null)
										.create().show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				FacturaRequest facturaRequest = new FacturaRequest(respoListener);
				//Clase que nos permite interactuar con la libreria Volley - Clase donde nos encontramos
				queue = Volley.newRequestQueue(getActivity());
				queue.add(facturaRequest);
			}
		});
		//endregion
	}
}
