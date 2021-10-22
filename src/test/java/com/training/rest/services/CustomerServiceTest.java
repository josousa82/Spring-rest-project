package com.training.rest.services;


import com.training.rest.springrestproject.domain.Customer;
import com.training.rest.springrestproject.repositories.CustomerRepository;
import com.training.rest.springrestproject.services.CustomerService;
import com.training.rest.springrestproject.services.CustomerServiceImpl;
import com.training.rest.springrestproject.v1.mapper.CustomerMapper;
import com.training.rest.springrestproject.v1.model.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest  {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }


    @Test
    public void getAllCustomersTest() throws Exception {
        CUSTOMER_1.setId(1L);
        CUSTOMER_2.setId(2L);
        CUSTOMER_3.setId(3L);

        when(customerRepository.findAll()).thenReturn(Arrays.asList(CUSTOMER_1, CUSTOMER_2, CUSTOMER_3));

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerByNameTest() throws Exception {
        // given
        when(customerRepository.findByFirstName(anyString())).thenReturn(Optional.of(CUSTOMER_1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerByName(CUSTOMER_1.getFirstName());

        // then
        assertEquals(CUSTOMER_1.getId(), customerDTO.getId());
        assertEquals(CUSTOMER_1.getFirstName(), customerDTO.getFirstName());
        assertEquals(CUSTOMER_1.getLastName(), customerDTO.getLastName());
    }

    @Test
    public void getCustomerByIdTest() {

        // given
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(CUSTOMER_1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertEquals(CUSTOMER_1.getId(), customerDTO.getId());
        assertEquals(CUSTOMER_1.getFirstName(), customerDTO.getFirstName());
        assertEquals(CUSTOMER_1.getLastName(), customerDTO.getLastName());
    }
    
    @Test
    public void createNewCustomerTest() {
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER_1);

        CustomerDTO customerDTO = customerService.createNewCustomer(CUSTOMER_DTO_1);

        assertEquals(CUSTOMER_1.getFirstName(), customerDTO.getFirstName());
        assertEquals(CUSTOMER_1.getLastName(), customerDTO.getLastName());
        assertEquals(CUSTOMER_1.getId(), customerDTO.getId());
        assertEquals(CUSTOMERS_API_URL.concat(CUSTOMER_DTO_1.getId().toString()) ,customerDTO.getUrl());
    }


    @Test
    public void saveCustomerByDTOTest() {
        CUSTOMER_1.setFirstName("under test");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CUSTOMER_1));
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER_1);

        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, CUSTOMER_DTO_1);

        assertEquals(CUSTOMER_1.getFirstName(), savedDto.getFirstName());
        assertEquals(CUSTOMER_1.getLastName(), savedDto.getLastName());
        assertEquals(CUSTOMER_1.getId(), savedDto.getId());
        assertEquals(CUSTOMERS_API_URL.concat(CUSTOMER_DTO_1.getId().toString()) ,savedDto.getUrl());
    }


    @Test
    public void patchCustomerTest() {
        CUSTOMER_1.setFirstName("under test");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CUSTOMER_1));
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER_1);

        CustomerDTO savedDto = customerService.patchCustomer(1L, CUSTOMER_DTO_1);

        assertEquals(CUSTOMER_1.getFirstName(), savedDto.getFirstName());
        assertEquals(CUSTOMER_1.getLastName(), savedDto.getLastName());
        assertEquals(CUSTOMER_1.getId(), savedDto.getId());
        assertEquals(CUSTOMERS_API_URL.concat(CUSTOMER_DTO_1.getId().toString()) ,savedDto.getUrl());
    }

    @Test
    public void deleteCustomerByIdTest() {
        customerRepository.deleteById(1L);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}