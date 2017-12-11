package com.example.yashnanavati.catiescloset.Model;

import java.util.Date;

/**
 * Created by Yash Nanavati on 12/1/2017.
 */

public class Package {
    private int noOfItems;
    private boolean delivered;
    private String dcAddress; // Donation Center Address
    private String originAddress; // Package origin Address
    private Date deliveryDate; //Date on which Package was delivered

    public Package(int noOfItems, boolean delivered, String dcAddress, String originAddress, Date deliveryDate){
        this.noOfItems = noOfItems;
        this.dcAddress = dcAddress;
        this.delivered = delivered;
        this.originAddress = originAddress;
        this.deliveryDate = deliveryDate;
    }

    public Package(){
        //Does Nothing
    }


    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getDcAddress() {
        return dcAddress;
    }

    public void setDcAddress(String dcAddress) {
        this.dcAddress = dcAddress;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
