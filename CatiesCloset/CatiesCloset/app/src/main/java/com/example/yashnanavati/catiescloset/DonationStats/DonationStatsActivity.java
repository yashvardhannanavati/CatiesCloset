package com.example.yashnanavati.catiescloset.DonationStats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yashnanavati.catiescloset.Adapter.DonationStatsAdapter;
import com.example.yashnanavati.catiescloset.Model.Donations;
import com.example.yashnanavati.catiescloset.Model.Package;
import com.example.yashnanavati.catiescloset.Model.PackageDataModel;
import com.example.yashnanavati.catiescloset.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DonationStatsActivity extends AppCompatActivity {

    private static final ArrayList<PackageDataModel> dataModels = new ArrayList<>();
    ListView listView;
    private static DonationStatsAdapter adapter;
    private final static List<Package> listPackage = new ArrayList<>();
    private static boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_stats);

        Intent i = getIntent();
        flag = i.getExtras().getBoolean("status");

        //Setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //1. Getting reference of the listview
        listView = (ListView) findViewById(R.id.list);
        dataModels.clear();

        //2. Getting data from Firebase to update the list of donated packages based on Delivered flag
        FirebaseDatabase.getInstance().getReference().child("donations").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "|"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final boolean param = flag;
                        if (dataSnapshot.exists()) {
                            for (final DataSnapshot postDataSnapshot : dataSnapshot.getChildren()) {
                                Donations d = postDataSnapshot.getValue(Donations.class);
                                if (null != d) {
                                    listPackage.add(d.getPkg());
                                    if (d.getPkg().isDelivered() == param) {
                                        dataModels.add(new PackageDataModel(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date(d.getTimestamp())), (param ? "Delivery Status: Delivered" : "Delivery Status: Not Delivered"), String.valueOf(d.getPkg().getNoOfItems()), "Boston"));
                                    }
                                }
                            }
                            adapter = new DonationStatsAdapter(dataModels, getApplicationContext());
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        //3. Showing the pop-up on clicking an item on the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PackageDataModel dataModel = dataModels.get(position);

                Snackbar.make(view, dataModel.getName() + "\n" + dataModel.getType() + " #Items: " + dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }


}
