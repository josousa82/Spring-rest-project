package com.training.rest.mapper;


import com.training.rest.springrestproject.domain.Customer;
import com.training.rest.springrestproject.v1.mapper.CustomerMapper;
import com.training.rest.springrestproject.v1.model.CustomerDTO;
import org.junit.jupiter.api.Test;

import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.CUSTOMER_1;
import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.CUSTOMER_DTO_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() throws Exception {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(CUSTOMER_1);
        assertEquals(CUSTOMER_1.getId(), customerDTO.getId());
        assertEquals(CUSTOMER_1.getFirstName(), customerDTO.getFirstName());
    }

    @Test
    public void customerDTOToCustomer() throws Exception {
        Customer customer = customerMapper.customerDTOToCustomer(CUSTOMER_DTO_1);
        assertEquals(CUSTOMER_DTO_1.getId(), customer.getId());
        assertEquals(CUSTOMER_DTO_1.getFirstName(), customer.getFirstName());
    }
}