package com.example.yashnanavati.catiescloset.Model;

import com.example.yashnanavati.catiescloset.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yash Nanavati on 11/17/2017.
 */

public class Donation {
    public String typeOfDonation;
    public String descDonation;
    public int photoCard;

    public Donation(String typeOfDonation, String descDonation, int photoCard) {
        this.typeOfDonation = typeOfDonation;
        this.descDonation = descDonation;
        this.photoCard = photoCard;
    }

}
