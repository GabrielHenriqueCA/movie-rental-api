package com.api.movierental.controllers;

import com.api.movierental.dtos.RentalDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.exceptions.MovieNotFoundException;
import com.api.movierental.exceptions.RentalNotFoundException;
import com.api.movierental.services.impl.RentalServiceImpl;
import jakarta.validation.Valid;
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

    private final RentalServiceImpl rentalService;

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Page<RentalDto>> getAllRentals(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        return rentalService.findAll(pageable);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable(value = "id") final UUID id) throws RentalNotFoundException {
        return rentalService.findById(id);
    }

    @PostMapping("create")
    public ResponseEntity<RentalDto> createRental(@RequestBody @Valid final RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        return rentalService.saveRental(rentalDto);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<RentalDto> updateRental(@PathVariable(value = "id") UUID id, @RequestBody final RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        return rentalService.updateRental(rentalDto,id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteRental(@PathVariable(value = "id") UUID id, @RequestBody final RentalDto rentalDto) throws RentalNotFoundException {
        return rentalService.deleteRental(id);
    }
}
