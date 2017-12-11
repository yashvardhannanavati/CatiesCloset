package com.example.yashnanavati.catiescloset.DonationModule;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yashnanavati.catiescloset.R;

public class TruckPickActivity extends AppCompatActivity {

    private Button callbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_pick);

        callbutton = (Button) findViewById(R.id.callbutton);

        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = "978-957-2200"; //CATIE'S CLOSET PHONE NO.
                Intent phoneCallMom = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNo));
                startActivity(phoneCallMom);

            }
        });

    }

}
