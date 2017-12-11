package com.example.yashnanavati.catiescloset.Model;

/**
 * Created by Yash Nanavati on 12/1/2017.
 */

public class Donations {
    private String id;
    private long timestamp;
    private Package pkg;
    private Cash cash;

    public Donations(){
        /* Do Nothing*/
    }

    public Donations(String id, long timestamp, Package pkg, Cash cash){
        this.id = id;
        this.timestamp = timestamp;
        this.pkg = pkg;
        this.cash = cash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Package getPkg() {
        return pkg;
    }

    public void setPkg(Package pkg) {
        this.pkg = pkg;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }
}
