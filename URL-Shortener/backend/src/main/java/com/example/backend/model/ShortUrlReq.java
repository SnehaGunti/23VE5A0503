package com.example.backend.model;

import lombok.Data;

@Data
public class ShortUrlReq {
    private String url;
    private Integer validity;
    private String shortcode;
}
