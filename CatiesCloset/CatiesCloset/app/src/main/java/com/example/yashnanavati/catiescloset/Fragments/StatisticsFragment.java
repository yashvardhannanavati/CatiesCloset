package com.example.yashnanavati.catiescloset.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yashnanavati.catiescloset.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {


    public static StatisticsFragment newInstance() {

        return new StatisticsFragment();
    }

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);
        chart = (BarChart) v.findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Number of Children Served");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);
        chart.setDescription("");

        return v;
    }

    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(4000f, 0));
        BARENTRY.add(new BarEntry(6000f, 1));
        BARENTRY.add(new BarEntry(13000f, 2));
        BARENTRY.add(new BarEntry(16000f, 3));
        BARENTRY.add(new BarEntry(24500f, 4));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("2012");
        BarEntryLabels.add("2013");
        BarEntryLabels.add("2014");
        BarEntryLabels.add("2015");
        BarEntryLabels.add("2016");

    }


}
