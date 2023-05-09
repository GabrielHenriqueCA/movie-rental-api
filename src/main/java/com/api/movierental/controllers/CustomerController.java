package com.api.movierental.controllers;

import com.api.movierental.dtos.CustomerDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.services.impl.CustomerServiceImpl;
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
@RequestMapping("/locator/customers")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public ResponseEntity<Page<CustomerDto>> getAllCustomers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        return customerService.findAllCustomers(pageable);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<CustomerDto> getCustomersById(@PathVariable(value = "id") final UUID id) throws CustomerNotFoundException {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerDto> getCustomerByCpf(@PathVariable(value = "cpf") final String cpf) throws CustomerNotFoundException {
        return customerService.findCustomerByCpf(cpf);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable(value = "email") final String email) throws CustomerNotFoundException {
        return customerService.findCustomerByEmail(email);
    }

    @GetMapping("/name")
    public ResponseEntity<Page<CustomerDto>> getCustomersByName(@RequestParam final String name, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) throws CustomerNotFoundException {
        return customerService.findCustomerByNameContainingIgnoreCase(name,pageable);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(value = "id") final UUID id, @RequestBody @Valid final CustomerDto customerDto) throws CustomerNotFoundException {
        return customerService.updateCustomer(id, customerDto);
    }

    @PutMapping("/desactive/{id}")
    public ResponseEntity<Void> deactivateCustomer(@PathVariable(value = "id") final UUID id) throws CustomerNotFoundException {
        return customerService.deactivateCustomer(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(value = "id") final UUID id) throws CustomerNotFoundException {

        return customerService.deleteCustomer(id);
    }
}


