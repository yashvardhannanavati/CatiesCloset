package com.example.yashnanavati.catiescloset.Adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yashnanavati.catiescloset.Model.Donation;
import com.example.yashnanavati.catiescloset.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.internal.Utils;

/**
 * Created by Yash Nanavati on 11/20/2017.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {


    private Context context;

    public HomePageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.images.setBackgroundResource(R.drawable.home_image);
        //runEnterAnimation(holder.itemView, position);
        Log.d("Animation","I am in animation");
        holder.images.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable homeAnimation = (AnimationDrawable) holder.images.getBackground();
                homeAnimation.start();
            }
        });

    }

    private void runEnterAnimation(View view, int position) {
        view.animate()
                .rotation(360)
                .setDuration(2000)
                .start();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;

        ViewHolder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.images);
        }

    }

}
