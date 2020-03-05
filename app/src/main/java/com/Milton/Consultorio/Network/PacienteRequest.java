package com.Milton.Consultorio.Network;

import com.Milton.Consultorio.Model.Paciente;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacienteRequest extends StringRequest {

    //https://milton1996.000webhostapp.com/
    private static final String PACIENTE_REQUEST_URL ="http://192.168.100.253/PacienteApp.php";
    //private static final String REGISTER_REQUEST_URL="https://milton1996.000webhostapp.com/Register.php";
    //private static final String PACIENTE_REQUEST_URL="http://adcon.mexpart.com/PacienteApp.php";
    private Map<String,String> params;
    //Constructor de la clase - Parametros de los datos que se enviarán
    public PacienteRequest(Paciente PacienteAlta, Response.Listener<String>listener){
        //Método de envío - URL donde se encuentra el archivo
        super(Method.POST, PACIENTE_REQUEST_URL, listener, null);
        //Parámetros que van a ser enviados
        params=new HashMap<>();
        params.put("IdPaciente", UUID.randomUUID().toString());

        params.put("NombrePaciente",PacienteAlta.NombrePaciente);
        params.put("NombreConyuge",PacienteAlta.NombreConyuge);
        params.put("Sexo",PacienteAlta.Sexo);
        params.put("EstadoCivil",PacienteAlta.EstadoCivil);
        params.put("TipoPaciente",PacienteAlta.TipoPaciente);
        params.put("FechaNac",PacienteAlta.FechaNac);
        params.put("Telefonos",PacienteAlta.Telefonos);
        params.put("Email",PacienteAlta.Email);
        params.put("OtrosDatos", PacienteAlta.OtrosDatos);
        params.put("TarjetaClub", PacienteAlta.TarjetaClub);
        params.put("Peso", PacienteAlta.Peso);
        params.put("PapaNicolaou", PacienteAlta.PapaNicolaou);
        params.put("Referencia", PacienteAlta.Referencia);
        params.put("TitularCS", PacienteAlta.TitularCS);
        params.put("EmpresaCS", PacienteAlta.EmpresaCS);
        params.put("VigenciaCS", PacienteAlta.VigenciaCS);
        params.put("OtrosDatosCS", PacienteAlta.OtrosDatosCS);
        params.put("Banco", PacienteAlta.Banco);
        params.put("NumNominaTarjeta", PacienteAlta.NumNominaTarjeta);
        params.put("OtrosDatosBanco", PacienteAlta.OtrosDatosBanco);
        params.put("EcoAnatomicoG", PacienteAlta.EcoAnatomicoG);
        params.put("EcoAnatomicoP", PacienteAlta.EcoAnatomicoP);
        params.put("EcoAnatomicoC", PacienteAlta.EcoAnatomicoC);
        params.put("EcoAnatomicoA", PacienteAlta.EcoAnatomicoA);
        params.put("FUM", PacienteAlta.FUM);
        params.put("EcoAnatomicoOtrosDatos", PacienteAlta.EcoAnatomicoOtrosDatos);
        params.put("NombreFactura", PacienteAlta.NombreFactura);
        params.put("EmailFactura", PacienteAlta.EmailFactura);
        params.put("RFC", PacienteAlta.RFC);
        params.put("DomicilioFactura", PacienteAlta.DomicilioFactura);
        params.put("OtrosDatosFactura", PacienteAlta.OtrosDatosFactura);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}