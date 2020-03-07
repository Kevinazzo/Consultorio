package com.Milton.Consultorio.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.Milton.Consultorio.Adapters.PageAdapter;
import com.Milton.Consultorio.Adapters.UsersAdapter;
import com.Milton.Consultorio.Fragment.Pacientes_Tab1;
import com.Milton.Consultorio.MiExepcion;
import com.Milton.Consultorio.Model.Paciente;
import com.Milton.Consultorio.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PacientesActivity extends AppCompatActivity implements View.OnClickListener {

	Button btn_Buscar, btn_Nuevo, btn_Actualizar, btn_Eliminar;
	EditText edtTxt_SearchBar;

	ListView lView_Results;

	TabLayout tableLayout;
	ViewPager viewPager;
	PageAdapter pageAdapter;

	Pacientes_Tab1 pacientes_tab1;
	ArrayList<Paciente> PacientesObtenidos;
	public static Paciente PacienteSeleccionado;

	public static UsersAdapter adapter;

	public static RequestQueue queue;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pacientes);
		tableLayout = findViewById(R.id.Layout_Pacientes_TabSelector);
		tableLayout.addTab(tableLayout.newTab().setText("Info Gral"));
		tableLayout.addTab(tableLayout.newTab().setText("Info Factura"));
		tableLayout.addTab(tableLayout.newTab().setText("Club Salud"));
		tableLayout.addTab((tableLayout.newTab().setText("Banco")));
		tableLayout.addTab((tableLayout.newTab().setText("Eco Anatómico")));
		tableLayout.setTabGravity(TabLayout.GRAVITY_FILL);

		viewPager = findViewById(R.id.Layout_Pacientes_TabContainer);
		pageAdapter = new PageAdapter(getSupportFragmentManager(), tableLayout.getTabCount());
		viewPager.setAdapter(pageAdapter);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayout));
		tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			}
		});
		viewPager.setOffscreenPageLimit(4);

		edtTxt_SearchBar = findViewById(R.id.Pacientes_EditTxt_SearchBar);

		btn_Buscar = findViewById(R.id.Pacientes_Btn_Buscar);
		btn_Nuevo = findViewById(R.id.Pacientes_Btn_Nuevo);
		btn_Eliminar = findViewById(R.id.Pacientes_Btn_Eliminar);
		btn_Actualizar = findViewById(R.id.Pacientes_Btn_Actualizar);


		//region Botón buscar onClick listener
		btn_Buscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager inputManager = (InputMethodManager)
						getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				try {
					RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

					String query;
					query = edtTxt_SearchBar.getText().toString();

					HashMap<String, String> param = new HashMap<String, String>();
					param.put("NombrePaciente", query);

					JsonObjectRequest PeticionActualizaPaciente = new JsonObjectRequest(
							"http://adcon.mexpart.com/ADCON/BuscarPaciente", new JSONObject(param), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {

									PacientesObtenidos = new ArrayList<Paciente>();

									// Loop through the array elements
									for (int i = 0; i < response.getJSONArray("success").length(); i++) {
										// Get current json object
										JSONObject PacienteJSON = response.getJSONArray("success").getJSONObject(i);

										Paciente Paciente = new Paciente();

										// Get the current student (json object) data
										Paciente.IdPaciente = PacienteJSON.getString("IdPaciente");
										Paciente.NombrePaciente = PacienteJSON.getString("NombrePaciente");
										Paciente.NombreConyuge = PacienteJSON.getString("NombreConyuge");
										Paciente.Sexo = PacienteJSON.getString("Sexo");
										Paciente.EstadoCivil = PacienteJSON.getString("EstadoCivil");
										Paciente.FechaNac = PacienteJSON.getString("FechaNac");
										Paciente.TipoPaciente = PacienteJSON.getString("TipoPaciente");
										Paciente.Telefonos = PacienteJSON.getString("Telefonos");
										Paciente.Email = PacienteJSON.getString("Email");
										Paciente.OtrosDatos = PacienteJSON.getString("OtrosDatos");

										PacientesObtenidos.add(Paciente);
									}
									adapter = new UsersAdapter(getApplicationContext(), PacientesObtenidos);
									lView_Results.setAdapter(adapter);
								} else {
									Toast.makeText(getApplicationContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), "¡¡FALLO AL RPOCESAR LA RESPUESTA DEL SERVICIO!!", Toast.LENGTH_SHORT).show();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(getApplicationContext(), "¡¡FALLO DE INTERMITENIA DE RED!!", Toast.LENGTH_SHORT).show();
						}
					});
					queue = Volley.newRequestQueue(getApplicationContext());
					queue.add(PeticionActualizaPaciente);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//endregion

		//region Botón nuevo onClick listener
		btn_Nuevo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Paciente PacienteAlta = new Paciente();

					PacienteAlta.IdPaciente = UUID.randomUUID().toString();
					PacienteAlta.NombrePaciente = ((EditText) viewPager.findViewById(R.id.Tab1_EditT_NombrePaciente)).getText().toString();
					PacienteAlta.NombreConyuge = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Conyugue")).getText().toString();
					PacienteAlta.Sexo = ((Spinner) viewPager.findViewWithTag("Tab1_Spn_Sexo")).getSelectedItem().toString();
					PacienteAlta.EstadoCivil = ((Spinner) viewPager.findViewWithTag("Tab1_Spn_ECivil")).getSelectedItem().toString();
					PacienteAlta.TipoPaciente = ((Spinner) viewPager.findViewWithTag("Tab1_Spn_TipoPaciente")).getSelectedItem().toString();
					PacienteAlta.FechaNac = ((TextView) viewPager.findViewWithTag("Tab1_Label_FechaNac")).getText().toString();
					PacienteAlta.Telefonos = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Telefono")).getText().toString();
					PacienteAlta.Email = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Email")).getText().toString();
					PacienteAlta.OtrosDatos = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Otro")).getText().toString();
					PacienteAlta.TarjetaClub = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_NumTarjeta")).getText().toString();
					PacienteAlta.Peso = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Peso")).getText().toString();
					PacienteAlta.PapaNicolaou = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Papanicolau")).getText().toString();
					PacienteAlta.Referencia = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Referencia")).getText().toString();
					PacienteAlta.TitularCS = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_Titular")).getText().toString();
					PacienteAlta.EmpresaCS = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_Empresa")).getText().toString();
					PacienteAlta.VigenciaCS = ((TextView) viewPager.findViewWithTag("Tab3_Label_Vigencia")).getText().toString();
					PacienteAlta.OtrosDatosCS = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_Otro")).getText().toString();
					PacienteAlta.Banco = ((EditText) viewPager.findViewWithTag("Tab4_EdtTxt_Banco")).getText().toString();
					PacienteAlta.NumNominaTarjeta = ((EditText) viewPager.findViewWithTag("Tab4_EdtTxt_NumTarjeta")).getText().toString();
					PacienteAlta.OtrosDatosBanco = ((EditText) viewPager.findViewWithTag("Tab4_EdtTxt_Otro")).getText().toString();
					PacienteAlta.EcoAnatomicoG = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_G")).getText().toString();
					PacienteAlta.EcoAnatomicoP = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_P")).getText().toString();
					PacienteAlta.EcoAnatomicoC = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_C")).getText().toString();
					PacienteAlta.EcoAnatomicoA = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_A")).getText().toString();
					PacienteAlta.FUM = ((TextView) viewPager.findViewWithTag("Tab5_Label_FUM")).getText().toString();
					PacienteAlta.EcoAnatomicoOtrosDatos = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_Otro")).getText().toString();
					PacienteAlta.NombreFactura = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_Paciente")).getText().toString();
					PacienteAlta.EmailFactura = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_Email")).getText().toString();
					PacienteAlta.RFC = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_RFC")).getText().toString();
					PacienteAlta.DomicilioFactura = ((EditText) viewPager.findViewById(R.id.Tab2_EditT_Dom)).getText().toString();
					PacienteAlta.OtrosDatosFactura = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_Otro")).getText().toString();

					Gson PacienteGson = new Gson();
					String PacienteString = PacienteGson.toJson(PacienteAlta);

					JsonObjectRequest AltaPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/AltaPaciente", new JSONObject(PacienteString), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {
									Toast.makeText(getApplicationContext(), response.getString("success"), Toast.LENGTH_SHORT).show();

									limpiarControles();
								} else {
									Toast.makeText(getApplicationContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), "¡¡FALLO AL RPOCESAR LA RESPUESTA DEL SERVICIO!!", Toast.LENGTH_SHORT).show();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(getApplicationContext(), "¡¡FALLO DE INTERMITENIA DE RED!!", Toast.LENGTH_SHORT).show();
						}
					});
					queue = Volley.newRequestQueue(getApplicationContext());
					queue.add(AltaPaciente);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//endregion

		//region Botón actualizar onClick listener
		btn_Actualizar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					PacienteSeleccionado.NombrePaciente = ((EditText) viewPager.findViewById(R.id.Tab1_EditT_NombrePaciente)).getText().toString();
					PacienteSeleccionado.NombreConyuge = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Conyugue")).getText().toString();
					PacienteSeleccionado.Sexo = ((Spinner) viewPager.findViewWithTag("Tab1_Spn_Sexo")).getSelectedItem().toString();
					PacienteSeleccionado.EstadoCivil = ((Spinner) viewPager.findViewWithTag("Tab1_Spn_ECivil")).getSelectedItem().toString();
					PacienteSeleccionado.TipoPaciente = ((Spinner) viewPager.findViewWithTag("Tab1_Spn_TipoPaciente")).getSelectedItem().toString();
					PacienteSeleccionado.FechaNac = ((TextView) viewPager.findViewWithTag("Tab1_Label_FechaNac")).getText().toString();
					PacienteSeleccionado.Telefonos = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Telefono")).getText().toString();
					PacienteSeleccionado.Email = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Email")).getText().toString();
					PacienteSeleccionado.OtrosDatos = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Otro")).getText().toString();
					PacienteSeleccionado.TarjetaClub = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_NumTarjeta")).getText().toString();
					PacienteSeleccionado.Peso = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Peso")).getText().toString();
					PacienteSeleccionado.PapaNicolaou = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Papanicolau")).getText().toString();
					PacienteSeleccionado.Referencia = ((EditText) viewPager.findViewWithTag("Tab1_EdtTxt_Referencia")).getText().toString();
					PacienteSeleccionado.TitularCS = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_Titular")).getText().toString();
					PacienteSeleccionado.EmpresaCS = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_Empresa")).getText().toString();
					PacienteSeleccionado.VigenciaCS = ((TextView) viewPager.findViewWithTag("Tab3_Label_Vigencia")).getText().toString();
					PacienteSeleccionado.OtrosDatosCS = ((EditText) viewPager.findViewWithTag("Tab3_EdtTxt_Otro")).getText().toString();
					PacienteSeleccionado.Banco = ((EditText) viewPager.findViewWithTag("Tab4_EdtTxt_Banco")).getText().toString();
					PacienteSeleccionado.NumNominaTarjeta = ((EditText) viewPager.findViewWithTag("Tab4_EdtTxt_NumTarjeta")).getText().toString();
					PacienteSeleccionado.OtrosDatosBanco = ((EditText) viewPager.findViewWithTag("Tab4_EdtTxt_Otro")).getText().toString();
					PacienteSeleccionado.EcoAnatomicoG = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_G")).getText().toString();
					PacienteSeleccionado.EcoAnatomicoP = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_P")).getText().toString();
					PacienteSeleccionado.EcoAnatomicoC = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_C")).getText().toString();
					PacienteSeleccionado.EcoAnatomicoA = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_A")).getText().toString();
					PacienteSeleccionado.FUM = ((TextView) viewPager.findViewWithTag("Tab5_Label_FUM")).getText().toString();
					PacienteSeleccionado.EcoAnatomicoOtrosDatos = ((EditText) viewPager.findViewWithTag("Tab5_EdtTxt_Otro")).getText().toString();
					PacienteSeleccionado.NombreFactura = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_Paciente")).getText().toString();
					PacienteSeleccionado.EmailFactura = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_Email")).getText().toString();
					PacienteSeleccionado.RFC = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_RFC")).getText().toString();
					PacienteSeleccionado.DomicilioFactura = ((EditText) viewPager.findViewById(R.id.Tab2_EditT_Dom)).getText().toString();
					PacienteSeleccionado.OtrosDatosFactura = ((EditText) viewPager.findViewWithTag("Tab2_EdtTxt_Otro")).getText().toString();

					Gson PacienteGson = new Gson();
					String PacienteString = PacienteGson.toJson(PacienteSeleccionado);

					JsonObjectRequest PeticionActualizaPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/ActualizaPaciente", new JSONObject(PacienteString), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {
									Toast.makeText(getApplicationContext(), response.getString("success"), Toast.LENGTH_SHORT).show();

									limpiarControles();
								} else {
									Toast.makeText(getApplicationContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), "¡¡FALLO AL RPOCESAR LA RESPUESTA DEL SERVICIO!!", Toast.LENGTH_SHORT).show();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(getApplicationContext(), "¡¡FALLO DE INTERMITENIA DE RED!!", Toast.LENGTH_SHORT).show();
						}
					});
					queue = Volley.newRequestQueue(getApplicationContext());
					queue.add(PeticionActualizaPaciente);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//endregion

		//region Botón eliminar onClick listener
		btn_Eliminar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

					if (PacienteSeleccionado == null)
						throw new MiExepcion("FAVOR DE SELECCIONAR EL PACIENTE");

					HashMap<String, String> param = new HashMap<String, String>();

					param.put("IdPaciente", PacienteSeleccionado.IdPaciente);

					JsonObjectRequest EliminarPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/EliminarPaciente", new JSONObject(param), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {
									//LIMPIAR CONTROLES
									limpiarControles();
									//ELIMINAR OBJETO DEL ADAPTER/ARRAYLIST
									PacientesObtenidos.remove(PacienteSeleccionado);
									adapter = new UsersAdapter(getApplicationContext(), PacientesObtenidos);
									lView_Results.setAdapter(adapter);

									Toast.makeText(getApplicationContext(), response.getString("success"), Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getApplicationContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), "¡¡FALLO AL RPOCESAR LA RESPUESTA DEL SERVICIO!!", Toast.LENGTH_SHORT).show();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(getApplicationContext(), "¡¡FALLO DE INTERMITENIA DE RED!!", Toast.LENGTH_SHORT).show();
						}
					});
					queue = Volley.newRequestQueue(getApplicationContext());
					queue.add(EliminarPaciente);
				} catch (MiExepcion x) {
					Toast.makeText(getApplicationContext(), x.getMessage(), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
					//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		//endregion
	}


	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	public void onClick(View v) {

	}

	public void loadListView(){
		lView_Results = viewPager.findViewWithTag("Tab1_LView_ResultsList");
		pacientes_tab1 = (Pacientes_Tab1)getSupportFragmentManager().findFragmentById(R.id.Tab1_Fragment_Parent);
	}

	public void limpiarControles(){
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_NombrePaciente)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_Conyugue)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_Telefono)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_Referencia)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_Peso)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_Email)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_Otro)).setText("");
		((EditText) viewPager.findViewById(R.id.Tab1_EditT_PpNicol)).setText("");
		((TextView) viewPager.findViewById(R.id.Tab1_Txt_FechaNac)).setText("");
	}

}
