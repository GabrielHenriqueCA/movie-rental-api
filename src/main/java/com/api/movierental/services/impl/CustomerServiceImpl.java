package com.api.movierental.services.impl;

import com.api.movierental.converters.CustomerConverter;
import com.api.movierental.dtos.CustomerDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.exceptions.ResourceAlreadyExistsException;
import com.api.movierental.exceptions.ResourceNotFoundException;
import com.api.movierental.models.CustomerModel;
import com.api.movierental.repositories.CustomerRepository;
import com.api.movierental.services.ICustomerService;
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
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public ResponseEntity<CustomerDto> saveCustomer(@Valid CustomerDto customerDto) {
        if (Objects.isNull(customerDto)) {
            log.warn("Failed to save customer. CustomerDto is null.");
            return ResponseEntity.badRequest().build();
        }

        if (customerRepository.existsByCpf(customerDto.getCpf())) {
            log.warn("Failed to save customer. Customer with CPF {} already exists.", customerDto.getCpf());
            throw new ResourceAlreadyExistsException("Customer with CPF " + customerDto.getCpf() + " already exists.");
        }

        CustomerModel customerModel = customerConverter.mapToCustomerModel(customerDto);
        customerRepository.save(customerModel);
        log.info("Customer saved successfully: {}", customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(UUID id, CustomerDto customerDto) throws CustomerNotFoundException {
        if (Objects.isNull(customerDto)) {
            log.warn("Failed to update customer. CustomerDto is null.");
            return ResponseEntity.badRequest().build();
        }

        CustomerModel optionalCustomerModel = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Failed to update customer. Customer with ID {} not found.", id);
            return new ResourceNotFoundException("Customer with ID " + id + " not found.");
        });

        customerRepository.save(customerConverter.mapToCustomerModel(customerDto, optionalCustomerModel));
        log.info("Customer updated successfully: {}", customerDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Page<CustomerDto>> findAllCustomers(Pageable pageable) {
        try {
            Page<CustomerModel> customers = customerRepository.findAll(pageable);
            Page<CustomerDto> customerDtos = customerConverter.convertToDtoPage(customers);

            return ResponseEntity.ok(customerDtos);
        } catch (Exception e) {
            log.error("Failed to retrieve customers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerById(UUID id) throws CustomerNotFoundException {
        Optional<CustomerModel> customerOptional = customerRepository.findById(id);

        return customerOptional.map(customerConverter::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Failed to find customer with id: %s", id)));
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerByEmail(String email) throws CustomerNotFoundException {
        Optional<CustomerModel> customerOptional = customerRepository.findByEmail(email);

        return customerOptional.map(customerConverter::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Failed to find customer with email: %s", email)));
    }

    @Override
    public ResponseEntity<Page<CustomerDto>> findCustomerByNameContainingIgnoreCase(String name, Pageable pageable) throws CustomerNotFoundException {
        try {
            Page<CustomerModel> customerPage = customerRepository.findCustomerByNameContainingIgnoreCase(name, pageable);
            Page<CustomerDto> customerDtos = customerPage.map(customerConverter::convertToDto);

            return ResponseEntity.ok(customerDtos);
        } catch (Exception e) {
            log.error("Failed to retrieve customers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<CustomerDto> findCustomerByCpf(String cpf) throws CustomerNotFoundException {
        Optional<CustomerModel> customerOptional = customerRepository.findCustomerByCpf(cpf);

        return customerOptional.map(customerConverter::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Failed to find customer with cpf: %s", cpf)));
    }

    @Override
    public ResponseEntity<String> deleteCustomer(UUID id) throws CustomerNotFoundException {
        CustomerModel customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %s not found", id)));

        customerRepository.delete(customer);
        log.info("Customer deleted successfully: {}", customer);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @Override
    public ResponseEntity<Void> deactivateCustomer(UUID id) throws CustomerNotFoundException {
        CustomerModel customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %s not found", id)));

        customer.setActive(false);
        customerRepository.save(customer);
        log.info("Customer deactivated successfully: {}", customer);
        return ResponseEntity.noContent().build();
    }

}