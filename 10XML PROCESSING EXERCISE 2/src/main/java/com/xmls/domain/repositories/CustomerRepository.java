package com.xmls.domain.repositories;


import com.xmls.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Set<Customer> getAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("SELECT c FROM Customer as c WHERE c.sales.size > 0")
    Set<Customer> getAllCustomerTotalSales();

    @Query("SELECT c from Customer c  ORDER BY c.birthDate , c.youngDriver  DESC ")
    Set<Customer> findAllAndSort();

}
