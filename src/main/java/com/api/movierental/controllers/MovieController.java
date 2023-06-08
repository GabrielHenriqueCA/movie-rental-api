package com.api.movierental.controllers;

import com.api.movierental.dtos.MovieDto;
import com.api.movierental.exceptions.MovieNotFoundException;
import com.api.movierental.services.impl.MovieServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/locator/movies")
public class MovieController {

    private static final Logger log = LogManager.getLogger(MovieController.class);

    private final MovieServiceImpl movieService;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Page<MovieDto>> getAllMovies(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        log.info("Received request to get all movies");
        return movieService.findAllMovies(pageable);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable(value = "id") final UUID id) throws MovieNotFoundException {
        log.info("Received request to get movie by id: {}", id);
        return movieService.findMoviesById(id);
    }

    @GetMapping("/three")
    public ResponseEntity<Slice<MovieDto>> getTopThreeMoviesByName(@RequestParam final String movieName, final Pageable pageable) {
        log.info("Received request to get top three movies by name: {}", movieName);
        return movieService.findTop3ByMovieNameContainingIgnoreCase(movieName, pageable);
    }

    @GetMapping("/ten")
    public ResponseEntity<Page<MovieDto>> getFirstTenMoviesByName(@RequestParam final String movieName, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        log.info("Received request to get first ten movies by name: {}", movieName);
        return movieService.findFirst10ByMovieNameContainingIgnoreCase(movieName, pageable);
    }

    @GetMapping("/stock")
    public ResponseEntity<Page<MovieDto>> getMoviesByStockGreaterThan(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable, @RequestParam final Integer stock) {
        log.info("Received request to get movies by stock greather than: {}", stock);
        return movieService.findByStockGreaterThan(stock, pageable);
    }

    @GetMapping("/name")
    public ResponseEntity<Page<MovieDto>> getMovieByName(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable, @RequestParam final String movieName) {
        log.info("Received request to get movie by name: {}", movieName);
        return movieService.findByNameContainingIgnoreCase(movieName, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity<MovieDto> createMovie(@RequestBody @Valid final MovieDto movieDto) {
        log.info("Received request to create movie: {}", movieDto);
        return movieService.saveMovie(movieDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable(value = "id") UUID id, @RequestBody @Valid final MovieDto movieDto) {
        log.info("Received request to update movie with id: {}", id);
        return movieService.updateMovie(id, movieDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable(value = "id") final UUID id) throws MovieNotFoundException {
        log.info("Received request to delete movie woth id: {}", id);
        return movieService.deleteMovie(id);
    }
}