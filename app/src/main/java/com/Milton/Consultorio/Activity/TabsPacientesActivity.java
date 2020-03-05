package com.Milton.Consultorio.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.Milton.Consultorio.Network.FacturaRequest;
import com.Milton.Consultorio.MiExepcion;
import com.Milton.Consultorio.Model.Paciente;
import com.Milton.Consultorio.Fragment.UsersAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.Milton.Consultorio.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class TabsPacientesActivity extends AppCompatActivity implements View.OnClickListener {

	ArrayList<Paciente> PacientesObtenidos;

	EditText edtNombre, edtNombre2, edtConyuge, edtSexo, edtEcivil, edtFecha, edtTelefono, edtEmail;
	TextView edtFecha2, txtVigenciaCS, txtFUM;
	EditText OtrosDatos;

	EditText edtTarjetaClub, edtPeso, edtPapaNicolaou, edtReferencia, edtTitularCS, edtEmpresaCS, edtOtrosDatosCS, edtBanco, edtNumNominaTarjeta, edtOtrosDatosBanco,
			edtEcoAnatomicoG, edtEcoAnatomicoP, edtEcoAnatomicoC, edtEcoAnatomicoA, edtEcoAnatomicoOtrosDatos, edtNombreFactura, edtEmailFactura, edtRFC, edtDomicilioFactura,
			edtOtrosDatosFactura;

	private ArrayAdapter<CharSequence> adapSexo;
	private ArrayAdapter<CharSequence> adapEcivil;
	private ArrayAdapter<CharSequence> adapTpaciente;
	Spinner Sexo, Ecivil, Tpaciente;
	RequestQueue queue;
	private int dia, mes, ano;
	Button btnAgregar, btnActualizar, btnEliminar, btnfecha, btnagregarfactura;

	Button btnbuscar;
	ListView pacientes;

	Paciente PacienteSeleccionado;

	void LimpiarControles(){
		edtNombre2.setText("");
		edtConyuge.setText("");
		edtFecha2.setText("");
		edtTelefono.setText("");
		edtEmail.setText("");
		OtrosDatos.setText("");
		edtPeso.setText("");
		edtReferencia.setText("");
		edtPapaNicolaou.setText("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabs_pacientes);

		edtNombre2=(EditText)findViewById(R.id.EDTnombre2);
		edtConyuge=(EditText)findViewById(R.id.EDTconyugue);
        /*edtSexo=(EditText)findViewById(R.id.EDTsexo);
        edtEcivil=(EditText)findViewById(R.id.EDTcivil);*/
		edtFecha2=(TextView)findViewById(R.id.EDTfecha2);
		edtTelefono=(EditText)findViewById(R.id.EDTtelefono);
		edtEmail=(EditText)findViewById(R.id.EDTemail);
		OtrosDatos=(EditText)findViewById(R.id.EDTotrosdatos);
		Sexo=(Spinner)findViewById(R.id.SPsexo);
		Ecivil=(Spinner)findViewById(R.id.SPecivil);
		Tpaciente=(Spinner)findViewById(R.id.SPtipopaciente);
		edtPeso=(EditText)findViewById(R.id.EDTpeso);
		edtReferencia=(EditText)findViewById(R.id.EDTreferencia);
		edtPapaNicolaou=(EditText)findViewById(R.id.EDTpapanicolau);

		edtNombreFactura=(EditText)findViewById(R.id.EDTnombrefac);
		edtEmailFactura=(EditText)findViewById(R.id.EDTemailfac);
		edtRFC=(EditText)findViewById(R.id.EDTrfcfac);
		edtDomicilioFactura=(EditText)findViewById(R.id.EDTdomiciliofac);
		edtOtrosDatosFactura=(EditText)findViewById(R.id.EDTotrosdatosfac);

		edtTitularCS=(EditText)findViewById(R.id.EDTtitularclub);
		edtTarjetaClub=(EditText)findViewById(R.id.EDTtarjetaclub);
		edtEmpresaCS=(EditText)findViewById(R.id.EDTempresaclub);
		txtVigenciaCS=(TextView) findViewById(R.id.TXTvigenciaclub);
		edtOtrosDatosCS=(EditText)findViewById(R.id.EDTotrosdatosclub);

		edtBanco=(EditText)findViewById(R.id.EDTbanco);
		edtNumNominaTarjeta=(EditText)findViewById(R.id.EDTtarjetabanco);
		edtOtrosDatosBanco=(EditText)findViewById(R.id.EDTotrosdatosbanco);

		edtEcoAnatomicoG=(EditText)findViewById(R.id.EDTg);
		edtEcoAnatomicoP=(EditText)findViewById(R.id.EDTp);
		edtEcoAnatomicoC=(EditText)findViewById(R.id.EDTc);
		edtEcoAnatomicoA=(EditText)findViewById(R.id.EDTa);
		txtFUM=(TextView)findViewById(R.id.TXTfum);
		edtEcoAnatomicoOtrosDatos=(EditText)findViewById(R.id.EDTotrosdatoseco);

		btnbuscar=(Button)findViewById(R.id.BTNbuscar);
		btnActualizar=(Button)findViewById(R.id.BTNactualizar);
		btnEliminar=(Button)findViewById(R.id.BTNeliminar);

		edtNombre=(EditText)findViewById(R.id.EDTnombre);
		pacientes=(ListView)findViewById(R.id.LVpacientes);

		btnagregarfactura = (Button)findViewById(R.id.BTNagregarfactura);

		adapSexo = ArrayAdapter.createFromResource(this, R.array.SEXO, R.layout.spinner_item_estilo);
		Sexo.setAdapter(adapSexo);

		adapEcivil = ArrayAdapter.createFromResource(this, R.array.ECIVIL, R.layout.spinner_item_estilo);
		Ecivil.setAdapter(adapEcivil);

		adapTpaciente = ArrayAdapter.createFromResource(this, R.array.TPACIENTE, R.layout.spinner_item_estilo);
		Tpaciente.setAdapter(adapTpaciente);

		btnAgregar=(Button)findViewById(R.id.BTNagregar);
		btnfecha=(Button)findViewById(R.id.BTNbuscarfecha);

		Resources res = getResources();

		TabHost tabs = (TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();

		TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Info Gral",
				res.getDrawable(R.drawable.ic_person));
		tabs.addTab(spec);

		spec=tabs.newTabSpec("mitab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Info Factura",
				res.getDrawable(R.drawable.ic_content));
		tabs.addTab(spec);

		spec=tabs.newTabSpec("mitab3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Club Salud",
				res.getDrawable(R.drawable.ic_group));
		tabs.addTab(spec);

		spec=tabs.newTabSpec("mitab4");
		spec.setContent(R.id.tab4);
		spec.setIndicator("Banco",
				res.getDrawable(R.drawable.ic_account));
		tabs.addTab(spec);

		spec=tabs.newTabSpec("mitab5");
		spec.setContent(R.id.tab5);
		spec.setIndicator("Eco Anatómico",
				res.getDrawable(R.drawable.ic_contentt));
		tabs.addTab(spec);

		tabs.setCurrentTab(0);

		btnfecha.setOnClickListener(this);

		btnbuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//String request = "[{NombrePaciente:\""+PACIENTE+"\"}]";
					mContext = getApplicationContext();

					// Initialize a new RequestQueue instance
					RequestQueue requestQueue = Volley.newRequestQueue(mContext);

					final String busca;
					busca = edtNombre.getText().toString();


					HashMap<String, String> param = new HashMap<String,String>();

					param.put("NombrePaciente",busca);

					JsonObjectRequest PeticionActualizaPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/BuscarPaciente", new JSONObject(param), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {

									PacientesObtenidos= new ArrayList<Paciente>();

									// Loop through the array elements
									for(int i=0;i<response.getJSONArray("success").length();i++){
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
									//ArrayAdapter<PacienteORM> adaptador = new ArrayAdapter<PacienteORM>(mContext,,PacientesObtenidos

									adapter = new UsersAdapter(mContext,PacientesObtenidos);

									ListView pacientes = (ListView) findViewById(R.id.LVpacientes);
									pacientes.setAdapter(adapter);

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



					queue = Volley.newRequestQueue(TabsPacientesActivity.this);
					queue.add(PeticionActualizaPaciente);



				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
				}
				//buscarPaciente( "http://192.168.100.253/BuscarPacienteApp.php?NombrePaciente="+edtNombre.getText()+"");
				//buscarPaciente( "http://192.168.100.225/BuscarPacienteApp.php", edtNombre.getText().toString());
				//Toast.makeText(getApplicationContext(), "HOLA",Toast.LENGTH_SHORT).show();
			}
		});

		pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PacienteSeleccionado = adapter.getItem(position);
               /* Toast.makeText(getApplicationContext(),
                        "El paciente seleccionado es: \n" + PacienteSeleccionado.Nombre,
                        Toast.LENGTH_SHORT).show();*/
				//edtNombre.setText(PacienteSeleccionado.Nombre);
				edtNombre2.setText(PacienteSeleccionado.NombrePaciente);
				edtConyuge.setText(PacienteSeleccionado.NombreConyuge);
				//edtSexo.setText(PacienteSeleccionado.Sexo);

				String compareValue = PacienteSeleccionado.Sexo;
				//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
				//adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				Sexo.setAdapter(adapSexo);
				if (compareValue != null) {
					int spinnerPosition = adapSexo.getPosition(compareValue);
					Sexo.setSelection(spinnerPosition);
				}

				String compareValue2 = PacienteSeleccionado.EstadoCivil;
				Ecivil.setAdapter(adapEcivil);
				if (compareValue2 != null) {
					int spinnerPosition = adapEcivil.getPosition(compareValue2);
					Ecivil.setSelection(spinnerPosition);
				}

				String compareValue3 = PacienteSeleccionado.TipoPaciente;
				Tpaciente.setAdapter(adapTpaciente);
				if (compareValue3 != null) {
					int spinnerPosition = adapTpaciente.getPosition(compareValue3);
					Tpaciente.setSelection(spinnerPosition);
				}
				edtFecha2.setText(PacienteSeleccionado.FechaNac);
				edtTelefono.setText(PacienteSeleccionado.Telefonos);
				edtEmail.setText(PacienteSeleccionado.Email);
				OtrosDatos.setText(PacienteSeleccionado.OtrosDatos);
			}
		});

		btnAgregar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					Paciente PacienteAlta = new Paciente();

					PacienteAlta.IdPaciente= UUID.randomUUID().toString();
					PacienteAlta.NombrePaciente=edtNombre2.getText().toString();
					PacienteAlta.NombreConyuge=edtConyuge.getText().toString();
					PacienteAlta.Sexo=Sexo.getSelectedItem().toString();
					PacienteAlta.EstadoCivil=Ecivil.getSelectedItem().toString();
					PacienteAlta.TipoPaciente=Tpaciente.getSelectedItem().toString();
					PacienteAlta.FechaNac= edtFecha2.getText().toString();
					PacienteAlta.Telefonos=edtTelefono.getText().toString();
					PacienteAlta.Email=edtEmail.getText().toString();
					PacienteAlta.OtrosDatos=OtrosDatos.getText().toString();
					PacienteAlta.TarjetaClub=edtTarjetaClub.getText().toString();
					PacienteAlta.Peso=edtPeso.getText().toString();
					PacienteAlta.PapaNicolaou=edtPapaNicolaou.getText().toString();
					PacienteAlta.Referencia=edtReferencia.getText().toString();
					PacienteAlta.TitularCS=edtTitularCS.getText().toString();
					PacienteAlta.EmpresaCS=edtEmpresaCS.getText().toString();
					PacienteAlta.VigenciaCS=txtVigenciaCS.getText().toString();
					PacienteAlta.OtrosDatosCS=edtOtrosDatosCS.getText().toString();
					PacienteAlta.Banco=edtBanco.getText().toString();
					PacienteAlta.NumNominaTarjeta=edtNumNominaTarjeta.getText().toString();
					PacienteAlta.OtrosDatosBanco=edtOtrosDatosBanco.getText().toString();
					PacienteAlta.EcoAnatomicoG=edtEcoAnatomicoG.getText().toString();
					PacienteAlta.EcoAnatomicoP=edtEcoAnatomicoP.getText().toString();
					PacienteAlta.EcoAnatomicoC=edtEcoAnatomicoC.getText().toString();
					PacienteAlta.EcoAnatomicoA=edtEcoAnatomicoA.getText().toString();
					PacienteAlta.FUM=txtFUM.getText().toString();
					PacienteAlta.EcoAnatomicoOtrosDatos=edtEcoAnatomicoOtrosDatos.getText().toString();
					PacienteAlta.NombreFactura=edtNombreFactura.getText().toString();
					PacienteAlta.EmailFactura=edtEmailFactura.getText().toString();
					PacienteAlta.RFC=edtRFC.getText().toString();
					PacienteAlta.DomicilioFactura=edtDomicilioFactura.getText().toString();
					PacienteAlta.OtrosDatosFactura=edtOtrosDatosFactura.getText().toString();

					Gson PacienteGson = new Gson();
					String PacienteString = PacienteGson.toJson(PacienteAlta);

					JsonObjectRequest AltaPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/AltaPaciente", new JSONObject(PacienteString), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {
									Toast.makeText(getApplicationContext(), response.getString("success"), Toast.LENGTH_SHORT).show();

									LimpiarControles();
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
					queue = Volley.newRequestQueue(TabsPacientesActivity.this);
					queue.add(AltaPaciente);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnagregarfactura.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Response.Listener<String> respoListener = new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {

							JSONObject jsonResponse = new JSONObject(response);
							//Devuelve respuesta si el Registro se hizo correctamente
							boolean success = jsonResponse.getBoolean("success");
							if(success){
								Toast.makeText(getApplicationContext(), "¡¡Factura Registrada Exitosamente!!", Toast.LENGTH_SHORT).show();
							}else{
								//Mensaje a mostrar en caso que no se haga el Registro
								AlertDialog.Builder builder = new AlertDialog.Builder(TabsPacientesActivity.this);
								builder.setMessage("Error con Factura")
										.setNegativeButton("Reintentar",null)
										.create().show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				FacturaRequest facturaRequest = new FacturaRequest( respoListener);
				//Clase que nos permite interactuar con la libreria Volley - Clase donde nos encontramos
				queue = Volley.newRequestQueue(TabsPacientesActivity.this);
				queue.add(facturaRequest);
			}
		});

		btnActualizar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					PacienteSeleccionado.NombrePaciente = edtNombre2.getText().toString();
					PacienteSeleccionado.NombreConyuge = edtConyuge.getText().toString();
					PacienteSeleccionado.Sexo = Sexo.getSelectedItem().toString();
					PacienteSeleccionado.EstadoCivil = Ecivil.getSelectedItem().toString();
					PacienteSeleccionado.TipoPaciente = Tpaciente.getSelectedItem().toString();
					PacienteSeleccionado.FechaNac = edtFecha2.getText().toString();
					PacienteSeleccionado.Telefonos = edtTelefono.getText().toString();
					PacienteSeleccionado.Email = edtEmail.getText().toString();
					PacienteSeleccionado.OtrosDatos = OtrosDatos.getText().toString();
					PacienteSeleccionado.TarjetaClub = edtTarjetaClub.getText().toString();
					PacienteSeleccionado.Peso = edtPeso.getText().toString();
					PacienteSeleccionado.PapaNicolaou = edtPapaNicolaou.getText().toString();
					PacienteSeleccionado.Referencia = edtReferencia.getText().toString();
					PacienteSeleccionado.TitularCS = edtTitularCS.getText().toString();
					PacienteSeleccionado.EmpresaCS = edtEmpresaCS.getText().toString();
					PacienteSeleccionado.VigenciaCS = txtVigenciaCS.getText().toString();
					PacienteSeleccionado.OtrosDatosCS = edtOtrosDatosCS.getText().toString();
					PacienteSeleccionado.Banco = edtBanco.getText().toString();
					PacienteSeleccionado.NumNominaTarjeta = edtNumNominaTarjeta.getText().toString();
					PacienteSeleccionado.OtrosDatosBanco = edtOtrosDatosBanco.getText().toString();
					PacienteSeleccionado.EcoAnatomicoG = edtEcoAnatomicoG.getText().toString();
					PacienteSeleccionado.EcoAnatomicoP = edtEcoAnatomicoP.getText().toString();
					PacienteSeleccionado.EcoAnatomicoC = edtEcoAnatomicoC.getText().toString();
					PacienteSeleccionado.EcoAnatomicoA = edtEcoAnatomicoA.getText().toString();
					PacienteSeleccionado.FUM = txtFUM.getText().toString();
					PacienteSeleccionado.EcoAnatomicoOtrosDatos = edtEcoAnatomicoOtrosDatos.getText().toString();
					PacienteSeleccionado.NombreFactura = edtNombreFactura.getText().toString();
					PacienteSeleccionado.EmailFactura = edtEmailFactura.getText().toString();
					PacienteSeleccionado.RFC = edtRFC.getText().toString();
					PacienteSeleccionado.DomicilioFactura = edtDomicilioFactura.getText().toString();
					PacienteSeleccionado.OtrosDatosFactura = edtOtrosDatosFactura.getText().toString();

					Gson PacienteGson = new Gson();
					String PacienteString = PacienteGson.toJson(PacienteSeleccionado);

					JsonObjectRequest PeticionActualizaPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/ActualizaPaciente", new JSONObject(PacienteString), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {
									Toast.makeText(getApplicationContext(), response.getString("success"), Toast.LENGTH_SHORT).show();

									LimpiarControles();
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
					queue = Volley.newRequestQueue(TabsPacientesActivity.this);
					queue.add(PeticionActualizaPaciente);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnEliminar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{

					mContext = getApplicationContext();
					RequestQueue requestQueue = Volley.newRequestQueue(mContext);

					if(PacienteSeleccionado == null)
						throw new MiExepcion("FAVOR DE SELECCIONAR EL PACIENTE");

					HashMap<String, String> param = new HashMap<String,String>();

					param.put("IdPaciente",PacienteSeleccionado.IdPaciente);

					JsonObjectRequest EliminarPaciente = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/EliminarPaciente", new JSONObject(param), new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							try {
								if (response.has("success")) {
									//LIMPIAR CONTROLES
									LimpiarControles();
									//ELIMINAR OBJETO DEL ADAPTER/ARRAYLIST
									PacientesObtenidos.remove(PacienteSeleccionado);
									adapter = new UsersAdapter(mContext,PacientesObtenidos);
									ListView pacientes = (ListView) findViewById(R.id.LVpacientes);
									pacientes.setAdapter(adapter);

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
					queue = Volley.newRequestQueue(TabsPacientesActivity.this);
					queue.add(EliminarPaciente);
				} catch(MiExepcion x){
					Toast.makeText(getApplicationContext(), x.getMessage(), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO PACIENTE!", Toast.LENGTH_SHORT).show();
					//Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private Context mContext;
	public UsersAdapter adapter;

	public void buscarPaciente(String URL){
		//String request = "[{NombrePaciente:\""+PACIENTE+"\"}]";
		mContext = getApplicationContext();

		// Initialize a new RequestQueue instance
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
				URL, null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try{

							ArrayList<Paciente> PacientesObtenidos = new ArrayList<Paciente>();

							// Loop through the array elements
							for(int i=0;i<response.length();i++){
								// Get current json object
								JSONObject PacienteJSON = response.getJSONObject(i);

								Paciente Paciente = new Paciente();

								// Get the current student (json object) data
								Paciente.IdPaciente = PacienteJSON.getString("IdPaciente");
								Paciente.NombrePaciente = PacienteJSON.getString("NombrePaciente");
								//Paciente.Nombre2 = PacienteJSON.getString("NombrePaciente");
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
							//ArrayAdapter<PacienteORM> adaptador = new ArrayAdapter<PacienteORM>(mContext,,PacientesObtenidos);
							adapter = new UsersAdapter(mContext,PacientesObtenidos);

							ListView pacientes = (ListView) findViewById(R.id.LVpacientes);
							pacientes.setAdapter(adapter);

						}catch (JSONException e){
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		// Add JsonArrayRequest to the RequestQueue
		requestQueue.add(jsonArrayRequest);

	}

	@Override
	public void onClick(View v) {
		if(v==btnfecha) {
			final Calendar c = Calendar.getInstance();
			dia = c.get(Calendar.DAY_OF_MONTH);
			mes = c.get(Calendar.MONTH);
			ano = c.get(Calendar.YEAR);

			DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
					//edtFecha2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
					edtFecha2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
				}
			}
					, ano, mes, dia);
			datePickerDialog.show();
		}
	}


}