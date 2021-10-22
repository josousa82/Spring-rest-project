package com.training.rest.springrestproject.bootstrap;



import com.training.rest.springrestproject.repositories.CategoryRepository;
import com.training.rest.springrestproject.repositories.CustomerRepository;
import com.training.rest.springrestproject.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.training.rest.springrestproject.bootstrap.DataInitializerHelper.*;


@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {


    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository= categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        categoryRepository.saveAll(Arrays.asList(FRUITS, DRIED,FRESH, EXOTIC, NUTS));

        log.info("Number of categories loaded {}", categoryRepository.count());

        customerRepository.saveAll(Arrays.asList(CUSTOMER_1, CUSTOMER_2, CUSTOMER_3, CUSTOMER_4));
        log.info("Number of customers loaded {}", customerRepository.count());

        vendorRepository.saveAll(Arrays.asList(VENDOR_1, VENDOR_2, VENDOR_3, VENDOR_4));
        log.info("Number of vendors loaded {}", customerRepository.count());
    }

}
