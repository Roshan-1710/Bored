package com.example.projectsem5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    public EditText fname,lname,pass,conpass,username,email;
    public Button signin;
    public TextView login,errtxt;
    public String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    Pattern pattern;
    Matcher matcher;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();

        View view=this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.white));

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.white));

        fname=(EditText) findViewById(R.id.fname);
        lname=(EditText) findViewById(R.id.lname);
        pass=(EditText) findViewById(R.id.pass);
        conpass=(EditText) findViewById(R.id.conpass);
        username=(EditText) findViewById(R.id.username);
        signin=(Button) findViewById(R.id.signin);
        login=(TextView) findViewById(R.id.textView8);
        errtxt=(TextView) findViewById(R.id.errtxt);
        email=(EditText) findViewById(R.id.email);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pattern = Pattern.compile(emailPattern);
                matcher = pattern.matcher(email.getText().toString());

                if (pass.length()<6){
                    pass.requestFocus();
                    pass.setError("Password Should Contain more than 6 Characters !!");
                    Toast.makeText(SignUp.this, "Password Should Contain more than 6 Characters !!", Toast.LENGTH_SHORT).show();
                }

                if (!conpass.getText().toString().equals(pass.getText().toString())) {
                    pass.requestFocus();
                    pass.setError("Passwords Don't Match !!");
                    pass.setText("");
                    conpass.setText("");

                }
                if (!matcher.matches()){
                    email.requestFocus();
                    email.setError("Enter a Valid Email");
                    errtxt.setText("Enter a Valid Email !!");
                    email.setText("");
                }
                if(fname.length()==0 || lname.length()==0 || pass.length()==0 || conpass.length()==0 || username.length()==0 || email.length()==0) {
                    errtxt.setVisibility(View.VISIBLE);
                    errtxt.setText("!! PLease Fill Out All the Fields !!");
                }

                else {
                    String Email=email.getText().toString();
                    String Firstname=fname.getText().toString();
                    String Lastname=lname.getText().toString();
                    String Username=username.getText().toString();
                    String Password=pass.getText().toString();

                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Account Created !!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user=mAuth.getCurrentUser();
                                String Uid=user.getUid();

                                rootNode=FirebaseDatabase.getInstance("https://projectsem5-94bc6-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                reference=rootNode.getReference("Players");
                                playerHelperClass helperClass=new playerHelperClass(Email,Firstname,Lastname,Username,Password);
                                reference.child(Uid).setValue(helperClass);
                                Intent i=new Intent(SignUp.this, MainActivity.class);
                                i.putExtra("name",Username);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(SignUp.this, "Authentication Failed !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
}