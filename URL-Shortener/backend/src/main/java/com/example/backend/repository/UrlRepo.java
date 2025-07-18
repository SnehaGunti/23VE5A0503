package com.example.backend.repository;

import com.example.backend.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepo extends JpaRepository<URL,String> {
}
