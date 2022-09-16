package com.example.projectsem5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class popUp extends AppCompatActivity {

    public TextView prevtext,prev,neww,newtext;
    public View view;
    public Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        getSupportActionBar().hide();

        prevtext=(TextView) findViewById(R.id.prevtxt);
        prev=(TextView) findViewById(R.id.prev);
        neww=(TextView) findViewById(R.id.neww);
        newtext=(TextView) findViewById(R.id.newtxt);
        ok=(Button) findViewById(R.id.ok);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        String th = sp.getString("theme", "");
        if (th.equals("th1") || th.equals(null)) {
            view = this.getWindow().getDecorView();
            view.setBackground(getResources().getDrawable(R.drawable.borderpop_white));
            prevtext.setTextColor(getResources().getColor(R.color.black));
            prev.setTextColor(getResources().getColor(R.color.black));
            newtext.setTextColor(getResources().getColor(R.color.black));
            neww.setTextColor(getResources().getColor(R.color.black));
            ok.setBackgroundColor(getResources().getColor(R.color.yellowgreen));
            ok.setTextColor(getResources().getColor(R.color.black));
        }
        if (th.equals("th2")) {
            view = this.getWindow().getDecorView();
            view.setBackground(getResources().getDrawable(R.drawable.borderpop));
            prevtext.setTextColor(getResources().getColor(R.color.white));
            prev.setTextColor(getResources().getColor(R.color.white));
            newtext.setTextColor(getResources().getColor(R.color.white));
            neww.setTextColor(getResources().getColor(R.color.white));
            ok.setBackgroundColor(getResources().getColor(R.color.white));
            ok.setTextColor(getResources().getColor(R.color.black));
        }

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8) ,(int) (height*0.25));

        String ps = getIntent().getStringExtra("ps");
        String ns = getIntent().getStringExtra("ns");
        neww.setText(ps);
        prev.setText(ns);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(popUp.this,HomePage.class));
                finish();
            }
        });
    }
}