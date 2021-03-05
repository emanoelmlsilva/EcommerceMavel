package com.example.ecommercemarvel.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ecommercemarvel.R;
import com.example.ecommercemarvel.model.Comic;

public class DetailFragment extends Fragment {
    private Comic comic;
    public DetailFragment(Comic comic) {
        this.comic = comic;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        return root;
    }
}