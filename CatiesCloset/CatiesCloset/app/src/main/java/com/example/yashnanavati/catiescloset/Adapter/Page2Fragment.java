package com.example.yashnanavati.catiescloset.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yashnanavati.catiescloset.R;

/**
 * Created by haris on 12/5/2017.
 */

public class Page2Fragment  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_three, container, false);

        return view;
    }
}
