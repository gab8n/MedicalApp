package com.example.medicalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QRGeneretorFragment extends Fragment {
    public static View sview;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sview = inflater.inflate(R.layout.fragment_q_r_generator, container, false);
        QRGenerator.GenerarareQR();


        return sview;
    }
}
