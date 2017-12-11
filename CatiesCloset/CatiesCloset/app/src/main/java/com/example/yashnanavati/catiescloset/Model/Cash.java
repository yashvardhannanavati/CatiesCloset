package com.example.yashnanavati.catiescloset.Model;

import android.util.Log;

/**
 * Created by Yash Nanavati on 12/1/2017.
 */

public class Cash {
    String name;
    String email;
    String address;
    String country;
    String state;
    String zipCode;
    double amount;

    public Cash(double amount){
        this.amount = amount;
        Log.i("tag", "init");
    }

    public Cash(){
        //Does Nothing
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCash(String name, String email, String address,
                        String country, String state, String zipCode,
                        double amount) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
        this.amount = amount;
        Log.i("tag", "Cash: " + name + " " + email + " " + address + " " + country + " " + state + " " + zipCode + " " + String.valueOf(amount));    }
}

