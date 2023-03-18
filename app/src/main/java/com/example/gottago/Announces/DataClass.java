package com.example.gottago.Announces;

public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataDest;
    private String dataImage;

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataDest() {
        return dataDest;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClass(String dataTitle, String dataDesc, String dataDest, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataDest = dataDest;
        this.dataImage = dataImage;
    }

    public DataClass() {

    }
}
