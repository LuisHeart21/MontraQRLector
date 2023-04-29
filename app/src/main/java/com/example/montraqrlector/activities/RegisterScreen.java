package com.example.montraqrlector.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.montraqrlector.R;
import com.example.montraqrlector.models.IGoogleSheets;
import com.example.montraqrlector.utilies.Common;
import com.example.montraqrlector.utilies.Fecha;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterScreen extends AppCompatActivity {
    EditText etLectura, etId, etqrsan, etName, etEmpresa, etTel1, etTel2, etCorreo1, etCorreo2, etInformacion, etComentarios, etAgrx;

    AppCompatButton btnRegister;

    int lastId;
    String qrLectura;

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
        etInformacion = findViewById(R.id.et_info);
        etComentarios = findViewById(R.id.et_comentarios);
        etAgrx = findViewById(R.id.et_agrx);
        btnRegister = findViewById(R.id.btn_register);

        lastId = getIntent().getIntExtra("count", 0);
        qrLectura= getIntent().getStringExtra("qr");
        Log.e("datos",lastId+" "+qrLectura);
        etqrsan.setText(qrLectura);


        btnRegister.setOnClickListener(v -> registerPerson());
    }

    private void registerPerson() {
        ProgressDialog progressDialog = ProgressDialog.show(this,
                "Registrando nueva persona",
                "Espere por favor",
                true,
                false);

        String name = etName.getText().toString();
        String qrscan = etqrsan.getText().toString();
        String empresa = etEmpresa.getText().toString();
        String tel1 = etTel1.getText().toString();
        String tel2 = etTel2.getText().toString();
        String correo1 = etCorreo1.getText().toString();
        String correo2 = etCorreo2.getText().toString();
        String info = etInformacion.getText().toString();
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
        comentario = DatoNullo(info);
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
    }

public String DatoNullo(String text){
        if (text.isEmpty()){
            text = "  ";
        }
        else{
            text = text;
        }
        return text;
}
}