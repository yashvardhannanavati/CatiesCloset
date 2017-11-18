package com.example.yashnanavati.catiescloset.Fragments;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yashnanavati.catiescloset.Model.Donation;
import com.example.yashnanavati.catiescloset.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash Nanavati on 11/17/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DonationViewHolder> {

    //  Data
    private List<Donation> donation_list = new ArrayList<>();

    private Context context;

    public CardViewAdapter(Context context) {
        this.context = context;
        //prepareDesserts();
        donation_list = new ArrayList<>();
        donation_list.add(new Donation("Donate Items", "Clothes, Toileteries, etc.", R.drawable.kind_donation));
        donation_list.add(new Donation("Donate Cash", "Donate using Credit/Debit card, Paypal etc.", R.drawable.cash_donation));

    }

    @Override
    public DonationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardview, parent, false);
        DonationViewHolder pvh = new DonationViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DonationViewHolder holder, int position) {
        holder.titleTxt.setText(donation_list.get(position).typeOfDonation);
        holder.descTxt.setText(donation_list.get(position).descDonation);
        holder.cardPhoto.setImageResource(donation_list.get(position).photoCard);
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
