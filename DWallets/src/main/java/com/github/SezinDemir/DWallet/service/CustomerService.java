 package com.github.SezinDemir.DWallet.service;
 import com.github.SezinDemir.DWallet.domain.Customer;
 import com.github.SezinDemir.DWallet.repository.CustomerRepository;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Service;
 import reactor.core.publisher.Flux;
 import reactor.core.publisher.Mono;

  @Service
  public class CustomerService {
    Logger log= LoggerFactory.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<Customer> getAll() { log.info("All customers:");
        return customerRepository.getAll();

    }

    public Mono<Customer> get(String id) { log.info("Customers by id:");
        return customerRepository.get(Integer.parseInt(id));

    }

    public Customer create(Customer customer) {log.info("Creating new customers:");
        return customerRepository.create(customer);
    }

    public Mono<Integer> deleteCustomer(String id){ log.info("Deleting a customer:");
        return customerRepository.deleteCustomer(Integer.parseInt(id));
      }
  }

