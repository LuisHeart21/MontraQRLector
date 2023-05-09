package com.example.montraqrlector.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.montraqrlector.R;
import com.example.montraqrlector.models.IGoogleSheets;
import com.example.montraqrlector.utilies.Common;
import com.example.montraqrlector.utilies.Fecha;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterScreen extends AppCompatActivity {
    EditText etLectura, etqrsan, etName, etEmpresa, etTel1, etTel2, etCorreo1, etCorreo2, etComentarios, etAgrx;

    String[] items_info = {"Cubiscan - Dimensionamiento y Pesaje",
            "Wipotec - OCS pesaje dinámico de alta precisión",
            "Maxload Pro - Simulación y Optimización de cargado de camiones",
            "Identificación - Inspección y Detección",
            "Integración y Automatización",
            "Servicio, Mantenimiento y Asistencia"};
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;

    AppCompatButton btnRegister;

    int lastId;
    String qrLectura, item = "General", total_info_txt, fileName = "registroBD.txt";

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_screen);

        etLectura = findViewById(R.id.et_qrscan);
        etqrsan = findViewById(R.id.et_qrscan);
        etName = findViewById(R.id.et_name);
        etEmpresa = findViewById(R.id.et_empresar);
        etTel1 = findViewById(R.id.et_telf1);
        etTel2=findViewById(R.id.et_telf2);
        etCorreo1=findViewById(R.id.et_correo1);
        etCorreo2=findViewById(R.id.et_correo2);
        etComentarios = findViewById(R.id.et_comentarios);
        etAgrx = findViewById(R.id.et_agrx);
        btnRegister = findViewById(R.id.btn_register);

        autoCompleteText = findViewById(R.id.dsp_info);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items_info);
        autoCompleteText.setAdapter(adapterItems);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();
            }
        });

        lastId = getIntent().getIntExtra("count", 0);
        qrLectura= getIntent().getStringExtra("qr");
        Log.e("datos",lastId+" "+qrLectura);
        etqrsan.setText(qrLectura);


        btnRegister.setOnClickListener(v -> registerPerson());
    }




    private void registerPerson() {


        String name = etName.getText().toString();
        String qrscan = etqrsan.getText().toString();
        String empresa = etEmpresa.getText().toString();
        String tel1 = etTel1.getText().toString();
        String tel2 = etTel2.getText().toString();
        String correo1 = etCorreo1.getText().toString();
        String correo2 = etCorreo2.getText().toString();
        String info = item;
        String comentario = etComentarios.getText().toString();
        String agrx = etAgrx.getText().toString();
        String lecturar = Fecha.obtenerFechaActual("America/Mexico_City") + "  " + Fecha.obtenerHoraActual("America/Mexico_City");

        lecturar = DatoNullo(lecturar);
        name = DatoNullo(name);
        qrscan = DatoNullo(qrscan);
        empresa = DatoNullo(empresa);
        tel1 = DatoNullo(tel1);
        tel2 = DatoNullo(tel2);
        correo1 = DatoNullo(correo1);
        correo2 = DatoNullo(correo2);
        info = DatoNullo(info);
        comentario = DatoNullo(comentario);
        agrx = DatoNullo(agrx);


        String finalLecturar = lecturar;
        String finalQrscan = qrscan;
        String finalName = name;
        String finalEmpresa = empresa;
        String finalTel = tel1;
        String finalTel1 = tel2;
        String finalCorreo = correo1;
        String finalCorreo1 = correo2;
        String finalInfo = info;
        String finalComentario = comentario;
        String finalAgrx = agrx;

        if(!isConnected()){
            //Sin internet
            total_info_txt = "--"+"/"
                    +finalLecturar+"/"
                    +finalQrscan+"/"
                    +finalName+"/"
                    +finalEmpresa+"/"
                    +finalTel+"/"
                    +finalTel1+"/"
                    +finalCorreo+"/"
                    +finalCorreo1+"/"
                    +finalInfo+"/"
                    +finalComentario+"/"
                    +finalAgrx;

            WriteToFile(fileName,total_info_txt);
            startActivity(new Intent(RegisterScreen.this, ConsultScreen.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }else {
            ProgressDialog progressDialog = ProgressDialog.show(this,
                    "Registrando nueva persona",
                    "Espere por favor",
                    true,
                    true);
            //Para registro Internet
            AsyncTask.execute(() -> {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://script.google.com/macros/s/AKfycbzBthnVWXEgfZCs9QceU9kY6Z7AByV7-IFHOR5hcDFIwpcHKX2BvxHSMg0kCZpdZ4R3/")
                            .build();

                    IGoogleSheets iGoogleSheets = retrofit.create(IGoogleSheets.class);

                    int id = lastId + 1;

                    String jsonRequest = "{\n" +
                            "    \"spreadsheet_id\": \"" + Common.GOOGLE_SHEET_ID + "\",\n" +
                            "    \"sheet\":\"" + Common.SHEET_NAME + "\"  ,\n" +
                            "    \"rows\":[\n" +
                            "            [\n" +
                            "            \"" + id + "\",\n" +
                            "            \"" + finalLecturar + "\",\n" +
                            "            \"" + finalQrscan + "\",\n" +
                            "            \"" + finalName + "\",\n" +
                            "            \"" + finalEmpresa + "\",\n" +
                            "            \"" + finalTel + "\",\n" +
                            "            \"" + finalTel1 + "\",\n" +
                            "            \"" + finalCorreo + "\",\n" +
                            "            \"" + finalCorreo1 + "\",\n" +
                            "            \"" + finalInfo + "\",\n" +
                            "            \"" + finalComentario + "\",\n" +
                            "            \"" + finalAgrx + "\"\n" +
                            "            ]\n" +
                            "    ]\n" +
                            "}";

                    Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                    Response<String> response = call.execute();
                    int code = response.code();

                    progressDialog.dismiss();
                    if (code == 200) {
                        startActivity(new Intent(RegisterScreen.this, ConsultScreen.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //Sin internet
            total_info_txt = lastId+1 +"/"
                    +finalLecturar+"/"
                    +finalQrscan+"/"
                    +finalName+"/"
                    +finalEmpresa+"/"
                    +finalTel+"/"
                    +finalTel1+"/"
                    +finalCorreo+"/"
                    +finalCorreo1+"/"
                    +finalInfo+"/"
                    +finalComentario+"/"
                    +finalAgrx;

            WriteToFile(fileName,total_info_txt);
        }
}

    public String DatoNullo(String text){
            if (text.isEmpty() || text == null){
                text = "  ";
            }
            else{
                text = text;
            }
            return text;
    }

    public void WriteToFile( String sFileName, String sBody) {
        Log.e("escribe","minimo");
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(),Environment.DIRECTORY_DOCUMENTS);
            if (!root.exists())
            {
                root.mkdir();
            }
            File gpxfile = new File(root, sFileName);

            Log.e("escribe2","minimo2");

            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append(sBody+"\n\n");
            writer.flush();
            writer.close();
            Toast.makeText(this, "Datos Guardados en Archivo Local", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }

    private boolean isConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!= null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
