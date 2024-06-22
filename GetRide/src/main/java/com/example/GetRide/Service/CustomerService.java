package com.example.GetRide.Service;

import com.example.GetRide.Dto.request.CustomerRequest;
import com.example.GetRide.Dto.response.CustomerResponse;
import com.example.GetRide.Enum.Gender;
import com.example.GetRide.Model.Customer;
import com.example.GetRide.Repository.CustomerRepository;
import com.example.GetRide.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRespository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        Customer savedCustomer = customerRespository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomer(String email) {
        Customer savedCustomer = customerRespository.findByEmailId(email);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllByGenderAndAgeGreaterThan(Gender gender, int age) {
        java.util.List<Customer> customers = customerRespository.getAllByGenderAndAgeGreaterThan(gender,age);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer customer: customers) {
            customerResponses.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponses;
    }
}
