package com.gmail.hanivisushiva.insurehub.Model.ServiceProviderDashBoardModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("customers")
    @Expose
    private Integer customers;
    @SerializedName("Bills")
    @Expose
    private Integer bills;

    public Integer getCustomers() {
        return customers;
    }

    public void setCustomers(Integer customers) {
        this.customers = customers;
    }

    public Integer getBills() {
        return bills;
    }

    public void setBills(Integer bills) {
        this.bills = bills;
    }

}