package com.example.montraqrlector.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montraqrlector.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.montraqrlector.adapters.PeopleAdapter;
import com.example.montraqrlector.models.IGoogleSheets;
import com.example.montraqrlector.models.People;
import com.example.montraqrlector.utilies.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultScreen extends AppCompatActivity {
    IGoogleSheets iGoogleSheets;
    private List<People> peopleList;
    private RecyclerView recyclerPeople;
    ProgressDialog progressDialog;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        recyclerPeople = findViewById(R.id.recycler_people);

        fab = findViewById(R.id.fab_register);

        peopleList = new ArrayList<>();

        iGoogleSheets = Common.iGSGetMethodClient(Common.BASE_URL);
        loadDataFromGoogleSheets();
    }

    private void loadDataFromGoogleSheets() {
        String pathUrl;
        progressDialog = ProgressDialog.show(ConsultScreen.this,
                "Cargando resultados",
                "Espere por favor",
                true,
                false);

        try {
            pathUrl = "exec?spreadsheetId=" + Common.GOOGLE_SHEET_ID + "&sheet=" + Common.SHEET_NAME;
            iGoogleSheets.getPeople(pathUrl).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {
                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray peopleArray = responseObject.getJSONArray("personas");

                        for (int i = 0; i < peopleArray.length(); i++) {
                            JSONObject object = peopleArray.getJSONObject(i);

                            String id = object.getString("Id");
                            String lectura = object.getString("Lectura");
                            String qrscan = object.getString("QrScan");
                            String name = object.getString("Nombre");
                            String empresa = object.getString("Empresa");
                            String tel1 = object.getString("telefono1");
                            String tel2 = object.getString("telefono2");
                            String correo1 = object.getString("correo1");
                            String correo2 = object.getString("correo2");
                            String informacion = object.getString("Informacion");
                            String comentarios = object.getString("Comentarios");
                            String agrx = object.getString("Agregadox");


                            People people = new People(id,lectura,qrscan,name,empresa,tel1,tel2,correo1,correo2,informacion,comentarios,agrx);
                            peopleList.add(people);

                            setPeopleAdapter(peopleList);
                            progressDialog.dismiss();
                        }

                        int size = peopleList.size();
                        goToRegisterScreen(size);

                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPeopleAdapter(List<People> peopleList) {
        LinearLayoutManager manager = new LinearLayoutManager(ConsultScreen.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        PeopleAdapter peopleAdapter = new PeopleAdapter(ConsultScreen.this, peopleList);
        recyclerPeople.setLayoutManager(manager);
        recyclerPeople.setAdapter(peopleAdapter);
    }

    private void goToRegisterScreen(int size) {
        fab.setOnClickListener(v -> startActivity(new Intent(ConsultScreen.this, RegisterScreen.class)
                .putExtra("count", size)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }

}