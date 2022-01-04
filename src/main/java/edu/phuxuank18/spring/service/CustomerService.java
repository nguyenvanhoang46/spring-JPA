package edu.phuxuank18.spring.service;
import java.util.List;

import edu.phuxuank18.spring.model.Customer;

public interface CustomerService {
	
	public List < Customer > getCustomers();

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer(int theId) throws ResourceNotFoundException;

    public void deleteCustomer(int theId) throws ResourceNotFoundException;

}
