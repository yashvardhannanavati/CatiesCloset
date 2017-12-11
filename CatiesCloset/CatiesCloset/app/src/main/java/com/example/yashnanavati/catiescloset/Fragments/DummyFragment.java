package com.example.yashnanavati.catiescloset.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yashnanavati.catiescloset.Adapter.AboutUsAdapter;
import com.example.yashnanavati.catiescloset.Adapter.ViewPagerAdapter;
import com.example.yashnanavati.catiescloset.R;

import me.huseyinozer.TooltipIndicator;

public class DummyFragment extends Fragment {
    public static final String TAG = DummyFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLOR = "tab_index";

    // TODO: Rename and change types of parameters
    private int tab_index;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private TooltipIndicator indicator;


    private RecyclerView recyclerView;

    public DummyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tab_index = getArguments().getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        indicator = (TooltipIndicator) rootView.findViewById(R.id.tooltip_indicator);




        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setBackgroundColor(Color.WHITE);

//        if(tab_index==1){
//            CardViewAdapter adapter = new CardViewAdapter(getContext());
//            //adapter = new ViewPagerAdapter(getFragmentManager());
//            //viewPager.setAdapter(adapter);
//            recyclerView.setAdapter(adapter);

        if(tab_index==2){
            CardViewAdapter adapter = new CardViewAdapter(getContext());
            recyclerView.setAdapter(adapter);
        }else {
            AboutUsAdapter adapter = new AboutUsAdapter(getContext());
            recyclerView.setAdapter(adapter);
        }
        return rootView;

    }


    /**
     * Updates {@link RecyclerView} background color upon changing Bottom Navigation item.
     *
     * @param color to apply to {@link RecyclerView} background.
     */
    public void updateColor(int color) {
        recyclerView.setBackgroundColor(getLighterColor(color));
    }

    /**
     * Facade to return colors at 30% opacity.
     *
     * @param color
     * @return
     */
    private int getLighterColor(int color) {
        return Color.argb(30,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

}