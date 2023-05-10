package com.example.montraqrlector.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.montraqrlector.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LocalBaseDataActivity extends AppCompatActivity {

    File sdcard = new File(Environment.getExternalStorageDirectory(),Environment.DIRECTORY_DOCUMENTS);

    File file = new File(sdcard,"registroBD.txt");

    StringBuilder text = new StringBuilder();

   ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_base_data);

        home = findViewById(R.id.btn_home);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        TextView tv = findViewById(R.id.tvbdtxt);
        if(file.exists()){
            tv.setText(text);
        }
        else {
            tv.setText("No existe base de datos local");
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LocalBaseDataActivity.this,ConsultScreen.class));
            }
        });
    }
}