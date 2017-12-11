package com.example.yashnanavati.catiescloset.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yashnanavati.catiescloset.DonationStats.DonationStatsActivity;
import com.example.yashnanavati.catiescloset.Model.Donations;
import com.example.yashnanavati.catiescloset.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.List;

/**
 * Created by Yash Nanavati on 12/7/2017.
 */

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder> {

    List<Object> contents;
    private final OnItemClickListener listener;
    Context context;
    private int countItems = 0;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_NEW = 2;
    static final int TYPE_COUNT = 3;


    public ProfileRecyclerViewAdapter(List<Object> contents, FragmentActivity activity, OnItemClickListener listener) {
        this.contents = contents;
        this.listener = listener;
        this.context = activity;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            case 1:
                return TYPE_NEW;
            case 2:
                return TYPE_CELL;
            case 3:
                return TYPE_COUNT;
            default:
                return TYPE_COUNT;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnShareFb;
        private Button btnShareTw;
        ShareDialog shareDialog;
        private ImageView imgComplete;
        private ImageView imgIncomplete;
        private TextView help_count;


        public ViewHolder(final View itemView) {
            super(itemView);
            final String sharedString = "I donated " + Integer.toString(howMuch()) + " packages to Catie's Closet!";
            shareDialog = new ShareDialog((Activity) itemView.getContext());

            btnShareFb = (Button) itemView.findViewById(R.id.btnShareFb);
            if (btnShareFb != null)
                btnShareFb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Facebook Clicked", Toast.LENGTH_SHORT).show();

                        //ShareButton shareButton = (ShareButton)itemView.findViewById(R.id.btnShareFb);
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse("http://catiescloset.org"))
                                .setQuote(sharedString)
                                .build();
                        shareDialog.show(content);
                        //shareButton.setShareContent(content);

                    }
                });

            btnShareTw = (Button) itemView.findViewById(R.id.btnShareTw);
            if (btnShareTw != null)
                btnShareTw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Twitter Clicked", Toast.LENGTH_SHORT).show();
                        TweetComposer.Builder builder = new TweetComposer.Builder(v.getContext())
                                .text(sharedString);
                        builder.show();
                    }
                });

            Button btnShare = (Button) itemView.findViewById(R.id.btnShare);
            if (btnShare != null)
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, sharedString);
                        sendIntent.setType("text/plain");
                        itemView.getContext().startActivity(sendIntent);
                    }
                });

            imgComplete = (ImageView) itemView.findViewById(R.id.imgComplete);
            if (imgComplete != null)
                imgComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(itemView.getContext(), DonationStatsActivity.class);
                        i.putExtra("status", true);
                        itemView.getContext().startActivity(i);
                    }
                });

            imgIncomplete = (ImageView) itemView.findViewById(R.id.imgIncomplete);
            if (imgIncomplete != null)
                imgIncomplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(itemView.getContext(), DonationStatsActivity.class);
                        i.putExtra("status", false);
                        itemView.getContext().startActivity(i);
                    }
                });

            help_count = (TextView) itemView.findViewById(R.id.help_count);

        }

        //TODO: connect to Firebase to get the values of how many things donnated
        public int howMuch() {
            return 137;
        }

        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;


        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_badges, parent, false);

                return new ViewHolder(view) {
                };
            }
            case TYPE_NEW: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_packages, parent, false);
                return new ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_share, parent, false);
                return new ViewHolder(view) {
                };
            }
            case TYPE_COUNT: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_count, parent, false);
                return new ViewHolder(view) {
                };
            }
        }
        return null;
    }

    public int getDataFromDb(final ViewHolder holder) {
        FirebaseDatabase.getInstance().getReference().child("donations").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "|"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (final DataSnapshot postDataSnapshot : dataSnapshot.getChildren()) {
                                Donations d = postDataSnapshot.getValue(Donations.class);
                                if (null != d) {
                                    if (d.getPkg().isDelivered()) {
                                        countItems += d.getPkg().getNoOfItems();
                                        Log.d("***** COUNT EACH", String.valueOf(d.getPkg().getNoOfItems()));
                                    }
                                }
                            }
                            holder.help_count.setText(String.valueOf(countItems));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Log.d("***** COUNT ITEMS", String.valueOf(countItems));
        return countItems;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_NEW:
                break;
            case TYPE_CELL:
                break;
            case TYPE_COUNT:
                getDataFromDb(holder);
                //holder.help_count.setText("10");
                break;
        }
    }


}
