package com.test.login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModelResponse {


    @SerializedName("status")
    @Expose
    private Boolean status;


    @SerializedName("message")
    @Expose
    private String message;
    public RegisterModelResponse() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public RegisterModelResponse(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

