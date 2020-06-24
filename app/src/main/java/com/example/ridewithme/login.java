package com.example.ridewithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private TextView join;
    private EditText email,pass;
    private Button register;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        join=(TextView) findViewById(R.id.join);
        register= (Button) findViewById(R.id.login);
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, com.example.ridewithme.join.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1=email.getText().toString();
                String pass1=pass.getText().toString();

                if(TextUtils.isEmpty(email1)) Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(pass1)) Toast.makeText(getApplicationContext(),"Please enter passwordr",Toast.LENGTH_SHORT).show();
                else{
                    dialog=new ProgressDialog(login.this);
                    dialog.setMessage("Please wait for System to log you in");
                    dialog.show();

                    FirebaseAuth mAuth= FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email1,pass1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Login  Successfully",Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(login.this,home.class);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }
                            });
                }



            }
        });
    }
}
