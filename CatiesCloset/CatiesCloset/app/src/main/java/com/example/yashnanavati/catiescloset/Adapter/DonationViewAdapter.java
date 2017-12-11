package com.example.yashnanavati.catiescloset.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.yashnanavati.catiescloset.DonationModule.DonationDeliveryActivity;
import com.example.yashnanavati.catiescloset.Model.Cash;
import com.example.yashnanavati.catiescloset.Model.Donations;
import com.example.yashnanavati.catiescloset.Model.Items;
import com.example.yashnanavati.catiescloset.Model.User;
import com.example.yashnanavati.catiescloset.Model.Package;
import com.example.yashnanavati.catiescloset.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Yash Nanavati on 11/29/2017.
 */

public class DonationViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Object> contents;
    Context context;

    private Integer sub_item_counter;
    private ExpandingList mExpandingList;
    private static String tag = "Donation_View_Adapter";
    private String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private User user;

    public TextView total;



    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_BUTTON = 2;


    public DonationViewAdapter(List<Object> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            case 1:
                return TYPE_CELL;
            default:
                return TYPE_BUTTON;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            /* Donate here, Clothing, and Toiletry */
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                mExpandingList = (ExpandingList) view.findViewById(R.id.expanding_list_main);
                createItems();
                return new RecyclerView.ViewHolder(view) {
                };
            }
            /* Total */
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                total = (TextView) view.findViewById(R.id.total);
                Log.d(tag, "type_cell");
                return new RecyclerView.ViewHolder(view) {
                };
            }
            /* Button */
            case TYPE_BUTTON:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_button, parent, false);
                Button button = (Button) view.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addItemtoDb(Integer.parseInt(total.getText().toString()));
                        Intent i = new Intent(context, DonationDeliveryActivity.class);
                        context.startActivity(i);
                    }
                });
                Log.d(tag, "type_button");
                return new RecyclerView.ViewHolder(view) {
                };
        }
        return null;
    }

    /*
     * Add the total number of items to DB (Package Model)
     */
    public void addItemtoDb(final int noOfItems) {
        /* Adding the donations path*/
        FirebaseDatabase.getInstance()
                .getReference().child("donations").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "|"))
                .push().setValue(new Donations(user_email, (new Timestamp(System.currentTimeMillis())).getTime(), new Package(noOfItems, false, null, null, null), new Cash(0)));

        /*Making separate objects for each item donated to be able to track each of them*/
        for(int item=0; item<noOfItems; item++){
            /* Making the item id --> <email_id>+<current_ts>+<item>*/
            String item_id = FirebaseAuth.getInstance().getCurrentUser().getEmail()+"_"+String.valueOf((new Timestamp(System.currentTimeMillis())).getTime())
            +"_"+String.valueOf(item);
            FirebaseDatabase.getInstance()
                    .getReference().child("Items").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "|"))
                    .push().setValue(new Items(item_id,"(future_use)placeholder_id"));
        }
    }

    private void createItems() {
        addItem("Boxes (Required)", new String[]{"Small", "Medium", "Large"}, R.color.gray, R.drawable.box);
        addItem("Youth girls", new String[]{"Top","Bottom", "Undergarments", "Shoes", "Others"}, R.color.yellow, R.drawable.youth_girl_icon);
        addItem("Teen girls", new String[]{"Top","Bottom", "Undergarments", "Shoes", "Others"}, R.color.red, R.drawable.teen_girl_icon);
        addItem("Youth boys", new String[]{"Top","Bottom", "Undergarments", "Shoes", "Others"}, R.color.cornflower_blue, R.drawable.youth_boy_icon);
        addItem("Teen boys", new String[]{"Top","Bottom", "Undergarments", "Shoes", "Others"}, R.color.bottomtab_0, R.drawable.teen_boy_icon);
        addItem("Toiletries", new String[]{"Shampoo", "Toothpaste", "Deodorant","Toothbrushes", "Others"}, R.color.ivory_dark, R.drawable.toiletry);
    }


    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);
            Log.i(tag, "Title: " + title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, title, view, subItems[i]);
            }
        }
    }


    private void configureSubItem(final ExpandingItem item, final String title, final View view, final String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        Log.i(tag, "title: " + title);

        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView) view.findViewById(R.id.counter);
                if(null != text) {
                    String counttext = (String) text.getText();
                    sub_item_counter = Integer.valueOf(counttext);
                    if (sub_item_counter > 0) {
                        sub_item_counter--;
                        if (!title.equals("Boxes (Required)") && null != total)
                            total.setText(String.valueOf(Integer.parseInt(total.getText().toString()) - 1));
                    }
                    Log.d(tag, "sub/" + subTitle + " " + sub_item_counter.toString());
                    text.setText(String.valueOf(sub_item_counter));
                }
            }
        });

        view.findViewById(R.id.addto_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView) view.findViewById(R.id.counter);
                if(null != text) {
                    String counttext = (String) text.getText();
                    sub_item_counter = Integer.valueOf(counttext);
                    sub_item_counter++;
                    if (!title.equals("Boxes (Required)") && null != total)
                        total.setText(String.valueOf(Integer.parseInt(total.getText().toString()) + 1));
                    Log.d(tag, "add/" + subTitle + " " + sub_item_counter.toString());
                    text.setText(String.valueOf(sub_item_counter));
                }
            }
        });
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
            case TYPE_BUTTON:
                break;
        }
    }
}
