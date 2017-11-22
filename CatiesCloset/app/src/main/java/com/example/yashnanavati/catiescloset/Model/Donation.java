package com.example.yashnanavati.catiescloset.Model;

import android.util.Log;

import com.example.yashnanavati.catiescloset.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Donation {
    public HashMap<String, HashMap<String, String>> Boys;
    public HashMap<String, HashMap<String, String>> Girls;
    public HashMap<String, String> Toiletry;
    public String UserId;

    private static String donationFlag = "Donation";

    /* initialize Donation object */
    public Donation(String userId) {
        this.Boys = new HashMap<String, HashMap<String, String>>();
        this.Girls = new HashMap<String, HashMap<String, String>>();
        this.Toiletry = new HashMap<String, String>();
        this.UserId = userId;
        Log.i(donationFlag, "Initialize... UserId set to " + userId);
    }

    /* generate DateTime */
    private String generateDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH/mm/ss");
        String dateTime = sdf.toString();

        return dateTime;
    }

    /* generate ItemId for an item */
    private String generateItemId(String item, Integer count) {
        String dateTime=generateDateTime();
        String itemId=this.UserId+"_"+dateTime+"_"+item+"_"+count.toString();

        Log.i(donationFlag, "Generate ItemId... ItemId set to " + itemId);
        return itemId;
    }

    /* method set clothing size and quantity*/
    public void setClothing(String gender, String item, Integer size, Integer qty) {
        HashMap<String, String> Item=new HashMap<String, String>();
        Item.put(size.toString(), qty.toString());
        Log.i(donationFlag, item + " Put... Size(Key): " + size.toString() + " Quantity(Value): " + qty.toString());

        HashMap<String, String> ItemId=new HashMap<String, String>();
        for (Integer i=0; i<qty;i++) {
            String itemKey = item+i.toString();
            String itemValue = generateItemId(item, i);
            Log.i(donationFlag, item + " Put... ItemKey(Key): " + itemKey + " ItemValue(Value): " + itemValue);
            ItemId.put(itemKey, itemValue);
        }

        String itemId = item+"Id";

        if (gender == "Boys") {
            this.Boys.put(item, Item);
            this.Boys.put(itemId, ItemId);
            Log.i(donationFlag, "Boys Put... " + item);
        } else if (gender == "Girls") {
            this.Girls.put(item, Item);
            this.Girls.put(itemId, ItemId);
            Log.i(donationFlag, "Girls Put... " + item);
        }
    }

    /* method get clothing quantity */
    public String getClothing(String gender, String item, Integer size) {
        String qty;
        HashMap<String, String> Item=new HashMap<String, String>();
        if (gender == "Boys") {
            Item=this.Boys.get(item);
            Log.i(donationFlag, "Boys Get... " + item);
        } else if (gender == "Girls") {
            Item=this.Girls.get(item);
            Log.i(donationFlag, "Girls Get... " + item);
        }
        qty=Item.get(size.toString());
        return qty;
    }


    /* method set toiletry quantity */
    public void setToiletry(String item, Integer qty) {
        this.Toiletry.put(item, qty.toString());
        Log.i(donationFlag, "Toiletry Put... Item(Key): " + item + " Quantity(Value): " + qty.toString());
    }


    /* method get toiletry quantity */
    public String getToiletry(String item) {
        String qty;
        qty = this.Toiletry.get(item);
        Log.i(donationFlag, "Toiletry Get... " + item);
        return qty;
    }
}