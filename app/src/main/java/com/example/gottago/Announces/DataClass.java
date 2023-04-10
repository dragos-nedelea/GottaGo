package com.example.gottago.Announces;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataDest;
    private String dataImage;
    private String uid;
    private String key;
    private boolean switchValue;
    private int numberValue;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataClass(String dataTitle, int numberValue, String dataDesc, String dataDest, String dataImage, String uid, boolean switchValue) {
        this.dataTitle = dataTitle;
        this.numberValue = numberValue;
        this.dataDesc = dataDesc;
        this.dataDest = dataDest;
        this.dataImage = dataImage;
        this.uid = uid;
        this.switchValue = switchValue;

    }

    // getters and setters

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getDataDest() {
        return dataDest;
    }

    public void setDataDest(String dataDest) {
        this.dataDest = dataDest;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(boolean switchValue) {
        this.switchValue = switchValue;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }

    public DataClass() {
        // required empty constructor for Firebase
    }
}
