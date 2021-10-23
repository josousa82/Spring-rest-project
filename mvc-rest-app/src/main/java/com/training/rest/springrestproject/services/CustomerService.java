package com.training.rest.springrestproject.services;

import com.training.rest.springrestproject.v1.model.CustomerDTO;
import javassist.NotFoundException;

import java.util.List;


public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerByName(String name);
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomerById(Long id) throws NotFoundException;
}
