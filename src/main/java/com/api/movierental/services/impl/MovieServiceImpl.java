package com.api.movierental.services.impl;

import com.api.movierental.converters.MovieConverter;
import com.api.movierental.dtos.MovieDto;
import com.api.movierental.exceptions.MovieNotFoundException;
import com.api.movierental.exceptions.ResourceAlreadyExistsException;
import com.api.movierental.exceptions.ResourceNotFoundException;
import com.api.movierental.models.MovieModel;
import com.api.movierental.repositories.MovieRepository;
import com.api.movierental.services.IMovieService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MovieServiceImpl implements IMovieService {

    private final MovieRepository movieRepository;
    private final MovieConverter movieConverter;

    private static final String ERRO_TO_RETRIVE_MOVIES = "\"Failed to retrieve movies: {}\"";

    public MovieServiceImpl(MovieRepository movieRepository, MovieConverter movieConverter) {
        this.movieRepository = movieRepository;
        this.movieConverter = movieConverter;
    }

    @Override
    public ResponseEntity<MovieDto> saveMovie(@Valid final MovieDto movieDto) {
        if (movieDto == null) {
            log.warn("Failed to save movie. MovieDto is null.");
            return ResponseEntity.badRequest().build();
        }

        if (movieRepository.existsByMovieName(movieDto.getMovieName())) {
            log.warn("Failed to save movie. Movie with name {} already exists.", movieDto.getMovieName());
            throw new ResourceAlreadyExistsException("Movie with name " + movieDto.getMovieName() + " already exists.");
        }

        MovieModel movieModel = movieConverter.mapToMovieModel(movieDto);
        movieRepository.save(movieModel);
        log.info("Movie saved successfully: {}", movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<MovieDto> updateMovie(final UUID id, final MovieDto movieDto) {
        if (movieDto == null) {
            log.warn("Failed to update movie. movieDto is null.");
            return ResponseEntity.badRequest().build();
        }

        MovieModel optionalMovieModel = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Failed to update movie. Movie with ID {} not found.", id);
                    return new ResourceNotFoundException("Movie with ID " + id + " not found.");
                });


        movieRepository.save(movieConverter.mapToMovieModel(movieDto, optionalMovieModel));
        log.info("Movie updated successfully: {}", movieDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<MovieDto>> findAllMovies(final Pageable pageable) {
        try {
            Page<MovieModel> moviesModels = movieRepository.findAll(pageable);
            Page<MovieDto> movieDtos = movieConverter.convertToDtoPage(moviesModels);

            return ResponseEntity.ok(movieDtos);
        } catch (Exception e) {
            log.error(ERRO_TO_RETRIVE_MOVIES, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<MovieDto> findMoviesById(final UUID id) throws MovieNotFoundException {
        Optional<MovieModel> movieOptional = movieRepository.findById(id);

        return movieOptional.map(movieConverter::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MovieNotFoundException(String.format("Failed to find movie with id: %s", id)));
    }

    @Override
    public ResponseEntity<Page<MovieDto>> findByNameContainingIgnoreCase(final String movieName, final Pageable pageable) {
        try {
            Page<MovieModel> movieOptional = movieRepository.findByMovieNameContainingIgnoreCase(movieName, pageable);
            Page<MovieDto> movieDto = movieConverter.convertToDtoPage(movieOptional);

            return ResponseEntity.ok(movieDto);
        } catch (Exception e) {
            log.error(ERRO_TO_RETRIVE_MOVIES, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Slice<MovieDto>> findTop3ByMovieNameContainingIgnoreCase(final String name, final Pageable pageable) {
        try {
            Slice<MovieModel> movies = movieRepository.findTop3ByMovieNameContainingIgnoreCase(name, pageable);
            Slice<MovieDto> moviesDto = movies.map(movieConverter::convertToDto);

            return ResponseEntity.ok(moviesDto);
        } catch (Exception e) {
            log.error(ERRO_TO_RETRIVE_MOVIES, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Page<MovieDto>> findFirst10ByMovieNameContainingIgnoreCase(final String movieName, final Pageable pageable) {
        try {
            Page<MovieModel> movies = movieRepository.findFirst10ByMovieNameContainingIgnoreCase(movieName, pageable);
            Page<MovieDto> moviesDto = movies.map(movieConverter::convertToDto);
            return ResponseEntity.ok(moviesDto);
        } catch (Exception e) {
            log.error(ERRO_TO_RETRIVE_MOVIES, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Page<MovieDto>> findByStockGreaterThan(final Integer stock, final Pageable pageable) {
        try {
            Page<MovieModel> movies = movieRepository.findByStockGreaterThan(stock, pageable);
            Page<MovieDto> moviesDto = movies.map(movieConverter::convertToDto);
            return ResponseEntity.ok(moviesDto);
        } catch (Exception e) {
            log.error(ERRO_TO_RETRIVE_MOVIES, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> deleteMovie(final UUID id) throws MovieNotFoundException {
        MovieModel movie = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Failed to find movie with id: {}", id);
                    return new MovieNotFoundException(String.format("Failed to find movie with id: %s", id));
                });

        movieRepository.delete(movie);
        log.warn("Movie deleted successfully {}", movie);

        return ResponseEntity.ok("Movie deleted successfully");
    }
}