package com.Milton.Consultorio.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.Milton.Consultorio.Activity.PacientesActivity;
import com.Milton.Consultorio.Adapters.UsersAdapter;
import com.Milton.Consultorio.Model.Paciente;
import com.Milton.Consultorio.R;

import java.util.Calendar;

public class Pacientes_Tab1 extends Fragment implements View.OnClickListener {

	EditText edtTxt_Paciente, edtTxt_Conyugue, edtTxt_Telefono, edtTxt_Email, edtTxt_Peso, edtTxt_Ref, edtTxt_Papa, edtTxt_Otro;
	Spinner spn_ECivil, spn_TipoPaciente, spn_Sexo;
	TextView label_fechaNac;
	Button btn_SelectFecha;
	ListView lView_Results;

	private ArrayAdapter<CharSequence> adapSexo;
	private ArrayAdapter<CharSequence> adapEcivil;
	private ArrayAdapter<CharSequence> adapTpaciente;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_pacientes_tab1, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		edtTxt_Paciente = view.findViewById(R.id.Tab1_EditT_NombrePaciente);
		edtTxt_Conyugue = view.findViewById(R.id.Tab1_EditT_Conyugue);
		edtTxt_Email = view.findViewById(R.id.Tab1_EditT_Email);
		edtTxt_Otro = view.findViewById(R.id.Tab1_EditT_Otro);
		edtTxt_Papa = view.findViewById(R.id.Tab1_EditT_PpNicol);
		edtTxt_Peso = view.findViewById(R.id.Tab1_EditT_Peso);
		edtTxt_Ref = view.findViewById(R.id.Tab1_EditT_Referencia);
		edtTxt_Telefono = view.findViewById(R.id.Tab1_EditT_Telefono);

		lView_Results = view.findViewById(R.id.Tab1_Lview_ResultsLista);

		spn_ECivil = view.findViewById(R.id.Tab1_Spn_ECivil);
		spn_Sexo = view.findViewById(R.id.Tab1_Spn_Sexo);
		spn_TipoPaciente = view.findViewById(R.id.Tab1_Spn_TipoPaciente);

		label_fechaNac = view.findViewById(R.id.Tab1_Txt_FechaNac);

		btn_SelectFecha = view.findViewById(R.id.Tab1_Btn_SelectFecha);

		edtTxt_Paciente.setTag("Tab1_EdtTxt_Paciente");
		edtTxt_Conyugue.setTag("Tab1_EdtTxt_Conyugue");
		edtTxt_Email.setTag("Tab1_EdtTxt_Email");
		edtTxt_Otro.setTag("Tab1_EdtTxt_Otro");
		edtTxt_Papa.setTag("Tab1_EdtTxt_Papanicolau");
		edtTxt_Peso.setTag("Tab1_EdtTxt_Peso");
		edtTxt_Ref.setTag("Tab1_EdtTxt_Referencia");
		edtTxt_Telefono.setTag("Tab1_EdtTxt_Telefono");
		btn_SelectFecha.setTag("Tab1_Btn_SelectFecha");
		label_fechaNac.setTag("Tab1_Label_FechaNac");
		spn_Sexo.setTag("Tab1_Spn_Sexo");
		spn_TipoPaciente.setTag("Tab1_Spn_TipoPaciente");
		spn_ECivil.setTag("Tab1_Spn_ECivil");
		lView_Results.setTag("Tab1_LView_ResultsList");

		adapSexo = ArrayAdapter.createFromResource(getActivity(), R.array.SEXO, R.layout.spinner_item_estilo);
		spn_Sexo.setAdapter(adapSexo);

		adapEcivil = ArrayAdapter.createFromResource(getActivity(), R.array.ECIVIL, R.layout.spinner_item_estilo);
		spn_ECivil.setAdapter(adapEcivil);

		adapTpaciente = ArrayAdapter.createFromResource(getActivity(), R.array.TPACIENTE, R.layout.spinner_item_estilo);
		spn_TipoPaciente.setAdapter(adapTpaciente);

		//region Botón fecha onClick Listener
		btn_SelectFecha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int dia = c.get(Calendar.DAY_OF_MONTH);
				int mes = c.get(Calendar.MONTH);
				int ano = c.get(Calendar.YEAR);

				DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
						label_fechaNac.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
					}
				}, ano, mes, dia);
				datePickerDialog.show();
			}
		});
		//endregion

		//region ListView onItemClick listener
		lView_Results.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				((PacientesActivity)getActivity()).PacienteSeleccionado = PacientesActivity.adapter.getItem(position);
               /* Toast.makeText(getApplicationContext(),
                        "El paciente seleccionado es: \n" +((PacientesActivity)getActivity()).PacienteSeleccionado.Nombre,
                        Toast.LENGTH_SHORT).show();*/
				//edtNombre.setText(PacienteSeleccionado.Nombre);
				edtTxt_Paciente.setText(((PacientesActivity)getActivity()).PacienteSeleccionado.NombrePaciente);
				edtTxt_Conyugue.setText(((PacientesActivity)getActivity()).PacienteSeleccionado.NombreConyuge);
				//edtSexo.setText(PacienteSeleccionado.Sexo);

				String compareValue = ((PacientesActivity)getActivity()).PacienteSeleccionado.Sexo;
				//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
				//adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spn_Sexo.setAdapter(adapSexo);
				if (compareValue != null) {
					int spinnerPosition = adapSexo.getPosition(compareValue);
					spn_Sexo.setSelection(spinnerPosition);
				}

				String compareValue2 = ((PacientesActivity)getActivity()).PacienteSeleccionado.EstadoCivil;
				spn_ECivil.setAdapter(adapEcivil);
				if (compareValue2 != null) {
					int spinnerPosition = adapEcivil.getPosition(compareValue2);
					spn_ECivil.setSelection(spinnerPosition);
				}

				String compareValue3 =((PacientesActivity)getActivity()).PacienteSeleccionado.TipoPaciente;
				spn_TipoPaciente.setAdapter(adapTpaciente);
				if (compareValue3 != null) {
					int spinnerPosition = adapTpaciente.getPosition(compareValue3);
					spn_TipoPaciente.setSelection(spinnerPosition);
				}
				label_fechaNac.setText(((PacientesActivity)getActivity()).PacienteSeleccionado.FechaNac);
				edtTxt_Telefono.setText(((PacientesActivity)getActivity()).PacienteSeleccionado.Telefonos);
				edtTxt_Email.setText(((PacientesActivity)getActivity()).PacienteSeleccionado.Email);
				edtTxt_Otro.setText(((PacientesActivity)getActivity()).PacienteSeleccionado.OtrosDatos);
			}
		});
		//endregion
	}

	@Override
	public void onClick(View v) { }
	@Override
	public void onStart() {
		super.onStart();
		((PacientesActivity)getActivity()).loadListView();
	}
	public void LimpiarControles() {
		edtTxt_Paciente.setText("");
		edtTxt_Conyugue.setText("");
		label_fechaNac.setText("");
		edtTxt_Telefono.setText("");
		edtTxt_Email.setText("");
		edtTxt_Otro.setText("");
		edtTxt_Peso.setText("");
		edtTxt_Ref.setText("");
		edtTxt_Papa.setText("");
	}
}
