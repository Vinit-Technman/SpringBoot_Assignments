package com.example.Employee_Management_System;

import org.springframework.http.HttpStatus;

public class ApiManager<T> {
    private T data;
    private int statusCode;
    private String error;
    private String message;

    public ApiManager() {
    }

    public ApiManager(T data, HttpStatus statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode.value();
        this.message = message;
    }

    public ApiManager(HttpStatus statusCode, String error, String message) {
        this.statusCode = statusCode.value();
        this.error = error;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
