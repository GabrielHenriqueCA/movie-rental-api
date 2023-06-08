package com.api.movierental.controllers;

import com.api.movierental.dtos.RentalDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.exceptions.MovieNotFoundException;
import com.api.movierental.exceptions.RentalNotFoundException;
import com.api.movierental.services.impl.RentalServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/locator/rentals")
public class RentalController {

    private static final Logger log = LogManager.getLogger(RentalController.class);
    private final RentalServiceImpl rentalService;

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Page<RentalDto>> getAllRentals(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        log.info("Received request to get all rentals");
        return rentalService.findAll(pageable);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable(value = "id") final UUID id) throws RentalNotFoundException {
        log.info("Received request to get rental by id: {}", id);
        return rentalService.findById(id);
    }

    @PostMapping("create")
    public ResponseEntity<RentalDto> createRental(@RequestBody @Valid final RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        log.info("Received request to crate rental: {}", rentalDto);
        return rentalService.saveRental(rentalDto);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<RentalDto> updateRental(@PathVariable(value = "id") UUID id, @RequestBody final RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        log.info("Received request to update rental with id: {}", id);
        return rentalService.updateRental(rentalDto, id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteRental(@PathVariable(value = "id") UUID id, @RequestBody final RentalDto rentalDto) throws RentalNotFoundException {
        log.info("Received request to delete rental with id: {}", id);
        return rentalService.deleteRental(id);
    }
}