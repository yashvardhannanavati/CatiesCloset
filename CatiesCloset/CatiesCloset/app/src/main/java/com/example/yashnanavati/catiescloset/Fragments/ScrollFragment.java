package com.example.yashnanavati.catiescloset.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yashnanavati.catiescloset.DonationModule.UserProfileActivity;
import com.example.yashnanavati.catiescloset.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollFragment extends Fragment {


    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;



    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    private static int load_img = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll, container, false);

        EditText enterName = (EditText) view.findViewById(R.id.enterName);

        Button btnChangePic = (Button) view.findViewById(R.id.btnChangePic);

        btnChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, load_img);
            }
        });

        enterName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                SharedPreferences shared = getActivity().getPreferences(Context.MODE_PRIVATE);
                shared.edit().putString("username", s.toString()).commit();
                Log.d("SPF","spf put"+s.toString());
                ((UserProfileActivity)getActivity()).setUsername();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {}
        });

        enterName.setText(getActivity().getPreferences(Context.MODE_PRIVATE).getString("username", "User"));

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
    }

}
