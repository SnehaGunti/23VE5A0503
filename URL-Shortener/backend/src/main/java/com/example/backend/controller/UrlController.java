package com.example.backend.controller;

import com.example.backend.model.GeneralRes;
import com.example.backend.model.ShortUrlReq;
import com.example.backend.model.ShortUrlRes;
import com.example.backend.model.ShortUrlStatusRes;
import com.example.backend.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/shorturls")
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping
    public ResponseEntity<GeneralRes> createShortUrl(@RequestBody ShortUrlReq request) {
        try {
            ShortUrlRes response = service.createShortUrl(request);
            return ResponseEntity.ok(GeneralRes.success("Short URL created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GeneralRes.error(e.getMessage()));
        }
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<GeneralRes> getStats(@PathVariable String shortcode) {
        try {
            ShortUrlStatusRes response = service.getStats(shortcode);
            return ResponseEntity.ok(GeneralRes.success("Short URL stats retrieved", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GeneralRes.error(e.getMessage()));
        }
    }

    @GetMapping("/r/{shortcode}")
    public ResponseEntity<GeneralRes> redirect(@PathVariable String shortcode, HttpServletResponse response) throws IOException {
        try {
            String originalUrl = service.redirectToOriginal(shortcode);
            response.sendRedirect(originalUrl);
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GeneralRes.error(e.getMessage()));
        }
    }
}