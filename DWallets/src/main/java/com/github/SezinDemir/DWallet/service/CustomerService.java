package com.github.SezinDemir.DWallet.service;

import com.github.SezinDemir.DWallet.App;
import com.github.SezinDemir.DWallet.domain.Customer;
import com.github.SezinDemir.DWallet.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<Customer> getAll() {
        return customerRepository.getAll();
    }

    public Mono<Customer> get(String id) {
        return customerRepository.get(Integer.parseInt(id));
    }


    public Customer create(Customer customer) {
        return customerRepository.create(customer);}

    }


