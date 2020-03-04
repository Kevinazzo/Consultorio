package com.example.nuevo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    Button btn_log, btn_registrar;
    TextView tv_registrar, et_usuario, et_password;
    private Context mContext;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Poner icono en Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Asosiar elemento de la vista(Registrarse)  a la varible
        btn_registrar=(Button)findViewById(R.id.Btn_registrar);

        et_usuario=(EditText)findViewById(R.id.TV_usu);
        et_password=(EditText)findViewById(R.id.TV_pas);
        btn_log=(Button)findViewById(R.id.Btn_iniciar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this,Registro.class);
                MainActivity.this.startActivity(intentReg);
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mContext = getApplicationContext();
                    // Initialize a new RequestQueue instance
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                    HashMap<String, String> params = new HashMap<String,String>();
                    params.put("Usuario",et_usuario.getText().toString());
                    params.put("Contraseña",et_password.getText().toString());

                    JsonObjectRequest PeticionLogin = new JsonObjectRequest("http://adcon.mexpart.com/ADCON/Ingresar", new JSONObject(params), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.has("success")) {
                                    if(response.getBoolean("success")){
                                        Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();

                                        LimpiarControles();

                                        Intent intent = new Intent(MainActivity.this,TabsPacientesActivity.class);
                                        MainActivity.this.startActivity(intent);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "¡¡ERROR!! Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();

                                        LimpiarControles();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "¡¡Fallo al procesar la respuesta del servicio!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "¡¡Fallo de intermitencia de red!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(PeticionLogin);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "¡¡FALLO AL INTENTAR ARMAR EL OBJETO LOGIN!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void LimpiarControles(){
        et_usuario.setText("");
        et_password.setText("");
    }
}
