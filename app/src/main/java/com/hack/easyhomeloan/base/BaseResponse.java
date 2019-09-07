package com.hack.easyhomeloan.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("status")
    @Expose
    public BaseStatusModel status = new BaseStatusModel();

    /**
     * @return
     */
    public BaseStatusModel getStatus() {
        return status;
    }

    public class BaseStatusModel {

        @SerializedName("code")
        @Expose
        private int statusCode;
        @SerializedName("message")
        @Expose
        private String statusMessage;

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        @Override
        public String toString() {
            return "statusCode=" + statusCode +
                    ", statusMessage='" + statusMessage;
        }
    }
}
