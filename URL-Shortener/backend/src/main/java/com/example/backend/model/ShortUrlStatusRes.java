package com.example.backend.model;

import lombok.Data;

@Data
public class ShortUrlStatusRes {
    private String originalUrl;
    private String shortLink;
    private String creationDate;
    private String expiryDate;
    private int clickCount;

}
