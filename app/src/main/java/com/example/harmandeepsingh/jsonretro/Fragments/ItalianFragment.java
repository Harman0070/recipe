package com.example.harmandeepsingh.jsonretro.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harmandeepsingh.jsonretro.R;

/**
 * Created by Harmandeep singh on 8/22/2017.
 */

public class ItalianFragment extends Fragment {
    public ItalianFragment() {;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_italian, container, false);
    }
}
