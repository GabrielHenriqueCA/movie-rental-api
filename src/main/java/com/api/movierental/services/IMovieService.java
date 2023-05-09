package com.api.movierental.services;

import com.api.movierental.dtos.MovieDto;
import com.api.movierental.exceptions.MovieNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IMovieService {

    /**
     * Creates a new movie and saves it to the database.
     *
     * @param movieDto DTO containing the movie information to be saved.
     * @return A ResponseEntity containing the saved movie DTO.
     */
    ResponseEntity<MovieDto> saveMovie(MovieDto movieDto);

    /**
     * Updates an existing movie with the provided movieDto and saves it to the database.
     *
     * @param id       The ID of the movie to be updated.
     * @param movieDto DTO containing the updated movie information.
     * @return A ResponseEntity containing the updated movie DTO, or an appropriate error response.
     * @throws MovieNotFoundException if the movie with the specified ID is not found in the database.
     */
    ResponseEntity<MovieDto> updateMovie(UUID id, MovieDto movieDto) throws MovieNotFoundException;

    /**
     * Retrieves all movies from the database.
     *
     * @param pageable The number of pages to display and the number of items per page.
     * @return A page of all movies found in the database.
     */
    ResponseEntity<Page<MovieDto>> findAllMovies(Pageable pageable);

    /**
     * Searches for movies by name in the database.
     *
     * @param name     The name of the movie to search for.
     * @param pageable The number of pages to display and the number of items per page.
     * @return A page of movies with the specified name.
     * @throws MovieNotFoundException if no movies with the specified name are found in the database.
     */
    ResponseEntity<Page<MovieDto>> findByNameContainingIgnoreCase(String name, Pageable pageable) throws MovieNotFoundException;

    /**
     * Retrieves the movie with the specified ID from the database.
     *
     * @param id The ID of the movie to retrieve.
     * @return A ResponseEntity containing the movie DTO with the specified ID.
     * @throws MovieNotFoundException if the movie with the specified ID is not found in the database.
     */
    ResponseEntity<MovieDto> findMoviesById(UUID id) throws MovieNotFoundException;

    /**
     * Searches for the top 3 movies by name in the database.
     *
     * @param movieName The name of the movie to search for.
     * @param pageable  The number of pages to display and the number of items per page.
     * @return A slice of the top 3 movies with the specified name.
     */
    ResponseEntity<Slice<MovieDto>> findTop3ByMovieNameContainingIgnoreCase(String movieName, Pageable pageable);

    /**
     * Searches for the first 10 movies by name in the database.
     *
     * @param movieName The name of the movie to search for.
     * @param pageable  The number of pages to display and the number of items per page.
     * @return A page of the first 10 movies with the specified name.
     */
    ResponseEntity<Page<MovieDto>> findFirst10ByMovieNameContainingIgnoreCase(String movieName, Pageable pageable);

    /**
     * Searches for movies with a stock greater than the specified value in the database.
     *
     * @param stock    The value of the stock to search for.
     * @param pageable The number of pages to display and the number of items per page.
     * @return A page of movies with a stock greater than the specified value.
     */
    ResponseEntity<Page<MovieDto>> findByStockGreaterThan(Integer stock, Pageable pageable);

    /**
     * Deletes the specified movie from the database.
     *
     * @param id The ID of the movie to delete.
     * @return A ResponseEntity containing the status of the operation.
     * @throws MovieNotFoundException if the movie with the specified ID is not found in the database.
     */
    ResponseEntity<String> deleteMovie(UUID id) throws MovieNotFoundException;

}
