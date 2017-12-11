package com.example.yashnanavati.catiescloset.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.yashnanavati.catiescloset.R;

/**
 * Created by haris on 12/5/2017.
 */

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {


    private Context context;

    public AboutUsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AboutUsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_aboutus, parent, false);
        AboutUsAdapter.ViewHolder pvh = new AboutUsAdapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final AboutUsAdapter.ViewHolder holder, int position) {
        WebSettings webSettings = holder.web.getSettings();
        holder.web.getSettings().setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        holder.web.loadUrl("http://www.catiescloset.org/");
        //runEnterAnimation(holder.itemView, position);


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
        WebView web;

        ViewHolder(View itemView) {
            super(itemView);
            web = (WebView) itemView.findViewById(R.id.webView1);
        }

    }
}