package com.example.backend.model;

import lombok.Data;

@Data
public class GeneralRes {
    private boolean success;
    private String message;
    private Object data;

    public GeneralRes() {}

    public GeneralRes(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static GeneralRes success(String message, Object data) {
        return new GeneralRes(true, message, data);
    }

    public static GeneralRes error(String message) {
        return new GeneralRes(false, message, null);
    }


}
