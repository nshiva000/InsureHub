package com.gmail.hanivisushiva.insurehub.Model.CustomerPolicy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("documents")
    @Expose
    private String documents;
    @SerializedName("documentspath")
    @Expose
    private String documentspath;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getDocumentspath() {
        return documentspath;
    }

    public void setDocumentspath(String documentspath) {
        this.documentspath = documentspath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}