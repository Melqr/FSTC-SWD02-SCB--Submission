package com.bank.repo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import com.bank.entity.CustomerEntity;

@SpringBootTest
@ActiveProfiles("test") // Uses application-test.properties for H2 setup

public class CustomerFindByRepoTest {

    @Autowired
    private ICustomerRepo customerRepo;

    @Test
    void testFindByDescription() {
        // Arrange
        CustomerEntity customer = new CustomerEntity();
        customer.setIcNumber("IC001");
        customer.setLastname("Doe");
        customer.setSurname("John");
        customer.setDescription("VIP");
        customer.setCreationDate(LocalDateTime.now());
        customerRepo.save(customer);

        // Act
        List<CustomerEntity> result = customerRepo.findByDescription("VIP");

        // Assert
        assertNotNull(result);
    }

    @Test
    void testFindByDescriptionAndCreationDateBetween() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        CustomerEntity customer1 = new CustomerEntity(null, "IC002", "Smith", "Alice", "REGULAR", now.minusDays(5), List.of());
        CustomerEntity customer2 = new CustomerEntity(null, "IC003", "Brown", "Bob", "REGULAR", now.minusDays(1), List.of());
        customerRepo.saveAll(List.of(customer1, customer2));

        // Act
        List<CustomerEntity> result = customerRepo.findByDescriptionAndCreationDateBetween(
                "REGULAR",
                now.minusDays(10),
                now
        );

        assertNotNull(result);
    }
}
