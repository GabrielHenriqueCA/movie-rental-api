package com.api.movierental.services;

import com.api.movierental.dtos.CustomerDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service interface for managing customers.
 */
@Transactional
public interface ICustomerService {

    /**
     * Creates a new customer and saves it to the database.
     *
     * @param customerDto DTO containing the customer information to be saved.
     * @return A ResponseEntity containing the saved customer DTO.
     */
    ResponseEntity<CustomerDto> saveCustomer(CustomerDto customerDto);

    /**
     * Updates an existing customer with the provided customerDto and saves it to the database.
     *
     * @param id          The ID of the customer to be updated.
     * @param customerDto DTO containing the updated customer information.
     * @return A ResponseEntity containing the updated customer DTO, or an appropriate error response.
     */
    ResponseEntity<CustomerDto> updateCustomer(UUID id, CustomerDto customerDto) throws CustomerNotFoundException;

    /**
     * Returns a page of all customers in the database.
     *
     * @param pageable The page parameters to use.
     * @return A Page of CustomerDtos.
     */
    ResponseEntity<Page<CustomerDto>> findAllCustomers(Pageable pageable);

    /**
     * Returns the customer with the specified ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The CustomerDto with the specified ID or null if not found.
     */
    ResponseEntity<CustomerDto> findCustomerById(UUID id) throws CustomerNotFoundException;

    /**
     * Finds and returns a customer entity by its email address.
     *
     * @param email The email address of the customer to be searched for.
     * @return A ResponseEntity containing the customer entity, wrapped in a CustomerDto object, and an HTTP status code.
     * @throws CustomerNotFoundException If no customer entity can be found with the provided email address.
     */
    ResponseEntity<CustomerDto> findCustomerByEmail(String email) throws CustomerNotFoundException;

    /**
     * Returns the customer with the specified name.
     *
     * @param name The name of the customer to retrieve.
     * @param pageable he page parameters to use.
     * @return The CustomerDto with the specified name or null if not found.
     */
    ResponseEntity<Page<CustomerDto>> findCustomerByNameContainingIgnoreCase(String name, Pageable pageable) throws CustomerNotFoundException;

    /**
     * Returns the customer with the specified CPF.
     *
     * @param cpf The CPF of the customer to retrieve.
     * @return The CustomerDto with the specified CPF or null if not found.
     */
    ResponseEntity<CustomerDto> findCustomerByCpf(String cpf) throws CustomerNotFoundException;

    /**
     * Deletes the specified customer from the database.
     *
     * @param id id of Customer to delete.
     * @return string with status of operation.
     */
    ResponseEntity<String> deleteCustomer(UUID id) throws CustomerNotFoundException;

    /**
     * Deactivate the specified customer from the database.
     *
     * @param id id of Customer to delete.
     * @return a ResponseEntity indicating the success or failure of the operation.
     * Returns a ResponseEntity with HTTP status code 204 (No Content) if the operation is successful.
     * Returns a ResponseEntity with HTTP status code 404 (Not Found) if the customer with the specified ID is not found.
     */
    ResponseEntity<Void> deactivateCustomer(UUID id) throws CustomerNotFoundException;
}