package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }

    @GetMapping("/customers")
    public ModelAndView listCustomer(@PageableDefault(size = 10, sort = "name") Pageable pageable, @RequestParam("s") Optional<String> s) {
        Page<Customer> customers;
        if (s.isPresent()) {
            customers = customerService.findAllByNameStartsWith(s.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        return new ModelAndView("customer/list", "customers", customers);
    }

    @GetMapping("create-customer")
    public ModelAndView showCreateForm() {
        return new ModelAndView("customer/create", "customers", new Customer());
    }

    @PostMapping("create-customer")
    public ModelAndView saveProvince(@ModelAttribute Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customers", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Customer customer = this.customerService.findById(id);
        if (customer != null) {
            return new ModelAndView("customer/edit", "customers", customer);
        } else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customers", "customers", customer);
        modelAndView.addObject("message", "customer updated successfully");
        return modelAndView;
    }

    @GetMapping("delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Customer customer = this.customerService.findById(id);
        if (customer != null) {
            return new ModelAndView("customer/delete", "customers", customer);
        } else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("delete-customer")
    public String deleteProvince(@ModelAttribute Customer customer) {
        customerService.remove(customer.getId());
        return "redirect:/customers";
    }

}
