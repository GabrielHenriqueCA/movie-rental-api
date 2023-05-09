package com.api.movierental.dtos;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;
import java.util.List;

public class RentalDto {

    @NotNull(message = "a customer must be selected")
    private CustomerDto customer;

    @NotNull(message = "at least one movie must be selected")
    private List<MovieDto> movies;

    @NotNull(message = "lease date must not be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRental;

    @NotNull(message = "return date must not be null")
    @Temporal(TemporalType.TIMESTAMP)
    @Future(message = "the return date must be in the future")
    private Date dateReturn;

    @NotNull(message = "value cannot be null")
    @DecimalMax(value = "99.99", message = "the price must have a maximum of two digits")
    @Positive(message = "lease amount must be positive")
    private Double value;


    int numMovies;

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<MovieDto> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDto> movies) {
        this.movies = movies;
    }

    public Date getDateRental() {
        return dateRental;
    }

    public void setDateRental(Date dateRental) {
        this.dateRental = dateRental;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
