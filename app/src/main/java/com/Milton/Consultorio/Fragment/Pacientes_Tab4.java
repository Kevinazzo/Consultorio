package com.Milton.Consultorio.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.Milton.Consultorio.R;

public class Pacientes_Tab4 extends Fragment {

	EditText EdtTxt_Banco, EdtTxt_NumTarjeta, EdtTxt_Otro;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pacientes_tab4,container,false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState){
		EdtTxt_Banco = view.findViewById(R.id.Tab4_EditT_Banco);
		EdtTxt_NumTarjeta = view.findViewById(R.id.Tab4_EditT_NumTarjeta);
		EdtTxt_Otro = view.findViewById(R.id.Tab4_EditT_Otro);

		EdtTxt_Banco.setTag("Tab4_EdtTxt_Banco");
		EdtTxt_NumTarjeta.setTag("Tab4_EdtTxt_NumTarjeta");
		EdtTxt_Otro.setTag("Tab4_EdtTxt_Otro");
	}
}
