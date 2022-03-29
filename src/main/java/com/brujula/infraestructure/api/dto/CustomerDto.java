package com.brujula.infraestructure.api.dto;

import com.brujula.domain.Customer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerDto {

    @NotEmpty
    @Size(min = 10, max = 50)
    private String firstName;
    @NotEmpty
    @Size(min = 10, max = 100)
    private String lastName;
    @NotEmpty
    private String company;

    public static CustomerDto createCustomerDto(Customer customer){
        return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getCompany());
    }

    private CustomerDto(String firstName, String lastName, String company) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCompany(company);
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
