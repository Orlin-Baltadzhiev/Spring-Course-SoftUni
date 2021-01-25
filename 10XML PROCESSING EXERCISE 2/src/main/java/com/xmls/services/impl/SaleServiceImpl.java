package com.xmls.services.impl;


import com.google.gson.Gson;
import com.xmls.domain.entities.Car;
import com.xmls.domain.entities.Customer;
import com.xmls.domain.entities.Sale;
import com.xmls.domain.repositories.CarRepository;
import com.xmls.domain.repositories.CustomerRepository;
import com.xmls.domain.repositories.SaleRepository;
import com.xmls.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public void seedSalesInDB() {

        for (int i = 1; i <= 3; i++) {
            Sale sale = new Sale();
            Customer customer = getRandomCustomer();
            int discount = getRandomDiscount();
            Car car = getRandomCar();
            sale.setCar(car);
            sale.setCustomer(customer);
            sale.setDiscount(discount);

            this.saleRepository.saveAndFlush(sale);
        }
    }





    private int getRandomDiscount() {
        Random random = new Random();
        int num = random.nextInt(50) + 1;
        return num;
    }

    private Car getRandomCar() {
        Random random = new Random();
        long index = random.nextInt((int) this.carRepository.count()) + 1;
        Car car = this.carRepository.findById(index).get();
        return car;
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long index = random.nextInt((int) this.customerRepository.count()) + 1;
        Customer customer = this.customerRepository.findById(index).get();
        return customer;
    }
}
