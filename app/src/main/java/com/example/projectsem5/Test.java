package com.example.projectsem5;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
ListView listview,listview1,lim1,lim2,lim3;
View view;
TextView text,switchlead;
ArrayList<String> userList=new ArrayList<String>();
ArrayList<String> userList1=new ArrayList<>();
ArrayList<String> limlist=new ArrayList<>();
ArrayList<String> limlist1=new ArrayList<>();
ArrayList<String> limlist2=new ArrayList<>();
ArrayAdapter<String> arrayAdapter;
ArrayAdapter<String> arrayAdapter1;
ArrayAdapter<String> limAdapter;
ArrayAdapter<String> limAdapter1;
ArrayAdapter<String> limAdapter2;

DatabaseReference ref= FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Leaderboard");
DatabaseReference limref=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LimitLeader");

@RequiresApi(api = Build.VERSION_CODES.M)
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        listview=findViewById(R.id.listview);
        listview1=findViewById(R.id.listview1);
        lim1=findViewById(R.id.limit1);
        lim2=findViewById(R.id.limit2);
        lim3=findViewById(R.id.limit3);
        switchlead=findViewById(R.id.switchlead);

        getSupportActionBar().hide();
        view=this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.skyblue));

    Window window = this.getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.setStatusBarColor(this.getResources().getColor(R.color.skyblue));
    listview.setVisibility(View.VISIBLE);
    listview1.setVisibility(View.VISIBLE);
    SharedPreferences sp = getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
    String th = sp.getString("theme", "");
    if (th.equals("th1") || th.equals(null)) {
        view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.skyblue));
        arrayAdapter=new ArrayAdapter<>(Test.this,R.layout.test_list,userList);
        listview.setAdapter(arrayAdapter);
        arrayAdapter1=new ArrayAdapter<>(Test.this, R.layout.test_list,userList1);
        listview1.setAdapter(arrayAdapter1);
        limAdapter=new ArrayAdapter<>(Test.this,R.layout.test_lim_list,limlist);
        lim1.setAdapter(limAdapter);
        limAdapter1=new ArrayAdapter<>(Test.this,R.layout.test_lim_list,limlist1);
        lim2.setAdapter(limAdapter1);
        limAdapter2=new ArrayAdapter<>(Test.this,R.layout.test_lim_list,limlist2);
        lim3.setAdapter(limAdapter2);

    }
    if (th.equals("th2")) {
        view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.black));
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        switchlead.setTextColor(getResources().getColor(R.color.white));
        switchlead.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        arrayAdapter=new ArrayAdapter<>(Test.this,R.layout.test_list_white,userList);
        listview.setAdapter(arrayAdapter);
        arrayAdapter1=new ArrayAdapter<>(Test.this, R.layout.test_list_white,userList1);
        listview1.setAdapter(arrayAdapter1);
        limAdapter=new ArrayAdapter<>(Test.this,R.layout.test_lim_list_white,limlist);
        lim1.setAdapter(limAdapter);
        limAdapter1=new ArrayAdapter<>(Test.this,R.layout.test_lim_list_white,limlist1);
        lim2.setAdapter(limAdapter1);
        limAdapter2=new ArrayAdapter<>(Test.this,R.layout.test_lim_list_white,limlist2);
        lim3.setAdapter(limAdapter2);
    }




      //  test.setReverseLayout(true);
       // test.setStackFromEnd(true);
    /*ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(Test.this,R.layout.test_list,userList);
    listview.setAdapter(arrayAdapter);
    ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<>(Test.this, R.layout.test_list,userList1);
    listview1.setAdapter(arrayAdapter1);
    ArrayAdapter<String> limAdapter=new ArrayAdapter<>(Test.this,R.layout.test_lim_list,limlist);
    lim1.setAdapter(limAdapter);
    ArrayAdapter<String> limAdapter1=new ArrayAdapter<>(Test.this,R.layout.test_lim_list,limlist1);
    lim2.setAdapter(limAdapter1);
    ArrayAdapter<String> limAdapter2=new ArrayAdapter<>(Test.this,R.layout.test_lim_list,limlist2);
    lim3.setAdapter(limAdapter2);*/

    Query top=ref.orderByChild("score").limitToLast(15);
    top.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                String userdata = userSnapshot.child("name").getValue(String.class);
               // +" "+userSnapshot.child("score").getValue(String.class)
                String userData1 = userSnapshot.child("score").getValue(String.class);

                userList.add(userdata);
                arrayAdapter.notifyDataSetChanged();
                userList1.add(userData1);
                arrayAdapter1.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    Query toplim=limref.orderByChild("score").limitToLast(15);
    toplim.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot userSnapshot1:snapshot.getChildren()){
                String limData = userSnapshot1.child("name").getValue(String.class);
                String limData1 = userSnapshot1.child("time").getValue(String.class);
                String limData2 = userSnapshot1.child("score").getValue(String.class);

                limlist.add(limData);
                limAdapter.notifyDataSetChanged();
                limlist1.add(limData1);
                limAdapter1.notifyDataSetChanged();
                limlist2.add(limData2);
                limAdapter2.notifyDataSetChanged();
            }
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
                    listview.setVisibility(View.INVISIBLE);
                    listview1.setVisibility(View.INVISIBLE);
                    lim1.setVisibility(View.VISIBLE);
                    lim2.setVisibility(View.VISIBLE);
                    lim3.setVisibility(View.VISIBLE);

                }
                else {

                    switchlead.setText("Timed");
                    switchlead.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_access_time_24,0,0,0);
                    listview.setVisibility(View.VISIBLE);
                    listview1.setVisibility(View.VISIBLE);
                    lim1.setVisibility(View.INVISIBLE);
                    lim2.setVisibility(View.INVISIBLE);
                    lim3.setVisibility(View.INVISIBLE);                }
            }
        });

    }
}