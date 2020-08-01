package com.gmail.hanivisushiva.insurehub.Model.ServiceProviderAllBillsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("bid")
    @Expose
    private String bid;
    @SerializedName("bill_amt")
    @Expose
    private String billAmt;
    @SerializedName("discount_amt")
    @Expose
    private String discountAmt;
    @SerializedName("total_paid")
    @Expose
    private String totalPaid;
    @SerializedName("cus_id")
    @Expose
    private String cusId;
    @SerializedName("service_pro_id")
    @Expose
    private String serviceProId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("categery_id")
    @Expose
    private String categeryId;
    @SerializedName("card_no")
    @Expose
    private String cardNo;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBillAmt() {
        return billAmt;
    }

    public void setBillAmt(String billAmt) {
        this.billAmt = billAmt;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getServiceProId() {
        return serviceProId;
    }

    public void setServiceProId(String serviceProId) {
        this.serviceProId = serviceProId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCategeryId() {
        return categeryId;
    }

    public void setCategeryId(String categeryId) {
        this.categeryId = categeryId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

}