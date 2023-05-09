package com.api.movierental.repositories;

import com.api.movierental.models.CustomerModel;
import com.api.movierental.models.RentalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, UUID> {

    boolean existsByCustomerAndDateRentalAndDateReturn(CustomerModel customermodel, Date rentalDate, Date dateReturn);
}
