package com.training.rest.services;


import com.training.rest.springrestproject.SpringRestProjectApplication;
import com.training.rest.springrestproject.bootstrap.Bootstrap;
import com.training.rest.springrestproject.domain.Customer;
import com.training.rest.springrestproject.repositories.CategoryRepository;
import com.training.rest.springrestproject.repositories.CustomerRepository;
import com.training.rest.springrestproject.repositories.VendorRepository;
import com.training.rest.springrestproject.services.CustomerService;
import com.training.rest.springrestproject.services.CustomerServiceImpl;
import com.training.rest.springrestproject.v1.mapper.CustomerMapper;
import com.training.rest.springrestproject.v1.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringRestProjectApplication.class})
public class CustomerServiceImplIT {


    @Autowired
    public CustomerRepository customerRepository;
    CustomerService customerService;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    @BeforeEach
    public void setUp() throws Exception {
        log.info("Loading customer data");
        log.info("{} customers loaded.", customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }


    @Test
    @Transactional
    public void putUpdateFirstNameCustomer() throws Exception {
        String updatedName = "updatedName";
        long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = CustomerDTO
                .builder()
                .firstName(updatedName)
                .build();
        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertNotEquals(originalFirstName,  updatedCustomer.getFirstName());
        assertEquals(originalLastName,  updatedCustomer.getLastName());
    }

    @Test
    @Transactional
    public void putUpdateLastNameCustomer() throws Exception {
        String updatedName = "updatedName";
        long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = CustomerDTO
                .builder()
                .lastName(updatedName)
                .build();
        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertEquals(originalFirstName, updatedCustomer.getFirstName());
        assertNotEquals(originalLastName,  updatedCustomer.getLastName());
    }
    @Transactional
    private long getCustomerIdValue() {
        return customerRepository.findAll().get(0).getId();
    }
}