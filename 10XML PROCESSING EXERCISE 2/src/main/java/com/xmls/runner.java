package com.xmls;

import com.xmls.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class runner implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public runner(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
//       this.supplierService.seedSupplierInDB();
//        this.partService.seedParts();
//        this.carService.seedCars();
     //   this.customerService.seedCustomers();
      //  this.saleService.seedSalesInDB();
      //  this.customerService.exportOrdered();
        this.carService.carsAndParts();

    }
}
