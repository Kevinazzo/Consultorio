package com.example.nuevo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

//Clase para hacer la función del Login
public class LoginRequest extends StringRequest {

    public static final String LOGIN_REQUEST_URL="http://adcon.mexpart.com/ADCON/";
    private Map<String,String> params;
    public LoginRequest(String username, String password, Response.Listener<String>listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params=new HashMap<>();

        params.put("username",username);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}