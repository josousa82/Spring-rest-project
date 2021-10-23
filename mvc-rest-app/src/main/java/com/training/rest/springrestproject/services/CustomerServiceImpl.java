package com.training.rest.springrestproject.services;


import com.training.rest.springrestproject.domain.Customer;
import com.training.rest.springrestproject.repositories.CustomerRepository;
import com.training.rest.springrestproject.services.exceptions.ResourceNotFoundException;
import com.training.rest.springrestproject.v1.mapper.CustomerMapper;
import com.training.rest.springrestproject.v1.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.training.rest.springrestproject.services.SerializingDeserializingAbstractHelper.asJsonString;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    public static final String CUSTOMERS_URL = "/api/v1/customers/";
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> getCustomerDTO(customer, "id/".concat(customer.getId().toString())) )
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByName(String name) {
        return customerRepository.findByFirstName(name)
                .map(customer -> getCustomerDTO(customer, "name/".concat(customer.getFirstName())) )
                .orElseThrow(RuntimeException::new);
    }

    @Override

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> getCustomerDTO(customer, "id/".concat(customer.getId().toString())) )
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        CustomerDTO returnDTO = saveAndReturnDTO(customer);
        log.info("Created customer {}", asJsonString(returnDTO));
        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer customer1 =  customerRepository.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("Customer with id " + id + " not found. "));
        customer.setId(customer1.getId());
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        // TODO refactor coed.. don't like it.. smellllll
        return customerRepository.findById(id).map(customer1 -> {
            if(Strings.isNotBlank(customerDTO.getFirstName())){
                customer1.setFirstName(customerDTO.getFirstName());
            }
            if(Strings.isNotBlank(customerDTO.getLastName())){
                customer1.setLastName(customerDTO.getLastName());
            }
           return saveAndReturnDTO(customerRepository.save(customer1));
        }).orElseThrow(ResourceNotFoundException::new);
    }

//    TODO test exception
    @Override
    public void deleteCustomerById(Long id) {
        try {
            customerRepository.deleteById(id);
            log.info("Deleted customer with id {}.", id);
        }catch (Exception e){
            log.error("Customer with id {} not found. ", id);
            throw new ResourceNotFoundException("Customer with id " + id + " not found. ");
        }
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnDTO.setUrl(CUSTOMERS_URL.concat(savedCustomer.getId().toString()));
        return returnDTO;
    }

    private CustomerDTO getCustomerDTO(Customer customer, String path) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setUrl(CUSTOMERS_URL.concat(path));
        return customerDTO;
    }
}
