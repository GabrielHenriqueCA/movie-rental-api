package com.api.movierental.services.impl;

import com.api.movierental.converters.RentalConverter;
import com.api.movierental.dtos.RentalDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.exceptions.MovieNotFoundException;
import com.api.movierental.exceptions.RentalNotFoundException;
import com.api.movierental.models.RentalModel;
import com.api.movierental.repositories.RentalRepository;
import com.api.movierental.services.IRentalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class RentalServiceImpl implements IRentalService {


    private final RentalRepository rentalRepository;
    private final RentalConverter rentalConverter;


    public RentalServiceImpl(RentalRepository rentalRepository, RentalConverter rentalConverter) {
        this.rentalRepository = rentalRepository;
        this.rentalConverter = rentalConverter;
    }

    @Override
    public ResponseEntity<RentalDto> saveRental(@Valid RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        if (rentalDto == null) {
            log.warn("Failed to create rental. rentalDto is null.");
            return ResponseEntity.badRequest().build();
        }

        rentalRepository.save(rentalConverter.mapToRentalModel(rentalDto));

        log.info("Rental for Customer {} created successfully", rentalDto.getCustomer().getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<RentalDto> updateRental(RentalDto rentalDto, UUID id) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        if (Objects.isNull(rentalDto)) {
            log.warn("Failed to update rental. RentalDto is null.");
            return ResponseEntity.badRequest().build();
        }

        RentalModel rentalModel = rentalRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Failed to update rental. Rental with ID {} not found.", id);
                    return new RentalNotFoundException("Rental with ID " + id + " not found.");
                });

        rentalRepository.save(rentalConverter.mapToRentalModel(rentalDto, rentalModel));
        log.info("Rental updated successfully {}", rentalDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<RentalDto>> findAll(Pageable pageable) {
        try {
            Page<RentalModel> rentals = rentalRepository.findAll(pageable);
            Page<RentalDto> rentalsDto = rentalConverter.convertToDtoPage(rentals);

            return ResponseEntity.ok(rentalsDto);

        } catch (Exception e) {
            log.error("Failed to retrieve rentals: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<RentalDto> findById(UUID id) throws RentalNotFoundException {
        Optional<RentalModel> rentalModel = rentalRepository.findById(id);

        return rentalModel.map(rentalConverter::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RentalNotFoundException(String.format("Failed to find rental with id: %s", id)));
    }

    @Override
    public ResponseEntity<String> deleteRental(UUID id) throws RentalNotFoundException {
        RentalModel rentalModel = rentalRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Failed to exclude rental. Rental with ID {} not found.", id);
                    return new RentalNotFoundException("Rental with ID " + id + " not found.");
                });

        rentalRepository.delete(rentalModel);
        log.warn("Rental with id {} has been deleted", id);
        return ResponseEntity.ok("Rental with id " + id + " has been deleted.");

    }
}
