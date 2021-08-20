 package com.github.SezinDemir.DWallet.repository;
 import com.datastax.oss.driver.api.core.CqlSession;
 import com.datastax.oss.driver.api.core.cql.SimpleStatement;
 import com.github.SezinDemir.DWallet.domain.Customer;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Repository;
 import reactor.core.publisher.Flux;
 import reactor.core.publisher.Mono;

 @Repository
 public class CustomerRepository {
      Logger log= LoggerFactory.getLogger(CustomerRepository.class);

 private CqlSession session;
 public CustomerRepository(CqlSession session) {
      this.session = session;
 }

 public Flux<Customer> getAll() {log.info("Getting all customers");
     return Flux.from(session.executeReactive("SELECT * FROM cInfo.customers"))
     .map(row -> new Customer(row.getInt("id"), row.getString("lastname"), row.getString("firstname"), row.getDouble("balance")));
      }

 public Mono<Customer> get(int id) {log.info("Selecting customer by id ");
      return Mono.from(session.executeReactive("SELECT * FROM cInfo.customers WHERE id = " + id))
             .map(row -> new Customer(row.getInt("id"), row.getString("lastname"), row.getString("firstname"), row.getDouble("balance")));
      }

 public Customer create(Customer customer) {log.info("Creating a customer");
       SimpleStatement stmt = SimpleStatement.builder("INSERT INTO cInfo.customers (id,lastname,firstname,balance) values (?,?,?,?)")
      .addPositionalValues(customer.getId(), customer.getLastname(), customer.getFirstname(), customer.getBalance())
      .build();
       Flux.from(session.executeReactive(stmt)).subscribe();
       return customer;
       }
 public Mono<Integer> deleteCustomer(int id){
       log.info("Deleting the customer");
       Flux.from(
       session.executeReactive(
       SimpleStatement.builder("DELETE FROM cInfo.customers WHERE id = ?")
       .addPositionalValue(id)
       .build()))
       .subscribe();
       return Mono.just(id);
       }
    }


