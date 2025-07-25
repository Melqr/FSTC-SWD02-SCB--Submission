package com.bank.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.entity.CustomerEntity;

@Repository
public interface ICustomerRepo extends JpaRepository<CustomerEntity, Long> {
	
	List<CustomerEntity> findByDescription(String description);
	
    // Derived query method using BETWEEN for date range
    List<CustomerEntity> findByDescriptionAndCreationDateBetween(
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
    );

}
