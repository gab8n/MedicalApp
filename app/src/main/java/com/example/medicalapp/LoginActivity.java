package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public EditText emailId, password;
    Button loginButton;
    TextView textViewSingUp;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.LoginEmailTextBox);
        password = findViewById(R.id.PasswordLoginTextBox);
        loginButton = findViewById(R.id.LoginButton);
        textViewSingUp = findViewById(R.id.LoginTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Introduceti ID");
                    emailId.requestFocus();
                }
                else
                if(pwd.isEmpty()){
                    password.setError("Introduceti Parola");
                    password.requestFocus();
                }
                else
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Casutele sunt goale!", Toast.LENGTH_SHORT).show();

                }
                else
                if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Logare Nereusita! Va rugam reincercati!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"A avut loc o eroare! Ne pare rau! ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        textViewSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToSingUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intToSingUp);
            }
        });
    }


}