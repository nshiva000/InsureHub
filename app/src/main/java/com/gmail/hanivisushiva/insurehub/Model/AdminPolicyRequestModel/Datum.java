package com.gmail.hanivisushiva.insurehub.Model.AdminPolicyRequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("ser_provider_id")
    @Expose
    private String serProviderId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service_start_date")
    @Expose
    private String serviceStartDate;
    @SerializedName("service_end_date")
    @Expose
    private String serviceEndDate;
    @SerializedName("card_no")
    @Expose
    private String cardNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSerProviderId() {
        return serProviderId;
    }

    public void setSerProviderId(String serProviderId) {
        this.serProviderId = serProviderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public String getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

}