package com.github.SezinDemir.DWallet.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.SezinDemir.DWallet.domain.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

    @Repository
    public class CustomerRepository {
        private CqlSession session;

        public CustomerRepository(CqlSession session) {
            this.session = session;
        }

        public Flux<Customer> getAll() {
            return Flux.from(session.executeReactive("SELECT * FROM cInfo.customers"))
                    .map(row -> new Customer(row.getInt("id"), row.getString("lastname"),row.getString("firstname"),row.getDouble("balance")));
        }

        public Mono<Customer> get(int id) {
            return Mono.from(session.executeReactive("SELECT * FROM cInfo.customers WHERE id = " + id))
                    .map(row -> new Customer(row.getInt("id"), row.getString("lastname"),row.getString("firstname"),row.getDouble("balance")));
        }

        public Customer create(Customer customer) {
            SimpleStatement stmt = SimpleStatement.builder("INSERT INTO cInfo.customers (id,lastname,firstname,balance) values (?,?, ?, ?)")
                    .addPositionalValues(customer.getId(), customer.getLastname(),customer.getFirstname(),customer.getBalance() )
                    .build();
            Flux.from(session.executeReactive(stmt)).subscribe();
            return customer;
        }


    }


