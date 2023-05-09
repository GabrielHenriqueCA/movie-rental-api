package com.api.movierental.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_RENTAL")
public class RentalModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(nullable = false)
    private CustomerModel customer;

    @ManyToMany
    @Column(nullable = false)
    private List<MovieModel> movies;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateRental;

    @Column(nullable = false)
    private Date dateReturn;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private int numMovies;

    public UUID getId() {
        return id;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
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

    public int getNumMovies() {
        return numMovies;
    }

    public void setNumMovies(int numMovies) {
        this.numMovies = numMovies;
    }
}
