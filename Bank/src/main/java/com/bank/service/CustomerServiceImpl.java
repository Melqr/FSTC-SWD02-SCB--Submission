package com.bank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.CustomerEntity;
import com.bank.mapper.CustomerMapper;
import com.bank.repo.ICustomerRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepo customerRepo;
    
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerEntity createCustomer(CustomerEntity customer) {
        return customerRepo.save(customer);
    }

    @Transactional()
    @Override
    public Optional<CustomerEntity> getCustomerById(Long id) {                               
    	return customerRepo.findById(id);

    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public CustomerEntity updateCustomer(Long id, CustomerEntity updatedCustomer) {
        return customerRepo.findById(id).map(existing -> {
            existing.setIcNumber(updatedCustomer.getIcNumber());
            existing.setLastname(updatedCustomer.getLastname());
            existing.setSurname(updatedCustomer.getSurname());
            existing.setDescription(updatedCustomer.getDescription());
            return customerRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }

    @Override
    @Transactional()
    public List<CustomerEntity> getCustomersByDescriptionAndCreationDateBetween(
            String description, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Finding customers by description '{}' between {} and {}", description, startDate, endDate);
        return customerRepo.findByDescriptionAndCreationDateBetween(description, startDate, endDate);
    }

}
