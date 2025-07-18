package com.example.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class URL {
    @Id
    private String Shortcode;
    @Column(nullable = false)
    private String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiryAt;
    private int ClickCount;
}
