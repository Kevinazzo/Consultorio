package com.Milton.Consultorio.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Milton.Consultorio.Network.RegistroRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.Milton.Consultorio.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
EditText etnombre, etusuario, etpassword, etemail;
Button btn_registrar, btn_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Asosiar elementos de la vista con las variables
        etnombre=(EditText)findViewById(R.id.EditT_nombre);
        etusuario=(EditText)findViewById(R.id.EditT_usuario);
        etpassword=(EditText)findViewById(R.id.EditT_password);
        etemail=(EditText)findViewById(R.id.EditT_email);
        btn_registrar=(Button)findViewById(R.id.BTN_guardar);
        btn_cancelar=(Button)findViewById(R.id.BTN_cancelar);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                RegistroActivity.this.startActivity(intent);
            }
        });

        //Evento para el momento que se de click en el botón(Guardar)
        btn_registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Definir variables que recibirán los datos de cada elemento
        final String name=etnombre.getText().toString();
        final String username=etusuario.getText().toString();
        final String password=etpassword.getText().toString();
        final String email=etemail.getText().toString();

        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    //Devuelve respuesta si el Registro se hizo correctamente
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        //Ejecutar otro Activity si está correcto - Donde nos encontramos - A donde nos lleva
                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                        RegistroActivity.this.startActivity(intent);

                        Toast.makeText(getApplicationContext(), "¡¡Registro creado exitosamente!!", Toast.LENGTH_SHORT).show();
                    }else{
                        //Mensaje a mostrar en caso que no se haga el Registro
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                        builder.setMessage("Error Registro")
                                .setNegativeButton("Retry",null)
                                .create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegistroRequest registerRequest=new RegistroRequest(name, username, email, password, respoListener);
        //Clase que nos permite interactuar con la libreria Volley - Clase donde nos encontramos
        RequestQueue queue = Volley.newRequestQueue(RegistroActivity.this);
        queue.add(registerRequest);
    }
}
