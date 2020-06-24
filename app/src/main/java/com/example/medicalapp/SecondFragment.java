package com.example.medicalapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    public static View sview;
    private SecondFragment.onFragmentButtonSelected1 listener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         sview = inflater.inflate(R.layout.fragment_second, container, false);
         HomeActivity.update();

        Button clickme = sview.findViewById(R.id.edit_button);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected1();
            }
        });


       return sview;
   }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainFragment.onFragmentButtonSelected){
            listener = (SecondFragment.onFragmentButtonSelected1) context;

        }
        else{
            throw new ClassCastException(context.toString() + "must implement listener");
        }

    }

    public interface onFragmentButtonSelected1{
        public void onButtonSelected1();
    }
}
