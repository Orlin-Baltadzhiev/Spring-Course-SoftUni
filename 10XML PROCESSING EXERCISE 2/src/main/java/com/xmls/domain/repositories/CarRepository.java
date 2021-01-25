package com.xmls.domain.repositories;


import com.xmls.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {



}
