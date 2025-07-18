package com.example.backend.service;

import com.example.backend.model.ShortUrlReq;
import com.example.backend.model.ShortUrlRes;
import com.example.backend.model.ShortUrlStatusRes;
import com.example.backend.model.URL;
import com.example.backend.repository.UrlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepo repo;

    public ShortUrlRes createShortUrl(ShortUrlReq req) {
        String shortcode = Optional.ofNullable(req.getShortcode())
                .orElse(UUID.randomUUID().toString().substring(0, 6));

        if (repo.existsById(shortcode)) {
            throw new RuntimeException("Shortcode already exists");
        }

        URL entity = new URL();
        entity.setShortcode(shortcode);
        entity.setOriginalUrl(req.getUrl());
        entity.setCreatedAt(LocalDateTime.now());

        int validityMins = Optional.ofNullable(req.getValidity()).orElse(30);
        entity.setExpiryAt(LocalDateTime.now().plusMinutes(validityMins));
        entity.setClickCount(0);

        repo.save(entity);

        ShortUrlRes response = new ShortUrlRes();
        response.setShortLink("http://localhost:8080/shorturls/r/" + shortcode);
        response.setExpiry(entity.getExpiryAt().toString());

        return response;
    }

    public ShortUrlStatusRes getStats(String shortcode) {
        URL entity = repo.findById(shortcode)
                .orElseThrow(() -> new RuntimeException("Shortcode not found"));

        ShortUrlStatusRes res = new ShortUrlStatusRes();
        res.setOriginalUrl(entity.getOriginalUrl());
        res.setShortLink("http://localhost:8080/shorturls/r/" + shortcode);
        res.setCreationDate(entity.getCreatedAt().toString());
        res.setExpiryDate(entity.getExpiryAt().toString());
        res.setClickCount(entity.getClickCount());
        return res;
    }

    public String redirectToOriginal(String shortcode) {
        URL entity = repo.findById(shortcode)
                .orElseThrow(() -> new RuntimeException("Invalid shortcode"));

        if (entity.getExpiryAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Short URL expired");
        }

        entity.setClickCount(entity.getClickCount() + 1);
        repo.save(entity);

        return entity.getOriginalUrl();
    }
}