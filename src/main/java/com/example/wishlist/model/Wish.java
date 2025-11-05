package com.example.wishlist.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Wish {
    private Long id;

    @NotBlank(message = "Titel må ikke være tom.")
    @Size(max = 255, message = "Titel må være højst på 255 tegn")
    private String title;

    @DecimalMin(value = "0.0", message = "Pris skal være 0 eller højere")
    private BigDecimal price;

    private String url;
    private Long wishlistId;
    private LocalDateTime createdAt;

    // Constructors
    public Wish() {}

    public Wish(String title, BigDecimal price, String url, Long wishlistId) {
        this.title = title;
        this.price = price;
        this.url = url;
        this.wishlistId = wishlistId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Long getWishlistId() { return wishlistId; }
    public void setWishlistId(Long wishlistId) { this.wishlistId = wishlistId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}