package com.example.yashnanavati.catiescloset.Model;

/**
 * Created by Yash Nanavati on 12/1/2017.
 */

public class Items {
    private String id;
    private String studentId;

    public Items(String id, String studentId){
        this.id = id;
        this.studentId = studentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
