package com.api.movierental.repositories;

import com.api.movierental.models.CustomerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {

    Page<CustomerModel> findCustomerByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<CustomerModel> findCustomerByName(String name);

    Optional<CustomerModel> findCustomerByCpf(String cpf);

    Optional<CustomerModel> findByEmail(String email);

    boolean existsById(UUID id);

    boolean existsByCpf(String cpf);

}