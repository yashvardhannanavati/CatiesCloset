package com.example.yashnanavati.catiescloset.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yashnanavati.catiescloset.Adapter.ProfileRecyclerViewAdapter;
import com.example.yashnanavati.catiescloset.R;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileRecyclerViewFragment extends Fragment {



    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 4;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static ProfileRecyclerViewFragment newInstance() {
        return new ProfileRecyclerViewFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_recycler_view, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final List<Object> items = new ArrayList<>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }



        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(new ProfileRecyclerViewAdapter(items, getActivity(), new ProfileRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                Toast.makeText(getContext(), "ITEM CLICKED!", Toast.LENGTH_SHORT).show();
                addBadge("captain");
            }
        }));

    }



    //Adding a badge of type type to the list
    public void addBadge(String type){
        View v = mRecyclerView.getLayoutManager().findViewByPosition(0);
        LinearLayout layadd = (LinearLayout) v.findViewById(R.id.layadd);
        ImageView ii= new ImageView(getContext());
        ii.setLayoutParams(new LinearLayout.LayoutParams(200,200));
        ((LinearLayout.LayoutParams)ii.getLayoutParams()).setMargins(10,10,10,10);


        switch(type){
            case "flash":
                ii.setBackgroundResource(R.drawable.flash);
                break;
            case "superman":
                ii.setBackgroundResource(R.drawable.superman);
                break;
            case "batman":
                ii.setBackgroundResource(R.drawable.batman);
                break;
            case "ironman":
                ii.setBackgroundResource(R.drawable.ironman);
                break;
            case "captain":
                ii.setBackgroundResource(R.drawable.captain);
                break;
            default:
                Toast.makeText(getContext(), "Badge is not found!", Toast.LENGTH_SHORT).show();

        }
        layadd.addView(ii);

    }

    //Adding a package of type 0 if it is incomplete and 1 if it complete
    public void addPackage(int complete){
        View v = mRecyclerView.getLayoutManager().findViewByPosition(1);
        LinearLayout layPack = (LinearLayout) v.findViewById(R.id.layPack);


        ImageView ii= new ImageView(getContext());
        ii.setLayoutParams(new LinearLayout.LayoutParams(200,200));
        ((LinearLayout.LayoutParams)ii.getLayoutParams()).setMargins(10,10,10,10);


        switch(complete){
            case 0:
                ii.setBackgroundResource(R.drawable.pack2);
                break;
            case 1:
                ii.setBackgroundResource(R.drawable.pack1);
                break;

            default:
                Toast.makeText(getContext(), "Package is not found!", Toast.LENGTH_SHORT).show();

        }
        layPack.addView(ii);

    }


}
