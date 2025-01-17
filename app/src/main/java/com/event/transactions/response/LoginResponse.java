package com.event.transactions.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success")
    String success;
    @SerializedName("message")
    String message;
    @SerializedName("token")
    String token;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
