package com.huce.imuisc.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huce.imuisc.R;

public class FragmentHome extends Fragment {
    androidx.appcompat.widget.Toolbar toolbarhome;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbarhome = view.findViewById(R.id.toolbarhome);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarhome);
        toolbarhome.setTitleTextColor(Color.WHITE);
        return  view;
    }
}