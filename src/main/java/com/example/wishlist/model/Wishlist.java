package com.example.wishlist.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class Wishlist {
    private Long id;

    @NotBlank(message = "Titel må ikke være tom.")
    @Size(max = 100, message = "Titel må være højst på 100 tegn")
    private String title;

    private LocalDateTime createdAt;
    private Boolean isShared;
    private String shareToken;

    public Wishlist() {
    }

    public Wishlist(Long id, String title, LocalDateTime createdAt, Boolean isShared, String shareToken) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.isShared = isShared;
        this.shareToken = shareToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }
}