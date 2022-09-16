package com.example.projectsem5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {
ConstraintLayout backlay,back;
View view;
ImageView obj,cloud1,cloud2,cloud3,ground;
Button start,stop;
TextView score,time,timetxt,scoretxt;
boolean gameOn = false;
Integer s=0,in=0;
Timer timer;
TimerTask timerTask;
String New,n,scr,tym,oldtym,ns;
Double tim=0.0;
DatabaseReference reference= FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Players");
DatabaseReference ref=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Leaderboard");
DatabaseReference limref=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LimitLeader");
DatabaseReference userRef;
FirebaseAuth auth=FirebaseAuth.getInstance();
FirebaseUser user= auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        getSupportActionBar().hide();

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.skyblue));

        String mode = getIntent().getStringExtra("mode");
        String name = getIntent().getStringExtra("name");

        backlay = (ConstraintLayout) findViewById(R.id.backlayout);
        back=(ConstraintLayout) findViewById(R.id.back);
        start = (Button) findViewById(R.id.start);
        obj = (ImageView) findViewById(R.id.obj);
        score=(TextView) findViewById(R.id.score);
        time=(TextView) findViewById(R.id.time);
        timetxt=(TextView) findViewById(R.id.timetxt);
        scoretxt=(TextView) findViewById(R.id.scoretxt);
        stop=(Button) findViewById(R.id.stop);
        cloud1=(ImageView) findViewById(R.id.imageView);
        cloud2=(ImageView) findViewById(R.id.imageView2);
        cloud3=(ImageView) findViewById(R.id.imageView3);
        ground=(ImageView) findViewById(R.id.gameground);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        String th = sp.getString("theme", "");
        if (th.equals("th1") || th.equals(null)) {

            back.setBackgroundColor(getResources().getColor(R.color.skyblue));

        }
        if (th.equals("th2")) {
            back.setBackgroundColor(getResources().getColor(R.color.black));
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
            time.setTextColor(getResources().getColor(R.color.white));
            timetxt.setTextColor(getResources().getColor(R.color.white));
            score.setTextColor(getResources().getColor(R.color.white));
            scoretxt.setTextColor(getResources().getColor(R.color.white));
            ground.setImageDrawable(getResources().getDrawable(R.drawable.groundlildark));
            cloud1.setImageDrawable(getResources().getDrawable(R.drawable.starmin));
            cloud2.setImageDrawable(getResources().getDrawable(R.drawable.starmin));
            cloud3.setImageDrawable(getResources().getDrawable(R.drawable.starmin));
            obj.setImageDrawable(getResources().getDrawable(R.drawable.object_yellow));
            start.setBackgroundColor(getResources().getColor(R.color.white));
            start.setTextColor(getResources().getColor(R.color.black));
            stop.setBackgroundColor(getResources().getColor(R.color.white));
        }

        String Uid=user.getUid();
        userRef=reference.child(Uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n=snapshot.getValue(UserModel.class).getUsername().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        timer = new Timer();

        CountDownTimer ct = new CountDownTimer(31000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                time.setText("!!");
                obj.setVisibility(View.GONE);
                start.setText("TIMES UP!!");
                start.setVisibility(View.VISIBLE);
                popUp();
            }

        };

        backlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s=s-3;
                score.setText(""+s);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameOn == false) {

                    gameOn=true;
                    if (mode.equals("Timed")) {
                        obj.setVisibility(View.VISIBLE);
                        start.setVisibility(View.INVISIBLE);
                        ct.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                time.setTextColor(getResources().getColor(R.color.red));
                                timetxt.setTextColor(getResources().getColor(R.color.red));
                            }
                        }, 15000);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 scr=snapshot.child(n).child("score").getValue(String.class);
                                 New=snapshot.child(n).child("name").getValue(String.class);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if (mode.equals("Limitless")) {
                        obj.setVisibility(View.VISIBLE);
                        start.setVisibility(View.INVISIBLE);
                        stop.setVisibility(View.VISIBLE);
                        startTimer();
                        limref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                scr=snapshot.child(n).child("score").getValue(String.class);
                                New=snapshot.child(n).child("name").getValue(String.class);
                                oldtym=snapshot.child(n).child("time").getValue(String.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                else if(gameOn==true) {

                        String cs = score.getText().toString();

                        if (New != null) {
                            Integer x = new Integer(Integer.parseInt(scr));
                            if (new Integer(Integer.parseInt(cs)) > x) {
                                scr = cs;
                                ns = cs;
                                timerLeader tl = new timerLeader(n, scr);
                                ref.child(n).setValue(tl);
                            } else if (new Integer(Integer.parseInt(cs)) <= x) {
                                scr = x.toString();
                                ns = x.toString();
                                timerLeader tl = new timerLeader(n, scr);
                                ref.child(n).setValue(tl);
                            }
                        } else {
                            timerLeader tl = new timerLeader(n, cs);
                            ref.child(n).setValue(tl);
                        }

                        Intent i = new Intent(GameScreen.this, HomePage.class);
                        startActivity(i);
                        finish();

                }

            }

        });

        obj.setOnClickListener(view -> {

            s=s+5;
            score.setText(""+s);
            targetChangePosition();


        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerTask.cancel();
                obj.setVisibility(View.INVISIBLE);
                tym=time.getText().toString();
                String nt=tym;
                String limsc=score.getText().toString();
                if (New != null) {
                    Integer x = new Integer(Integer.parseInt(scr));
                    if (new Integer(Integer.parseInt(limsc)) > x) {
                        scr = limsc;
                        limitlessLeader ll=new limitlessLeader(n,tym,scr);
                        limref.child(n).setValue(ll);
                    } else if (new Integer(Integer.parseInt(limsc)) <= x) {
                        scr = x.toString();
                        tym=oldtym;
                        limitlessLeader ll=new limitlessLeader(n,tym,scr);
                        limref.child(n).setValue(ll);
                    }
                }
                else {
                    limitlessLeader ll=new limitlessLeader(n,tym,scr);
                    limref.child(n).setValue(ll);
                }

                Intent i = new Intent(GameScreen.this, popUp.class);
                i.putExtra("ps",limsc+" ("+nt+")");
                i.putExtra("ns",scr+" ("+tym+")");
                startActivity(i);
                in+=1;
                if (in==2){
                    startActivity(new Intent(GameScreen.this,HomePage.class));
                    finish();
                }


            }
        });

    }

    private void targetChangePosition(){
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) obj.getLayoutParams();

        int minX = 10;
        int minY = 10;
        int maxX = backlay.getWidth() - obj.getWidth() - 10;
        int maxY = backlay.getHeight() - obj.getHeight() - 10;

        Random r=new Random();
        int ranX = r.nextInt(maxX - minX) + minX;
        int ranY = r.nextInt(maxY - minY) + minY;
        layoutParams.leftMargin=ranX;
        layoutParams.topMargin=ranY;
        obj.requestLayout();
        obj.requestFocus();
    }

    private void startTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tim++;
                        time.setText(getTimerText());
                    }
                });

            }
        };
        timer.schedule(timerTask,0,1000);

    }

    private void popUp(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String ps=score.getText().toString();
                if (New != null) {
                Integer x = new Integer(Integer.parseInt(scr));
                if (new Integer(Integer.parseInt(ps)) > x) {
                    ns = ps;

                } else if (new Integer(Integer.parseInt(ps)) <= x) {
                    ns = x.toString();
                }
                }else {
                    ns=ps;
                }

                Intent i = new Intent(GameScreen.this,popUp.class);
                i.putExtra("ps",ps);
                i.putExtra("ns",ns);
                startActivity(i);
            }
        },1000);
    }

    private String getTimerText() {

        int rounded = (int) Math.round(tim);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600) ;

        return formatTime(seconds,minutes,hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours)+" : "+String.format("%02d",minutes)+" : "+String.format("%02d",seconds);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GameScreen.this,HomePage.class));
        finish();
    }
}