package com.brujula.application.service;

import com.brujula.application.repository.CustomerRepository;
import com.brujula.domain.Customer;
import com.brujula.infraestructure.api.dto.CustomerDto;
import com.brujula.infraestructure.repositoryimpl.models.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    /**
     * Este metodo se encarga de obtener el Customer por su ID
     *
     * @param id parametro con el cual se va a consultar el Customer
     * @return Customer
     */
    public Customer get(int id) {
        Customer customer = repository.getCustomer(id);
        return customer;
    }

    /**
     * Este metodo se encarga de actualizar el Customer
     *
     * @param id      parametro con el cual se va a consultar el Customer
     * @param customer Obj el cual trae la informacion para ser actualizada en la DB
     * @return Customer
     */
    public Customer update(int id, CustomerDto customer) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstName(customer.getFirstName());
        customerModel.setLastName(customer.getLastName());
        customerModel.setCompany(customer.getCompany());
        repository.update(id, customerModel);
        return new Customer(id, customer.getFirstName(), customer.getLastName(),customer.getCompany());
    }

    /**
     * Este metodo se encarga de crear el Customer
     *
     * @param customer Obj el cual trae la informacion para ser incertada en la DB
     * @return Customer
     */
    public Customer create(CustomerDto customer) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstName(customer.getFirstName());
        customerModel.setLastName(customer.getLastName());
        customerModel.setCompany(customer.getCompany());
        int id = repository.create(customerModel);
        return new Customer(id, customer.getFirstName(), customer.getLastName(),customer.getCompany());
    }
}
