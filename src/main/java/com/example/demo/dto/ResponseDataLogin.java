package com.example.demo.dto;

public class ResponseDataLogin<T> {
    private int statusCode;
    private T payload;
    private String message;
    private String token;

    public ResponseDataLogin() {

    }

    public ResponseDataLogin(int statusCode, T payload, String message, String token) {
        this.statusCode = statusCode;
        this.payload = payload;
        this.message = message;
        this.token = token;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
