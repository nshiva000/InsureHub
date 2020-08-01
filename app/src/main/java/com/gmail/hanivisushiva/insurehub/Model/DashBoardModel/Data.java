package com.gmail.hanivisushiva.insurehub.Model.DashBoardModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("customers")
    @Expose
    private Integer customers;
    @SerializedName("Service Providers ")
    @Expose
    private Integer serviceProviders;
    @SerializedName("Bills")
    @Expose
    private Integer bills;

    public Integer getCustomers() {
        return customers;
    }

    public void setCustomers(Integer customers) {
        this.customers = customers;
    }

    public Integer getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(Integer serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public Integer getBills() {
        return bills;
    }

    public void setBills(Integer bills) {
        this.bills = bills;
    }

}