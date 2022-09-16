package com.example.projectsem5;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {
    String ts,ls,Uid,n,oldtym;
    RecyclerView recyclerView,recyclerView1;
    List<timerLeader> list;
    List<limitlessLeader> list1;
    ScoreAdapter adapter;
    LimAdapter limAdapter;
    DatabaseReference reference;
    DatabaseReference playerref= FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Players");
    DatabaseReference limref=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LimitLeader");
    TextView text,switchlead,scoretxt,score;
    View view;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getSupportActionBar().hide();

        switchlead=findViewById(R.id.switchlead);
        scoretxt=findViewById(R.id.scoretxt);
        score=findViewById(R.id.score);
        Uid = user.getUid();
        DatabaseReference UserRef = playerref.child(Uid);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n=snapshot.getValue(UserModel.class).getUsername().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Leaderboard");

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.skyblue));
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        String th = sp.getString("theme", "");
        if (th.equals("th1") || th.equals(null)) {
            view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.skyblue));
            scoretxt.setTextColor(getResources().getColor(R.color.black));
            score.setTextColor(getResources().getColor(R.color.black));
        }
        if (th.equals("th2")) {
            view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.black));
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
            switchlead.setTextColor(getResources().getColor(R.color.white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                switchlead.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
            scoretxt.setTextColor(getResources().getColor(R.color.white));
            score.setTextColor(getResources().getColor(R.color.white));
        }

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ts = snapshot.child(n).child("score").getValue(String.class);
                score.setText(" "+ts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        limref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ls=snapshot.child(n).child("score").getValue(String.class);
                oldtym=snapshot.child(n).child("time").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.leaderboard_recycler);

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        reference.orderByChild("score").limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    timerLeader data = dataSnapshot.getValue(timerLeader.class);
                    list.add(data);
                }
                adapter = new ScoreAdapter(list,Leaderboard.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView1 = findViewById(R.id.limitless_recycler);

        list1 = new ArrayList<>();
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setReverseLayout(true);
        manager1.setStackFromEnd(true);
        recyclerView1.setLayoutManager(manager1);
        recyclerView1.setHasFixedSize(true);

        limref.orderByChild("score").limitToLast(15).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    limitlessLeader data1 = dataSnapshot.getValue(limitlessLeader.class);
                    list1.add(data1);
                }
                limAdapter = new LimAdapter(list1,Leaderboard.this);
                recyclerView1.setAdapter(limAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        switchlead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchlead.getText().toString()=="Timed") {
                    switchlead.setText("Limitless");
                    switchlead.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_timer_off_24,0,0,0);
                    recyclerView.setVisibility(View.INVISIBLE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    if (ls != null) {
                        score.setText(" "+ls+" ("+oldtym+")");
                    }
                    else {
                        score.setText("--");
                    }
                }
                else {
                    switchlead.setText("Timed");
                    switchlead.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24,0,0,0);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView1.setVisibility(View.INVISIBLE);
                    if(ts !=null){
                        score.setText(" "+ts);
                    }
                    else{
                        score.setText("--");
                    }
                }

            }
        });
    }


}