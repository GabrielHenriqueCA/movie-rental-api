package com.api.movierental.controllers;

import com.api.movierental.dtos.CustomerDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.services.impl.CustomerServiceImpl;
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
@RequestMapping("/locator/customers")
public class CustomerController {

    private static final Logger log = LogManager.getLogger(CustomerController.class);
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public ResponseEntity<Page<CustomerDto>> getAllCustomers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        log.info("Received request to get all customers");
        return customerService.findAllCustomers(pageable);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<CustomerDto> getCustomersById(@PathVariable(value = "id") final UUID id) throws CustomerNotFoundException {
        log.info("Received request to get customer by id: {}", id);
        return customerService.findCustomerById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerDto> getCustomerByCpf(@PathVariable(value = "cpf") final String cpf) throws CustomerNotFoundException {
        log.info("Received request to get customer by cpf: {}", cpf);
        return customerService.findCustomerByCpf(cpf);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable(value = "email") final String email) throws CustomerNotFoundException {
        log.info("Received request to get customer by email: {}", email);
        return customerService.findCustomerByEmail(email);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Page<CustomerDto>> getCustomersByName(@PathVariable(value = "name") final String name, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) throws CustomerNotFoundException {
        log.info("Received request to get customer by name: {}", name);
        return customerService.findCustomerByNameContainingIgnoreCase(name, pageable);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(value = "id") final UUID id, @RequestBody @Valid final CustomerDto customerDto) throws CustomerNotFoundException {
        log.info("Received request to update customer: {}", customerDto);
        return customerService.updateCustomer(id, customerDto);
    }

    @PutMapping("/desactive/{id}")
    public ResponseEntity<Void> deactivateCustomer(@PathVariable(value = "id") final UUID id) throws CustomerNotFoundException {
        log.info("Received request to deactivate customer with id: {}", id);
        return customerService.deactivateCustomer(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") final UUID id) throws CustomerNotFoundException {
        log.info("Received request to delete customer with id: {}", id);
        return customerService.deleteCustomer(id);
    }
}