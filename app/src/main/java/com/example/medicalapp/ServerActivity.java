package com.example.medicalapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerActivity extends AppCompatActivity  {

    EditText ip_insert, message_insert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);


        Thread myThread = new Thread(new MyServer());
        myThread.start();
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
                            Toast.makeText(getApplicationContext(), "message recieved from client : " + message, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public static  class BackgroundTask extends AsyncTask<String, Void, String>
    {

        Socket socket;
        DataOutputStream dataOutputStream;
        String ip, message;

        @Override
        protected String doInBackground(String... params) {

            ip = params[0];
            message = params[1];
            try {
                socket = new Socket(ip, 9700);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(message);
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

