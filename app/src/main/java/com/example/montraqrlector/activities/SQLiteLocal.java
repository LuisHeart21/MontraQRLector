package com.example.montraqrlector.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montraqrlector.R;
import com.example.montraqrlector.adapters.AdapterSQLite;

import java.util.ArrayList;

public class SQLiteLocal extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> lectura, qrscan, name, empresa, tel1, tel2, correo1, correo2, info, coment, agrx;
    DBHelper db;
    AdapterSQLite adapterSQLite;

    ImageButton deleteBD, home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_local);
        db = new DBHelper(this);
        lectura = new ArrayList<>();
        qrscan = new ArrayList<>();
        name = new ArrayList<>();
        empresa = new ArrayList<>();
        tel1 = new ArrayList<>();
        tel2 = new ArrayList<>();
        correo1 = new ArrayList<>();
        correo2 = new ArrayList<>();
        info = new ArrayList<>();
        coment = new ArrayList<>();
        agrx = new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerview);
        adapterSQLite = new AdapterSQLite(this,lectura,qrscan,name,empresa,tel1,tel2,correo1,correo2,info, coment,agrx);
        recyclerView.setAdapter(adapterSQLite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        deleteBD = findViewById(R.id.btn_delete);
        home_btn = findViewById(R.id.btn_home);


        deleteBD.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SQLiteLocal.this,ConsultScreen.class);
                Cursor cursor =db.getData();
                if(cursor.getCount()==0){
                    noinfobd();
                    //startActivity(intent);
                }else{
                    AlertDialog.Builder confirmar  = new AlertDialog.Builder(SQLiteLocal.this);
                    confirmar.setTitle("Borrar Base de Datos LOCAL")
                             .setMessage("¿Estas seguro de Borrar TODA la Base de Datos LOCAL?\nEste paso no borrará la Base de Datos de la nube, " +
                                    "solo borrara el contenido almacenado en su dispositivo")
                                    .setCancelable(true)
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            db.deleteALL();
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).setIcon(getResources().getDrawable(R.drawable.baseline_warning_24));

                    AlertDialog titulo = confirmar.create();
                    titulo.show();
                }
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SQLiteLocal.this,ConsultScreen.class);
                startActivity(intent);
            }
        });

    }

    private void displayData() {
        Cursor cursor = db.getData();
        if(cursor.getCount()==0){
            noinfobd();
        }else{
            while (cursor.moveToNext()){
                lectura.add(cursor.getString(0));
                qrscan.add(cursor.getString(1));
                name.add(cursor.getString(2));
                empresa.add(cursor.getString(3));
                tel1.add(cursor.getString(4));
                tel2.add(cursor.getString(5));
                correo1.add(cursor.getString(6));
                correo2.add(cursor.getString(7));
                info.add(cursor.getString(8));
                coment.add(cursor.getString(9));
                agrx.add(cursor.getString(10));

            }
        }
    }
    private void noinfobd(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layoutnoinfobd, (ViewGroup) findViewById(R.id.ll_toast));

        Toast toast = new Toast((getApplicationContext()));
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}