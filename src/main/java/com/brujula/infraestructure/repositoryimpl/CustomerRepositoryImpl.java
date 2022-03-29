package com.brujula.infraestructure.repositoryimpl;

import com.brujula.application.repository.CustomerRepository;
import com.brujula.domain.Customer;
import com.brujula.infraestructure.repositoryimpl.models.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final Logger logger = LogManager.getLogger(CustomerRepositoryImpl.class);

    @Autowired
    private JdbcTemplate template;

    @Override
    public Customer getCustomer(int id) {
        String sql = "select * from customers where ID=?";
        CustomerModel customer = template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<CustomerModel>(CustomerModel.class));
        return Customer.createCustomer(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getCompany());
    }

    @Override
    public int update(int id, CustomerModel customer) {
        String sql = "update customers set FIRST_NAME=?, LAST_NAME=?, COMPANY=? where id=?";
        int res = template.update(sql, customer.getFirstName(), customer.getLastName(), customer.getCompany(), id);
        return res;
    }

    @Override
    public int create(CustomerModel customer) {
        String sql = "insert into customers (FIRST_NAME, LAST_NAME, COMPANY) values(?,?,?)";
        int res = template.update(sql, customer.getFirstName(), customer.getLastName(), customer.getCompany());
        return res;
    }
}

