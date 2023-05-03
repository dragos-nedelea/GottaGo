package com.example.gottago.Announces;

public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataDest;
    private String dataStart;
    private String dataImage;
    private String dataUserName;
    private String uid;
    private String key;
    private boolean switchValue;
    private int numberValue;
    private long timestamp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataClass(String dataTitle, int numberValue, String dataDesc, String dataDest, String dataStart, String dataImage, String uid, boolean switchValue, long timestamp, String dataUserName) {
        this.dataTitle = dataTitle;
        this.numberValue = numberValue;
        this.dataDesc = dataDesc;
        this.dataDest = dataDest;
        this.dataStart = dataStart;
        this.dataImage = dataImage;
        this.dataUserName = dataUserName;
        this.uid = uid;
        this.switchValue = switchValue;
        this.timestamp = timestamp;

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

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDataUserName() {
        return dataUserName;
    }

    public void setDataUserName(String dataUserName) {
        this.dataUserName = dataUserName;
    }

    public DataClass() {
        // required empty constructor for Firebase
    }
}

