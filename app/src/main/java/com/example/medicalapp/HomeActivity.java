package com.example.medicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainFragment.onFragmentButtonSelected, SecondFragment.onFragmentButtonSelected1 {

    DrawerLayout drawerlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button logout_button;
    DatabaseReference userRef;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    String userid;
    public static Button edit_button;
    public static String user_email, user_CNP, user_prenume, user_telefon, user_adresa, user_nume, user_serie;
    public static String user_stare;
    public FusedLocationProviderClient client;
    public static String locatie;
    public static String log_de_trimis;


    public static void update()
    {
        if(SecondFragment.sview == null) {


            new Handler().postDelayed(new Runnable() {
                public void run() {
                    update();

                }
            }, 100);


        }
        else {
            TextView nume_textView = SecondFragment.sview.findViewById(R.id.nume_textview);
            TextView prenume_textView = SecondFragment.sview.findViewById(R.id.prenume_textview);
            TextView email_textView = SecondFragment.sview.findViewById(R.id.email_textview);
            TextView telefon_textView = SecondFragment.sview.findViewById(R.id.telefon_textview);
            TextView serie_textView = SecondFragment.sview.findViewById(R.id.serie_textview);
            TextView CNP_textView = SecondFragment.sview.findViewById(R.id.CNP_textview);
            TextView adresa_textView = SecondFragment.sview.findViewById(R.id.adresa_textview);

            nume_textView.setText("Nume : "+user_nume);
            prenume_textView.setText("Prenume : "+user_prenume);
            email_textView.setText("Email : "+user_email);
            telefon_textView.setText("Telefon : "+user_telefon);
            serie_textView.setText("Serie/Nr : "+user_serie);
            CNP_textView.setText("CNP : "+user_CNP);
            adresa_textView.setText("Adresa : "+user_adresa);
        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        Thread myThread = new Thread(new HomeActivity.MyServer());
        myThread.start();


        toolbar = findViewById(R.id.toolbar);
        drawerlayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open, R.string.close);
        drawerlayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new MainFragment());
        fragmentTransaction.commit();

        logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intToLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intToLogin);


            }
        });



        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userRef = database.getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        userid = firebaseUser.getUid();

        userRef.child("Users").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                user_nume = dataSnapshot.child("nume").getValue(String.class);
                user_prenume = dataSnapshot.child("prenume").getValue(String.class);
                user_email = dataSnapshot.child("email").getValue(String.class);
                user_CNP = dataSnapshot.child("CNP").getValue(String.class);
                user_adresa = dataSnapshot.child("adresa").getValue(String.class);
                user_telefon = dataSnapshot.child("telefon").getValue(String.class);
                user_serie = dataSnapshot.child("serie").getValue(String.class);
                user_stare = dataSnapshot.child("stare").getValue(String.class);

                if(SecondFragment.sview == null) {


                      new Handler().postDelayed(new Runnable() {
                        public void run() {
                            update();

                        }
                    }, 100);


                }
                else {
                    TextView nume_textView = SecondFragment.sview.findViewById(R.id.nume_textview);
                    TextView prenume_textView = SecondFragment.sview.findViewById(R.id.prenume_textview);
                    TextView email_textView = SecondFragment.sview.findViewById(R.id.email_textview);
                    TextView telefon_textView = SecondFragment.sview.findViewById(R.id.telefon_textview);
                    TextView serie_textView = SecondFragment.sview.findViewById(R.id.serie_textview);
                    TextView CNP_textView = SecondFragment.sview.findViewById(R.id.CNP_textview);
                    TextView adresa_textView = SecondFragment.sview.findViewById(R.id.adresa_textview);

                    nume_textView.setText("Nume : "+user_nume);
                    prenume_textView.setText("Prenume : "+user_prenume);
                    email_textView.setText("Email : "+user_email);
                    telefon_textView.setText("Telefon : "+user_telefon);
                    serie_textView.setText("Serie/Nr : "+user_serie);
                    CNP_textView.setText("CNP : "+user_CNP);
                    adresa_textView.setText("Adresa : "+user_adresa);
                }

                //Toast.makeText(HomeActivity.this, user_email, Toast.LENGTH_SHORT).show();
                //Toast.makeText(HomeActivity.this, user_nume, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Eroare la conectarea in baza de date!", Toast.LENGTH_SHORT).show();


            }
        });

        requestPermission();
        final Handler handler = new Handler();
// Define the code block to be executed
         Runnable runnableCode = new Runnable() {
            @Override
            public void run() {

        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());


        if (ActivityCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {


            return;
        }
        client.getLastLocation().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null){


                    locatie = location.toString();
                    String[] separat = locatie.split(" ");
                    String[] separat2 = separat[1].split(",");
                    if(separat[1].contains(".")){
                        log_de_trimis = " [ "+getTime()+" ] "+" [User : "+ user_email +" ] " + " [ Lat : " + separat2[0]  +" Long : " + separat2[1] + " ] ";

                    }
                    else {
                        log_de_trimis =  " [ "+getTime()+" ] " + "[User : " + user_email + " ] " + " [ Lat : " + separat2[0] + "," + separat2[1] + " Long : " + separat2[2] + "," + separat2[3] + " ] ";

                    }
                    if(user_email != null) {
                        if (!(user_email.equals("administrator@yahoo.com"))) {
                            ServerActivity.BackgroundTask backgroundTask = new ServerActivity.BackgroundTask();
                            backgroundTask.execute("192.168.100.11", log_de_trimis);
                        }
                    }





                }

            }
        });
                handler.postDelayed(this, 12000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);




    }
    public void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerlayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.home1) {
            drawerlayout.closeDrawer(GravityCompat.START);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new QRGeneretorFragment());

            fragmentTransaction.commit();



        }
        if (menuItem.getItemId() == R.id.another) {
            drawerlayout.closeDrawer(GravityCompat.START);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new SecondFragment());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.another1) {
            Intent intToQRGenerator = new Intent(HomeActivity.this, QRScannerActivity.class);
            startActivity(intToQRGenerator);

        }
        return true;
    }
    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public class MyServer implements Runnable{
        ServerSocket serverSocket;
        Socket mySocket;
        DataInputStream dataInputStream;
        String message;
        Handler handler = new Handler();


        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(9700);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "waiting for client", Toast.LENGTH_SHORT).show();
                    }
                });

                while (true){

                    mySocket = serverSocket.accept();
                    dataInputStream = new DataInputStream(mySocket.getInputStream());
                    message = dataInputStream.readUTF();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            FirebaseDatabase.getInstance().getReference("Logs").child(user_CNP).push().setValue(message);
                            Toast.makeText(getApplicationContext(), "message recieved from client : " + message, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onButtonSelected() {
        //Intent intToServer = new Intent(HomeActivity.this, ServerActivity.class);
        //startActivity(intToServer);
        //Intent intToServer = new Intent(HomeActivity.this, GPSActivity.class);
        //startActivity(intToServer);


    }
    @Override
    public void onButtonSelected1() {
        //Intent intToServer = new Intent(HomeActivity.this, ServerActivity.class);
        //startActivity(intToServer);
        Intent intToEdit = new Intent(HomeActivity.this, RegisterActivity.class);
        startActivity(intToEdit);


    }

}