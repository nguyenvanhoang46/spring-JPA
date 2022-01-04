package edu.phuxuank18.spring.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
