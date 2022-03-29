package com.brujula.application.repository;

import com.brujula.domain.Customer;
import com.brujula.infraestructure.repositoryimpl.models.CustomerModel;

import java.util.List;

public interface CustomerRepository {
    Customer getCustomer(int id);
    int update(int id,CustomerModel customer);
    int create(CustomerModel customer);
}
