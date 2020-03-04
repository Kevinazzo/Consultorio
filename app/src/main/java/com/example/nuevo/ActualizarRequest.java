package com.example.nuevo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ActualizarRequest extends StringRequest {

    private static final String ACTUALIZAR_REQUEST_URL ="http://adcon.mexpart.com/ADCON/ActualizaPaciente";

    private Map<String,PacienteORM> params;
    private Map<String,String> paramsReturn;

    //Constructor de la clase - Parametros de los datos que se enviarán
    public ActualizarRequest( PacienteORM paciente, Response.Listener<String>listener){
        //Método de envío - URL donde se encuentra el archivo
        super(Method.POST, ACTUALIZAR_REQUEST_URL, listener, null);
        //Parámetros que van a ser enviados
        params=new HashMap<>();

        params.put("Paciente",paciente);

    }
    /*@Override
    public Map<String, PacienteORM> getParams() {
        return params;
    }*/
}
