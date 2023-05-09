package com.api.movierental.services;

import com.api.movierental.dtos.RentalDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.exceptions.MovieNotFoundException;
import com.api.movierental.exceptions.RentalNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IRentalService {

    /**
     * Creates a new rental in the database with the provided rental data.
     * @param rentalDto the data for the rental to be created
     * @return a ResponseEntity containing the newly created rental data
     */
    ResponseEntity<RentalDto> saveRental(RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException;

    /**
     * Updates the rental with the provided UUID to match the given rental data.
     * @param rentalDto the updated rental data
     * @param uuid the UUID of the rental to be updated
     * @return a ResponseEntity containing the updated rental data
     */
    ResponseEntity<RentalDto> updateRental(RentalDto rentalDto, UUID uuid) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException;

    /**
     * Search all rental in database
     *
     * @param pageable pageable is the number of paginations you want
     * @return a page of everything found in the rental table
     */
    ResponseEntity<Page<RentalDto>> findAll(Pageable pageable);

    /**
     * Retrieves the rental with the provided UUID from the database.
     * @param id the UUID of the rental to retrieve
     * @return a ResponseEntity containing the rental data for the specified UUID
     */
    ResponseEntity<RentalDto> findById(UUID id) throws RentalNotFoundException;

    /**
     * Deletes the rental with the provided UUID from the database.
     * @param id the UUID of the rental to delete
     * @return a ResponseEntity containing a message indicating whether the rental was successfully deleted
     */
    ResponseEntity<String> deleteRental(UUID id) throws RentalNotFoundException;

}
