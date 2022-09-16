package com.example.projectsem5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
public ImageView fnameEdtBtn,lnameEdtBtn,unameEdtBtn,emailEdtBtn,passEdtBtn,fnameChk,lnameChk,unameChk,emailChk,passChk,fnameclose,lnameclose,unameclose,emailclose,passclose;
public TextView firstname,lastname,username,email,password,textview1,textview2,textview3,textview4,textview5;
public EditText fnameedt,lnameedt,unameedt,emailedt,passedt;
public Button update,delete,show,signout;
public DatabaseReference ref=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Leaderboard");
public DatabaseReference limref=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LimitLeader");
public DatabaseReference reference = FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Players");
public DatabaseReference userRef;
public String unameFromDb,passwordFromDb,emailFromDb,fnameFromDb,lnameFromDb,n,f,l,p,e;
public FirebaseAuth auth;
public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));

        fnameEdtBtn=(ImageView) findViewById(R.id.imageView3);
        lnameEdtBtn=(ImageView) findViewById(R.id.lnameedtbtn);
        unameEdtBtn=(ImageView) findViewById(R.id.unameedtbtn);
        emailEdtBtn=(ImageView) findViewById(R.id.emailedtbtn);
        passEdtBtn=(ImageView) findViewById(R.id.passedtbtn);
        fnameChk=(ImageView) findViewById(R.id.fnamechk);
        lnameChk=(ImageView) findViewById(R.id.lnamechk);
        unameChk=(ImageView) findViewById(R.id.unamechk);
        emailChk=(ImageView) findViewById(R.id.emailchk);
        passChk=(ImageView) findViewById(R.id.passchk);
        fnameclose=(ImageView) findViewById(R.id.fnameclose);
        lnameclose=(ImageView) findViewById(R.id.lnameclose);
        unameclose=(ImageView) findViewById(R.id.unameclose);
        emailclose=(ImageView) findViewById(R.id.emailclose);
        passclose=(ImageView) findViewById(R.id.passclose);

        textview1=(TextView) findViewById(R.id.textView12);
        textview2=(TextView) findViewById(R.id.textView13);
        textview3=(TextView) findViewById(R.id.textView15);
        textview4=(TextView) findViewById(R.id.textView16);
        textview5=(TextView) findViewById(R.id.textView14);
        firstname=(TextView) findViewById(R.id.textView11);
        lastname=(TextView) findViewById(R.id.lastname);
        username=(TextView) findViewById(R.id.username);
        email=(TextView) findViewById(R.id.email);
        password=(TextView) findViewById(R.id.password);

        fnameedt=(EditText) findViewById(R.id.fnameedt);
        lnameedt=(EditText) findViewById(R.id.lnameedt);
        unameedt=(EditText) findViewById(R.id.unameedt);
        emailedt=(EditText) findViewById(R.id.emailedt);
        passedt=(EditText) findViewById(R.id.passwordedt);

        update=(Button) findViewById(R.id.update);
        delete=(Button) findViewById(R.id.delete);
        show=(Button) findViewById(R.id.show);
        signout=(Button) findViewById(R.id.signout);

        textview1.setVisibility(View.INVISIBLE);
        textview2.setVisibility(View.INVISIBLE);
        textview3.setVisibility(View.INVISIBLE);
        textview4.setVisibility(View.INVISIBLE);
        firstname.setVisibility(View.INVISIBLE);
        lastname.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        fnameEdtBtn.setVisibility(View.INVISIBLE);
        lnameEdtBtn.setVisibility(View.INVISIBLE);
        emailEdtBtn.setVisibility(View.INVISIBLE);
        passEdtBtn.setVisibility(View.INVISIBLE);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        String th = sp.getString("theme", "");
        if (th.equals("th1") || th.equals(null)) {
            view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.white));

        }
        if (th.equals("th2")) {
            view = this.getWindow().getDecorView();
            view.setBackgroundColor(getResources().getColor(R.color.black));
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
            textview1.setTextColor(getResources().getColor(R.color.white));
            textview2.setTextColor(getResources().getColor(R.color.white));
            textview3.setTextColor(getResources().getColor(R.color.white));
            textview4.setTextColor(getResources().getColor(R.color.white));
            textview5.setTextColor(getResources().getColor(R.color.white));
            firstname.setTextColor(getResources().getColor(R.color.white));
            lastname.setTextColor(getResources().getColor(R.color.white));
            username.setTextColor(getResources().getColor(R.color.white));
            email.setTextColor(getResources().getColor(R.color.white));
            password.setTextColor(getResources().getColor(R.color.white));
            fnameedt.setTextColor(getResources().getColor(R.color.white));
            lnameedt.setTextColor(getResources().getColor(R.color.white));
            unameedt.setTextColor(getResources().getColor(R.color.white));
            emailedt.setTextColor(getResources().getColor(R.color.white));
            passedt.setTextColor(getResources().getColor(R.color.white));
            update.setBackgroundColor((getResources().getColor(R.color.white)));
            signout.setBackgroundColor((getResources().getColor(R.color.white)));
            show.setBackgroundColor((getResources().getColor(R.color.white)));
            unameEdtBtn.setBackgroundColor(getResources().getColor(R.color.black));
            unameEdtBtn.setImageDrawable(getResources().getDrawable(R.drawable.mode_edit_white_20));
            fnameEdtBtn.setBackgroundColor(getResources().getColor(R.color.black));
            fnameEdtBtn.setImageDrawable(getResources().getDrawable(R.drawable.mode_edit_white_20));
            lnameEdtBtn.setBackgroundColor(getResources().getColor(R.color.black));
            lnameEdtBtn.setImageDrawable(getResources().getDrawable(R.drawable.mode_edit_white_20));
            emailEdtBtn.setBackgroundColor(getResources().getColor(R.color.black));
            emailEdtBtn.setImageDrawable(getResources().getDrawable(R.drawable.mode_edit_white_20));
            passEdtBtn.setBackgroundColor(getResources().getColor(R.color.black));
            passEdtBtn.setImageDrawable(getResources().getDrawable(R.drawable.mode_edit_white_20));

        }



        auth= FirebaseAuth.getInstance();
        FirebaseUser user= auth.getCurrentUser();
        String Uid= user.getUid();
        userRef=reference.child(Uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n=snapshot.getValue(UserModel.class).getUsername();
                username.setText(n);
                e=snapshot.getValue(UserModel.class).getEmail();
                email.setText(e);
                p=snapshot.getValue(UserModel.class).getPassword();
                password.setText(p);
                f=snapshot.getValue(UserModel.class).getFirstname();
                firstname.setText(f);
                l=snapshot.getValue(UserModel.class).getLastname();
                lastname.setText(l);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Profile.this,MainActivity.class);
                startActivity(i);
            }
        });

        fnameEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnameEdtBtn.setVisibility(View.INVISIBLE);
                fnameedt.setVisibility(View.VISIBLE);
                fnameChk.setVisibility(View.VISIBLE);
                fnameedt.requestFocus();
                fnameclose.setVisibility(View.VISIBLE);
            }
        });

        lnameEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnameEdtBtn.setVisibility(View.INVISIBLE);
                lnameedt.setVisibility(View.VISIBLE);
                lnameChk.setVisibility(View.VISIBLE);
                lnameedt.requestFocus();
                lnameclose.setVisibility(View.VISIBLE);
            }
        });

        unameEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unameEdtBtn.setVisibility(View.INVISIBLE);
                unameedt.setVisibility(View.VISIBLE);
                unameChk.setVisibility(View.VISIBLE);
                unameedt.requestFocus();
                unameclose.setVisibility(View.VISIBLE);
            }
        });

        /*emailEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEdtBtn.setVisibility(View.INVISIBLE);
                emailedt.setVisibility(View.VISIBLE);
                emailChk.setVisibility(View.VISIBLE);
                emailedt.requestFocus();
                emailclose.setVisibility(View.VISIBLE);
            }
        });*/

        passEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passEdtBtn.setVisibility(View.INVISIBLE);
                passedt.setVisibility(View.VISIBLE);
                passChk.setVisibility(View.VISIBLE);
                passedt.requestFocus();
                passclose.setVisibility(View.VISIBLE);
            }
        });

        fnameChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f=fnameedt.getText().toString();
                if(!f.isEmpty()) {
                    firstname.setText(f);
                    fnameedt.setVisibility(View.INVISIBLE);
                    fnameChk.setVisibility(View.INVISIBLE);
                    fnameEdtBtn.setVisibility(View.VISIBLE);
                    fnameclose.setVisibility(View.INVISIBLE);
                }
                else{
                    fnameedt.setError("Cant be Empty!!");
                    fnameedt.requestFocus();
                }
            }
        });

        lnameChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String l=lnameedt.getText().toString();
                if(!l.isEmpty()) {
                    lastname.setText(l);
                    lnameedt.setVisibility(View.INVISIBLE);
                    lnameChk.setVisibility(View.INVISIBLE);
                    lnameEdtBtn.setVisibility(View.VISIBLE);
                    lnameclose.setVisibility(View.INVISIBLE);
                }
                else{
                    lnameedt.setError("Cant be Empty!!");
                    lnameedt.requestFocus();
                }
            }
        });

        unameChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=unameedt.getText().toString();
                if(!u.isEmpty()) {
                    username.setText(u);
                    unameedt.setVisibility(View.INVISIBLE);
                    unameChk.setVisibility(View.INVISIBLE);
                    unameEdtBtn.setVisibility(View.VISIBLE);
                    unameclose.setVisibility(View.INVISIBLE);
                }
                else{
                    unameedt.setError("Cant be Empty!!");
                    unameedt.requestFocus();
                }
            }
        });

        /*emailChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=emailedt.getText().toString();
                if(!e.isEmpty()) {
                    email.setText(e);
                    emailedt.setVisibility(View.INVISIBLE);
                    emailChk.setVisibility(View.INVISIBLE);
                    emailEdtBtn.setVisibility(View.VISIBLE);
                    emailclose.setVisibility(View.INVISIBLE);
                }
                else{
                    emailedt.setError("Cant be Empty!!");
                    emailedt.requestFocus();
                }
            }
        });*/

        passChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p=passedt.getText().toString();
                if(!p.isEmpty()) {
                    password.setText(p);
                    passedt.setVisibility(View.INVISIBLE);
                    passChk.setVisibility(View.INVISIBLE);
                    passEdtBtn.setVisibility(View.VISIBLE);
                    passclose.setVisibility(View.INVISIBLE);
                }
                if(p.length()<6){
                    passedt.setError("Password Should Contain more than 6 Characters !!");
                    Toast.makeText(Profile.this, "Password Should Contain more than 6 Characters !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    passedt.setError("Cant be Empty!!");
                    passedt.requestFocus();
                }
            }
        });

        fnameclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnameedt.setVisibility(View.INVISIBLE);
                fnameChk.setVisibility(View.INVISIBLE);
                fnameclose.setVisibility(View.INVISIBLE);
                fnameEdtBtn.setVisibility(View.VISIBLE);
            }
        });

        lnameclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnameedt.setVisibility(View.INVISIBLE);
                lnameChk.setVisibility(View.INVISIBLE);
                lnameclose.setVisibility(View.INVISIBLE);
                lnameEdtBtn.setVisibility(View.VISIBLE);
            }
        });

        unameclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unameedt.setVisibility(View.INVISIBLE);
                unameChk.setVisibility(View.INVISIBLE);
                unameclose.setVisibility(View.INVISIBLE);
                unameEdtBtn.setVisibility(View.VISIBLE);
            }
        });

        /*emailclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailedt.setVisibility(View.INVISIBLE);
                emailChk.setVisibility(View.INVISIBLE);
                emailclose.setVisibility(View.INVISIBLE);
                emailEdtBtn.setVisibility(View.VISIBLE);
            }
        });*/

        passclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passedt.setVisibility(View.INVISIBLE);
                passChk.setVisibility(View.INVISIBLE);
                passclose.setVisibility(View.INVISIBLE);
                passEdtBtn.setVisibility(View.VISIBLE);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show.getText().toString().equals("HIDE DETAILS")) {
                    //Toast.makeText(Profile.this, "Details are already shown !!", Toast.LENGTH_SHORT).show();
                    textview1.setVisibility(View.INVISIBLE);
                    textview2.setVisibility(View.INVISIBLE);
                    textview3.setVisibility(View.INVISIBLE);
                    textview4.setVisibility(View.INVISIBLE);
                    firstname.setVisibility(View.INVISIBLE);
                    lastname.setVisibility(View.INVISIBLE);
                    email.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    fnameEdtBtn.setVisibility(View.INVISIBLE);
                    lnameEdtBtn.setVisibility(View.INVISIBLE);
                    //emailEdtBtn.setVisibility(View.INVISIBLE);
                    passEdtBtn.setVisibility(View.INVISIBLE);
                    show.setText("SHOW DETAILS");
                }
                else {
                    textview1.setVisibility(View.VISIBLE);
                    textview2.setVisibility(View.VISIBLE);
                    textview3.setVisibility(View.VISIBLE);
                    textview4.setVisibility(View.VISIBLE);
                    firstname.setVisibility(View.VISIBLE);
                    lastname.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    fnameEdtBtn.setVisibility(View.VISIBLE);
                    lnameEdtBtn.setVisibility(View.VISIBLE);
                    //emailEdtBtn.setVisibility(View.VISIBLE);
                    passEdtBtn.setVisibility(View.VISIBLE);
                    show.setText("HIDE DETAILS");



                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);

                builder.setMessage("Are You Sure You Want to DELETE your Account ??");

                builder.setTitle("Alert !!");

                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog,which) -> {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if(task.isSuccessful()){
                                Toast.makeText(Profile.this, "Account Successfully Deleted ! GoodBye !!", Toast.LENGTH_SHORT).show();
                                reference.child(Uid).removeValue();
                                ref.child(n).removeValue();
                                limref.child(n).removeValue();
                                startActivity(new Intent(Profile.this,MainActivity.class));

                            }
                        }
                    });
                });

                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

                    dialog.cancel();
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!p.equals(password.getText().toString())) {
                    userRef.child("password").setValue(password.getText().toString());
                    user.updatePassword(password.getText().toString());
                }

                if (!e.equals(email.getText().toString())) {
                    userRef.child("email").setValue(email.getText().toString());
                }
                if(!n.equals(username.getText().toString())){
                    userRef.child("username").setValue(username.getText().toString());
                }
                if(!l.equals(lastname.getText().toString())) {
                    userRef.child("lastname").setValue(lastname.getText().toString());
                }
                if(!f.equals(firstname.getText().toString())) {
                    userRef.child("firstname").setValue(firstname.getText().toString());
                }

                if(p.equals(password.getText().toString()) && e.equals(email.getText().toString()) && n.equals(username.getText().toString()) && l.equals(lastname.getText().toString()) && f.equals(firstname.getText().toString())) {
                    Toast.makeText(Profile.this, "No Changes Found !", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(Profile.this,HomePage.class);
        startActivity(i);
        finish();
    }

    /*private boolean isPassChanged() {
        if(!p.equals(password.getText().toString())){
            userRef.child("password").setValue(password.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!e.equals(email.getText().toString())){
            userRef.child("email").setValue(email.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isUserChanged() {
        if(!n.equals(username.getText().toString())){
            userRef.child("username").setValue(username.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isLastChanged() {
        if(!l.equals(lastname.getText().toString())){
            userRef.child("lastname").setValue(lastname.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!f.equals(firstname.getText().toString())){
            userRef.child("firstname").setValue(firstname.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }*/

}