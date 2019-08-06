package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;

public interface CustomerService {
    Iterable<Customer>findAll();

    void save(Customer customer);

    void remove(Long id);

    Customer findById(Long id);

    Iterable<Customer> findAllByProvince(Province province);
}
