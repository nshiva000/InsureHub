package com.gmail.hanivisushiva.insurehub.Model.BannerImagesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("baid")
    @Expose
    private String baid;
    @SerializedName("banner")
    @Expose
    private String banner;

    public String getBaid() {
        return baid;
    }

    public void setBaid(String baid) {
        this.baid = baid;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}