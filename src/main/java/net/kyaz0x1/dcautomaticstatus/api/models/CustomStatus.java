package net.kyaz0x1.dcautomaticstatus.api.models;

import com.google.gson.annotations.SerializedName;

public class CustomStatus {

    @SerializedName("custom_status")
    private Status status;

    public CustomStatus(Status status){
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}