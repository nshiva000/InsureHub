package com.gmail.hanivisushiva.insurehub.Model.CustomerServicePartners;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("sname")
    @Expose
    private String sname;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

}