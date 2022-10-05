package com.example.projectsem5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {
    public TextView name, leaderchk, chgmode,welcome;
    public Button play;
    public ImageButton profile, mode;
    public FirebaseAuth auth;
    public FirebaseUser user;
    public String n;
    public ImageView ground,cloud1,cloud2,cloud3;
    View view;
    Window window;
    public DatabaseReference reference = FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Players");
    public DatabaseReference userRef;
    FirebaseAuth.AuthStateListener mAuthListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.skyblue));
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.skyblue));

        leaderchk = (TextView) findViewById(R.id.leaderchk);
        chgmode = (TextView) findViewById(R.id.modechg);
        name = (TextView) findViewById(R.id.name);
        mode = (ImageButton) findViewById(R.id.themeselect);
        profile = (ImageButton) findViewById(R.id.profile);
        play = (Button) findViewById(R.id.startbtn);
        welcome=(TextView) findViewById(R.id.textView9);
        ground=(ImageView) findViewById(R.id.ground);
        cloud1=(ImageView) findViewById(R.id.cloud1);
        cloud2=(ImageView) findViewById(R.id.cloud2);
        cloud3=(ImageView) findViewById(R.id.cloud3);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        String th = sp.getString("theme", "");
        if (th.equals("th1") || th.equals(null)) {
            view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.skyblue));

        }
        if (th.equals("th2")) {
            view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.black));
            play.setBackgroundColor(getResources().getColor(R.color.white));
            welcome.setTextColor(getResources().getColor(R.color.white));
            name.setTextColor(getResources().getColor(R.color.white));
            chgmode.setTextColor(getResources().getColor(R.color.white));
            chgmode.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            leaderchk.setTextColor(getResources().getColor(R.color.white));
            leaderchk.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
            ground.setImageDrawable(getResources().getDrawable(R.drawable.groundlildark));
            cloud1.setImageDrawable(getResources().getDrawable(R.drawable.starmin));
            cloud2.setImageDrawable(getResources().getDrawable(R.drawable.starmin));
            cloud3.setImageDrawable(getResources().getDrawable(R.drawable.starmin));
            profile.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            profile.setImageDrawable(getResources().getDrawable(R.drawable.facedark));
            mode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            mode.setImageDrawable(getResources().getDrawable(R.drawable.mode_edit_black_30));
        }

        auth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String Uid = auth.getUid();
        userRef = reference.child(Uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n = snapshot.getValue(UserModel.class).getUser().toString();
                Toast.makeText(HomePage.this, "Welcome " + n, Toast.LENGTH_SHORT).show();
                name.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        leaderchk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get values from Db
                //change Activity
                Intent i = new Intent(HomePage.this, Leaderboard.class);
                startActivity(i);
            }
        });

        if (sp.getInt("mode",-1)==1){
            chgmode.setText("Timed");
            chgmode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24, 0, 0, 0);
        }
        else if(sp.getInt("mode",-1)==2){
            chgmode.setText("Limitless");
            chgmode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_timer_off_24, 0, 0, 0);
        }
        else if(sp.getInt("mode",-1)==0){
            chgmode.setText("Change Mode");
        }
        chgmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chgmode.getText().toString() == "Change Mode" || chgmode.getText().toString() == "Limitless") {
                    editor.putInt("mode",1);
                    editor.commit();
                    chgmode.setText("Timed");
                    chgmode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24, 0, 0, 0);
                } else {
                    editor.putInt("mode",2);
                    editor.commit();
                    chgmode.setText("Limitless");
                    chgmode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_timer_off_24, 0, 0, 0);
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent j = new Intent(HomePage.this, Profile.class);
                startActivity(j);
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chgmode.getText().toString().equals("Change Mode")) {
                    Toast.makeText(HomePage.this, "Please Select a Game Mode !!", Toast.LENGTH_SHORT).show();
                } else {
                    String m = chgmode.getText().toString();
                    Intent i = new Intent(HomePage.this, GameScreen.class);

                    i.putExtra("mode", m);
                    startActivity(i);
                }
            }
        });

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, theme.class);
                startActivity(i);
                finish();
            }
        });

    }
}