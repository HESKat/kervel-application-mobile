package com.example.kervel.modele;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private boolean error;
   // @SerializedName("message")
    private String message;
    //@SerializedName("user")
    private LoginModel user;

    public LoginResponse(boolean error, String message, LoginModel user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }
    public LoginResponse(){

    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(LoginModel user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public LoginModel getUser() {
        return user;
    }
}
