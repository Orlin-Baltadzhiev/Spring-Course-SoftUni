package softuni.cardealer.service.impl;

import org.springframework.stereotype.Service;
import softuni.cardealer.domain.entities.Car;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.entities.Sale;
import softuni.cardealer.domain.repositories.CarRepository;
import softuni.cardealer.domain.repositories.CustomerRepository;
import softuni.cardealer.domain.repositories.SaleRepository;
import softuni.cardealer.service.SaleService;

import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public void seedSales() {

        int value = 5;
        Sale sale = new Sale();
        sale.setCar(getRandomCar());
        sale.setCustomer(getRandomCustomer());
        sale.setDiscount(5);
        value = value + 10;
        Sale sale1 = new Sale();
        sale1.setCar(getRandomCar());
        sale1.setCustomer(getRandomCustomer());
        sale1.setDiscount(10);
        value = value + 10;
        Sale sale2 = new Sale();
        sale2.setCar(getRandomCar());
        sale2.setCustomer(getRandomCustomer());
        sale2.setDiscount(20);

        this.saleRepository.saveAndFlush(sale);
        this.saleRepository.saveAndFlush(sale1);
        this.saleRepository.saveAndFlush(sale2);

    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findById(id).get();


    }

    private Car getRandomCar() {

        Random random = new Random();
        long id = (long) random.nextInt((int) this.carRepository.count()) + 1;
        Car car = this.carRepository.findById(id).get();
        return car;
    }
}
