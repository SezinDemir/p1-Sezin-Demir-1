package com.github.SezinDemir.DWallet.domain;

import java.util.Objects;

public class Customer {
        private int id;
        private String lastname;
        private String firstname;
        private double balance;


        public Customer(int id, String lastname, String firstname, double balance) {
            this.id = id;
            this.lastname = lastname;
            this.firstname = firstname;
            this.balance = balance;
        }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Double.compare(customer.balance, balance) == 0 && Objects.equals(lastname, customer.lastname)&& Objects.equals(firstname, customer.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname,firstname,balance);
    }
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
