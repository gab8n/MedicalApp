package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
        public EditText emailId, password;
        Button registerButton;
        TextView textViewSingIn;
        FirebaseAuth mFirebaseAuth;
        private ProgressBar progressBar;
        String globalemail;
        String email;
         private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.LoginEmailTextBox);
        password = findViewById(R.id.PasswordLoginTextBox);
        registerButton = findViewById(R.id.LoginButton);
        textViewSingIn = findViewById(R.id.LoginTextView);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(MainActivity.this, "Autentificare Reusita!" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);

                }



            }
        };

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 email = emailId.getText().toString();

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
                        Toast.makeText(MainActivity.this,"Casutele sunt goale!", Toast.LENGTH_SHORT).show();

                    }
                    else
                    if(!(email.isEmpty() && pwd.isEmpty())){
                        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Registrare nereusita! Reincercati!", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Intent intToRegisterActivity = new Intent(MainActivity.this, RegisterActivity.class);
                                    intToRegisterActivity.putExtra("email", email);
                                    startActivity(intToRegisterActivity);

                                }


                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this,"A avut loc o eroare! Ne pare rau! ", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        textViewSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}