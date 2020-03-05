package com.Milton.Consultorio.Network;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FacturaRequest extends StringRequest {

    private static final String FACTURA_REQUEST_URL ="https://www.destiladoslb.com.mx/Admin/Facturacion/TimbresRestantes";

    private Map<String,String> params;
    //Constructor de la clase - Parametros de los datos que se enviarán
    public FacturaRequest( Response.Listener<String>listener){
        //Método de envío - URL donde se encuentra el archivo
        super(Method.POST, FACTURA_REQUEST_URL, listener, null);
        //Parámetros que van a ser enviados
        params=new HashMap<>();

        params.put("RFC","DLB141105N28");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}