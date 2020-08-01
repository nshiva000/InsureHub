package com.gmail.hanivisushiva.insurehub.Model.AdminOffersModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("date")
    @Expose
    private String date;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}