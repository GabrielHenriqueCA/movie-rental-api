package com.api.movierental.converters;

import com.api.movierental.dtos.RentalDto;
import com.api.movierental.exceptions.*;
import com.api.movierental.models.CustomerModel;
import com.api.movierental.models.MovieModel;
import com.api.movierental.models.RentalModel;
import com.api.movierental.repositories.CustomerRepository;
import com.api.movierental.repositories.MovieRepository;
import com.api.movierental.repositories.RentalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RentalConverter {
    private final ModelMapper modelMapper;

    private final RentalRepository rentalRepository;

    private final CustomerRepository customerRepository;

    private final MovieRepository movieRepository;

    private static final String ERRO_TO_RETURN = "Return date cannot be before rental date";

    private static final String ERRO_TO_RENTAL_ALREADY_EXISTS = "Return date cannot be before rental date";

    public RentalConverter(ModelMapper modelMapper, RentalRepository rentalRepository, CustomerRepository customerRepository, MovieRepository movieRepository) {
        this.modelMapper = modelMapper;
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.movieRepository = movieRepository;
    }

    public RentalDto convertToDto(RentalModel rentalModel) {
        return modelMapper.map(rentalModel, RentalDto.class);
    }

    public List<RentalDto> convertToDtoList(List<RentalModel> rentalModels) {
        return rentalModels.stream()
                .map(this::convertToDto)
                .toList();
    }

    public Page<RentalDto> convertToDtoPage(Page<RentalModel> rentalPageDto) {
        return rentalPageDto.map(this::convertToDto);
    }

    public RentalModel mapToRentalModel(RentalDto rentalDto) throws RentalNotFoundException, CustomerNotFoundException, MovieNotFoundException {
        CustomerModel customerModel = customerRepository
                .findCustomerByCpf(rentalDto.getCustomer().getCpf())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Failed to find customer with cpf: %s", rentalDto.getCustomer().getCpf())));

        List<MovieModel> movieModels = rentalDto.getMovies().stream()
                .map(movieDto -> movieRepository.findByMovieNameContainingIgnoreCase(movieDto.getMovieName()))
                .flatMap(Optional::stream)
                .toList();

        if (movieModels.isEmpty()) {
            throw new MovieNotFoundException("No movies found for the given rental");
        }

        if (rentalDto.getDateReturn().before(rentalDto.getDateRental())) {
            log.warn(ERRO_TO_RETURN);
            throw new ReturnDateException(ERRO_TO_RETURN);
        }

        if (rentalRepository.existsByCustomerAndDateRentalAndDateReturn(customerModel, rentalDto.getDateRental(), rentalDto.getDateReturn())) {
            log.warn(ERRO_TO_RENTAL_ALREADY_EXISTS);
            throw new RentalAlreadyExistsException(ERRO_TO_RENTAL_ALREADY_EXISTS);
        }

        RentalModel rentalModel = new RentalModel();

        rentalModel.setCustomer(customerModel);
        rentalModel.setMovies(movieModels);
        rentalModel.setValue(rentalDto.getValue());
        rentalModel.setNumMovies(rentalDto.getMovies().size());
        rentalModel.setDateRental(rentalDto.getDateRental());
        rentalModel.setDateReturn(rentalDto.getDateReturn());

        return rentalModel;
    }

    public RentalModel mapToRentalModel(RentalDto rentalDto, RentalModel rentalModel) throws CustomerNotFoundException, MovieNotFoundException {
        CustomerModel customerModel = customerRepository
                .findCustomerByCpf(rentalDto.getCustomer().getCpf())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Failed to find customer with cpf: %s", rentalDto.getCustomer().getCpf())));

        List<MovieModel> movieModels = Optional.ofNullable(rentalDto.getMovies())
                .orElse(Collections.emptyList())
                .stream()
                .flatMap(movieDto -> movieRepository.findByMovieNameContainingIgnoreCase(movieDto.getMovieName()).stream())
                .collect(Collectors.toList());

        if (movieModels.isEmpty()) {
            throw new MovieNotFoundException("No movies found for the given rental");
        }

        if (rentalDto.getDateReturn().before(rentalDto.getDateRental())) {
            log.warn(ERRO_TO_RETURN);
            throw new ReturnDateException(ERRO_TO_RETURN);
        }

        if (rentalRepository.existsByCustomerAndDateRentalAndDateReturn(customerModel, rentalDto.getDateRental(), rentalDto.getDateReturn())) {
            log.warn(ERRO_TO_RENTAL_ALREADY_EXISTS);
            throw new RentalAlreadyExistsException(ERRO_TO_RENTAL_ALREADY_EXISTS);
        }

        rentalModel.setCustomer(customerModel);
        rentalModel.setMovies(movieModels);
        rentalModel.setNumMovies(rentalDto.getMovies().size());
        rentalModel.setDateRental(rentalDto.getDateRental());
        rentalModel.setDateReturn(rentalDto.getDateReturn());
        rentalModel.setValue(rentalDto.getValue());

        return rentalModel;
    }
}
