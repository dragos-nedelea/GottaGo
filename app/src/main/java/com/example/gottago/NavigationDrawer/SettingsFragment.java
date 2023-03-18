package com.example.gottago.NavigationDrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.gottago.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

public class SettingsFragment extends Fragment {

//    boolean nightMODE;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

//        SwitchMaterial Switch = v.findViewById(R.id.DarkModeSwitch);
//        sharedPreferences = getActivity().getSharedPreferences("Mode", Context.MODE_PRIVATE);
//        nightMODE = sharedPreferences.getBoolean("night", false);
//
//        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("night", false);
//                }else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("night", true);
//                }
//            }
//        });
//
    return v;
    }

}