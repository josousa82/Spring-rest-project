package com.training.rest.springrestproject.v1.mapper;



import com.training.rest.springrestproject.domain.Customer;
import com.training.rest.springrestproject.v1.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customer);
}
