package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/provinces")
    public ModelAndView listProvince(){
        Iterable<Province>provinces=provinceService.findAll();
        return new ModelAndView("province/list","provinces",provinces);
    }

    @GetMapping("create-province")
    public ModelAndView showCreateForm(){
        return new ModelAndView("province/create","provinces",new Province());
    }

    @PostMapping("create-province")
    public ModelAndView saveProvince(@ModelAttribute Province province){
        provinceService.save(province);
        ModelAndView modelAndView= new ModelAndView("province/create");
        modelAndView.addObject("provinces",new Province());
        modelAndView.addObject("message","New province created successfully");
        return modelAndView;
    }

    @GetMapping("edit-province/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Province province = this.provinceService.findById(id);
        if (province!=null){
            return new ModelAndView("province/edit","provinces",province);
        }
        else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("edit-province")
    public ModelAndView updateProvince(@ModelAttribute Province province){
        provinceService.save(province);
        ModelAndView modelAndView= new ModelAndView("redirect:/provinces","provinces",province);
        modelAndView.addObject("message","Province updated successfully");
        return modelAndView;
    }

    @GetMapping("delete-province/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Province province = this.provinceService.findById(id);
        if (province!=null){
            return new ModelAndView("province/delete","provinces",province);
        }
        else {
            return new ModelAndView("error-404");
        }
    }

    @PostMapping("delete-province")
    public String deleteProvince(@ModelAttribute Province province){
        provinceService.remove(province.getId());
        return "redirect:/provinces";
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Province province = provinceService.findById(id);
        if(province == null){
            return new ModelAndView("/error.404");
        }

        Iterable<Customer> customers = customerService.findAllByProvince(province);

        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
