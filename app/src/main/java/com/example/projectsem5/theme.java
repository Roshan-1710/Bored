package com.example.projectsem5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class theme extends AppCompatActivity {

    Button th1,th2;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        getSupportActionBar().hide();

        sp=getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.ligblue));

        th1=(Button) findViewById(R.id.th1);
        th2=(Button) findViewById(R.id.th2);

        th1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("theme","th1");
                editor.commit();
                Toast.makeText(theme.this, "Theme Changed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(theme.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });

        th2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("theme","th2");
                editor.commit();
                Toast.makeText(theme.this, "Theme Changed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(theme.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(theme.this,HomePage.class);
        startActivity(i);
        finish();
    }
}