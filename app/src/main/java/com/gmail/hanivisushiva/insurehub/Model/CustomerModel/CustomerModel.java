package com.gmail.hanivisushiva.insurehub.Model.CustomerModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}