package com.example.yashnanavati.catiescloset.Model;

import android.util.Log;

/**
 * @author yashnanavati
 * @version v1.0
 */
public class User {

    private String id;
    private String name;


    private static String tag = "User";

    public  User(){
        /*Do Nothing*/
    }
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

}
