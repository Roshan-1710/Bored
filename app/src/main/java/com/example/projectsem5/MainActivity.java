package com.example.projectsem5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.games.Players;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
public TextView newtxt,errtxt,forgot;
public EditText uname,pass;
public Button login;
public ProgressBar prog;

FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));

        newtxt=(TextView) findViewById(R.id.newtxt);
        errtxt=(TextView) findViewById(R.id.textView4);
        uname=(EditText) findViewById(R.id.editText);
        pass=(EditText) findViewById(R.id.editText2);
        login=(Button) findViewById(R.id.login);
        forgot=(TextView) findViewById(R.id.forgot);
        prog=(ProgressBar) findViewById(R.id.prog);
        mAuth=FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prog.setVisibility(View.VISIBLE);

                if(uname.length()==0){
                    if(pass.length()==0){
                        prog.setVisibility(View.GONE);
                        uname.requestFocus();
                        uname.setError("Please Enter an Email");
                        pass.requestFocus();
                        pass.setError("Please Enter Password");
                        errtxt.setVisibility(View.VISIBLE);
                        errtxt.setText("!! Please Enter Username and Password !!");
                    }
                    else {
                        prog.setVisibility(View.GONE);
                        uname.requestFocus();
                        uname.setError("Please Enter a Username");
                        errtxt.setVisibility(View.VISIBLE);
                        errtxt.setText("!! Enter Valid Email !!");
                    }
                }
                else if (pass.length()==0){
                    prog.setVisibility(View.GONE);
                    pass.requestFocus();
                    pass.setError("Please Enter Password");
                    errtxt.setVisibility(View.VISIBLE);
                    errtxt.setText("!! Enter Valid Password !!");
                }
                else{
                    prog.setVisibility(View.GONE);
                    //String n= uname.getText().toString();
                    errtxt.setVisibility(View.INVISIBLE);
                    isUser();
                }

            }
        });
        newtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
                finish();

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=uname.getText().toString();
                if(!email.isEmpty()) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Password Email Link sent", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter Email and click again", Toast.LENGTH_LONG).show();
                    uname.requestFocus();
                }
            }
        });

    }

    private void isUser() {

        String enteredUsername = uname.getText().toString().trim();
        String enteredPassword = pass.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(enteredUsername,enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    startActivity(new Intent(MainActivity.this,HomePage.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}