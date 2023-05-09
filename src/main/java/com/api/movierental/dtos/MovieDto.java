package com.api.movierental.dtos;

import jakarta.validation.constraints.*;

public class MovieDto {
    @NotNull(message = "name movie cannot be null")
    @Size(min = 2, max = 50, message = "the name of the movie must be between 2 and 50 characters")
    private String movieName;

    @NotNull(message = "stock movie cannot be null")
    @Min(value = 0, message = "stock must be positive")
    private Integer stock;

    @NotNull(message = "price rent movie cannot be null")
    @DecimalMax(value = "99.99", message = "the price must have a maximum of two digits")
    @Positive(message = "the movie rental amount must be positive")
    private Double priceRent;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPriceRent() {
        return priceRent;
    }

    public void setPriceRent(Double priceRent) {
        this.priceRent = priceRent;
    }
}
