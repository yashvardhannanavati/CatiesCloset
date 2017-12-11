package com.example.yashnanavati.catiescloset.Adapter;

/**
 * Created by haris on 12/1/2017.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Huseyin Ozer.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1){
            return new Page1Fragment();
        }
        if(position==2){
            return new Page2Fragment();
        }
        if(position==3){
            return new Page3Fragment();
        }

        return new PageFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
