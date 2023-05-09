package com.api.movierental.repositories;

import com.api.movierental.models.MovieModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, UUID> {

    Page<MovieModel> findByMovieNameContainingIgnoreCase(final String name, final Pageable pageable);
    Optional<MovieModel> findByMovieNameContainingIgnoreCase(final String name);

    Slice<MovieModel> findTop3ByMovieNameContainingIgnoreCase( final String movieName, final Pageable pageable);

    Page<MovieModel> findFirst10ByMovieNameContainingIgnoreCase(final String movieName, final Pageable pageable);

    Page<MovieModel> findByStockGreaterThan(final Integer stock, final Pageable pageable);

    boolean existsByMovieName(final String name);

}
