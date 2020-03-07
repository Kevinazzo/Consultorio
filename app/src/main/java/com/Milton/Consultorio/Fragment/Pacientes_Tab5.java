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
 * Use the {@link Pacientes_Tab5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pacientes_Tab5 extends Fragment {

	EditText EdtTxt_G, EdtTxt_P, EdtTxt_C, EdtTxt_A, EdtTxt_Otro;
	TextView FUM;
	Button SelectFUM;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pacientes_tab5, container, false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState){
		EdtTxt_G = view.findViewById(R.id.Tab5_EditT__G_);
		EdtTxt_P = view.findViewById(R.id.Tab5_EditT__P_);
		EdtTxt_C = view.findViewById(R.id.Tab5_EditT__C_);
		EdtTxt_A = view.findViewById(R.id.Tab5_EditT__A_);
		EdtTxt_Otro = view.findViewById(R.id.Tab5_EditT_Otro);
		FUM = view.findViewById(R.id.Tab5_Txt_FUM);
		SelectFUM = view.findViewById(R.id.Tab5_Btn_SelectFUM);

		EdtTxt_G.setTag("Tab5_EdtTxt_G");
		EdtTxt_P.setTag("Tab5_EdtTxt_P");
		EdtTxt_C.setTag("Tab5_EdtTxt_C");
		EdtTxt_A.setTag("Tab5_EdtTxt_A");
		EdtTxt_Otro.setTag("Tab5_EdtTxt_Otro");
		FUM.setTag("Tab5_Label_FUM");
		SelectFUM.setTag("Tab5_Btn_G");

	}
}
