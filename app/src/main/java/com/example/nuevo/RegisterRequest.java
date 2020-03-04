package com.example.nuevo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//Clase para hacer la función del Registro
public class RegisterRequest extends StringRequest {

    //https://milton1996.000webhostapp.com/
    private static final String REGISTER_REQUEST_URL="http://192.168.2.18/RegisterApp.php";
    //private static final String REGISTER_REQUEST_URL="https://milton1996.000webhostapp.com/Register.php";
    //private static final String REGISTER_REQUEST_URL="http://adcon.mexpart.com/RegisterApp.php";
    private Map<String,String> params;
    //Constructor de la clase - Parametros de los datos que se enviarán
    public RegisterRequest(String name, String username, String email, String password, Response.Listener<String> listener){
        //Método de envío - URL donde se encuentra el archivo
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        //Parámetros que van a ser enviados
        params=new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("email",email);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
