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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    public EditText nume, prenume, telefon, CNP, CUI, serie, adresa, eliberare, numefirma;
    TextView persoanajuridica;
    private FirebaseAuth mFirebaseAuth;
    Button registerbutton;
    public User user;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prenume = findViewById(R.id.lastNameRegisterTextBox);
        nume = findViewById(R.id.registerNameTextBox);
        telefon = findViewById(R.id.phoneRegisterTextBox);
        CNP = findViewById(R.id.cNPRegisterTextBox);
        CUI = findViewById(R.id.cUIRegisterTextBox);
        serie = findViewById(R.id.serieNrRegisterTextBox);
        adresa = findViewById(R.id.adressRegisterTextBox);
        eliberare = findViewById(R.id.ciprintRegisterTextBox);
        numefirma = findViewById(R.id.sRlNameRegisterTextBox);
        persoanajuridica = findViewById(R.id.persoanaJuridicaTextBox);
        mFirebaseAuth = FirebaseAuth.getInstance();
        registerbutton = findViewById(R.id.confirmRegisterButton);




        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _nume =nume.getText().toString();
                String _prenume =prenume.getText().toString().trim();
                String _telefon =telefon.getText().toString().trim();
                String _serie =serie.getText().toString().trim();
                String _CNP =CNP.getText().toString().trim();
                String _CUI =CUI.getText().toString().trim();
                String _numefirma =numefirma.getText().toString().trim();
                String _eliberare =eliberare.getText().toString().trim();
                String _adresa =adresa.getText().toString().trim();
                email = getIntent().getStringExtra("email");
                String _stare = "";

                user = new User(_nume, _prenume, _telefon, _serie, _CNP, _CUI, _numefirma, _eliberare, _adresa, email, _stare);
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Registrare Reusita!", Toast.LENGTH_SHORT).show();
                            Intent intToHome = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intToHome);
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Aaparut o eroare! Ne pare rau!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        persoanajuridica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CUI.setVisibility(View.VISIBLE);
                numefirma.setVisibility(View.VISIBLE);
            }
        });

    }
}