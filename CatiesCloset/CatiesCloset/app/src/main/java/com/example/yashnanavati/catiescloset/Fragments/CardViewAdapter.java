package com.example.yashnanavati.catiescloset.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yashnanavati.catiescloset.DonationModule.CashdonActivity;
import com.example.yashnanavati.catiescloset.DonationModule.DonationModuleActivity;
import com.example.yashnanavati.catiescloset.Model.DonationNavPage;
import com.example.yashnanavati.catiescloset.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash Nanavati on 11/17/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DonationViewHolder> {

    //  Data
    private List<DonationNavPage> donation_list = new ArrayList<>();

    private Context context;

    public CardViewAdapter(Context context) {
        this.context = context;
        //prepareDesserts();
        donation_list = new ArrayList<>();
        donation_list.add(new DonationNavPage("Donate Items", "Clothes, Toileteries, etc.", R.drawable.kind_donation));
        donation_list.add(new DonationNavPage("Donate Cash", "Donate using Credit/Debit card, Paypal etc.", R.drawable.cash_donation));

    }

    @Override
    public DonationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview, parent, false);
        DonationViewHolder pvh = new DonationViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DonationViewHolder holder, final int position) {
        holder.titleTxt.setText(donation_list.get(position).typeOfDonation);
        holder.descTxt.setText(donation_list.get(position).descDonation);
        holder.cardPhoto.setImageResource(donation_list.get(position).photoCard);
        /* TODO add this snippet
         * TODO add CashdonActivity
         * TODO add CashdonActivity to AndroidManifest.xml
         * TODO add layout/activity_cashdon.xml
         * TODO add drawable files
         * TODO add layout/activity_cash_response.xml*/
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent donation_module = new Intent(context, DonationModuleActivity.class);
                if (donation_list.get(position).typeOfDonation.equals("Donate Items")) {
                    Log.i("tag", "Donate Items");
                    donation_module = new Intent(context, DonationModuleActivity.class);
                } else if (donation_list.get(position).typeOfDonation.equals("Donate Cash")) {
                    Log.i("tag", "Donate Cash");
                    donation_module = new Intent(context, CashdonActivity.class);
                }
                /* add values in bundle */
                context.startActivity(donation_module);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donation_list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class DonationViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titleTxt;
        TextView descTxt;
        ImageView cardPhoto;

        DonationViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            titleTxt = (TextView)itemView.findViewById(R.id.titleTxt);
            descTxt = (TextView)itemView.findViewById(R.id.descTxt);
            cardPhoto = (ImageView)itemView.findViewById(R.id.cardPhoto);
        }

    }

}
