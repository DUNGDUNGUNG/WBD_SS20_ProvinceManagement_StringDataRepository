package com.codegym.service;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findAllByNameStartsWith(String name, Pageable pageable);

    void save(Customer customer);

    void remove(Long id);

    Customer findById(Long id);

    Iterable<Customer> findAllByProvince(Province province);
}
