package com.springdata.mapping.Repository;

import com.springdata.mapping.entity.Address;
import com.springdata.mapping.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
