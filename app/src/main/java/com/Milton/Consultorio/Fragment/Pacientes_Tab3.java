package com.Milton.Consultorio.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.Milton.Consultorio.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pacientes_Tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pacientes_Tab3 extends Fragment {

	EditText tab3_Titular, tab3_NumTarjeta, tab3_Empresa, tab3_Otros;
	TextView tab3_Vigencia;
	Button tab3_SelectVigencia;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pacientes_tab3, container, false);

	}

	public void onViewCreated(View view, Bundle savedInstanceState){
		tab3_Titular = view.findViewById(R.id.Tab3_EditT_Titular);
		tab3_NumTarjeta = view.findViewById(R.id.Tab3_EditT_NumTarjeta);
		tab3_Empresa = view.findViewById(R.id.Tab3_EditT_Empresa);
		tab3_Otros = view.findViewById(R.id.Tab3_EditT_Otro);
		tab3_Vigencia = view.findViewById(R.id.Tab3_Txt_Vigencia);
		tab3_SelectVigencia = view.findViewById(R.id.Tab3_Btn_SelectVigencia);

		tab3_Titular.setTag("Tab3_EdtTxt_Titular");
		tab3_NumTarjeta.setTag("Tab3_EdtTxt_NumTarjeta");
		tab3_Empresa.setTag("Tab3_EdtTxt_Empresa");
		tab3_Otros.setTag("Tab3_EdtTxt_Otro");
		tab3_Vigencia.setTag("Tab3_Label_Vigencia");
		tab3_SelectVigencia.setTag("Tab3_Btn_SelectVigencia");
	}

}
