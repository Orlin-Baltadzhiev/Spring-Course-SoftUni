package softuni.cardealer.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.export.CustomerExportDto;
import softuni.cardealer.domain.dtos.importt.CustomerSeedDto;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.repositories.CustomerRepository;
import softuni.cardealer.service.CustomerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl  implements CustomerService {
    private static final String CUSTOMER_PATH = "Z:\\JAVA\\SPRING\\9 JSON PROCESSING exercise\\src\\main\\resources\\json\\customers.json";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;

    }


    @Override
    public void seedCustomer() throws IOException {
        //reed json
        //jason -> DTO
        //DTO save db

        String content = String.join("",Files.readAllLines(Path.of(CUSTOMER_PATH)));
        CustomerSeedDto[] customerSeedDto = this.gson.fromJson(content,CustomerSeedDto[].class);

        for (CustomerSeedDto seedDto : customerSeedDto) {
            this.customerRepository
                    .saveAndFlush(this.modelMapper.map(seedDto, Customer.class));
        }


    }

    @Override
    public String orderedCustomers() {
        Set<Customer> orderCustomers = this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc();

        List<CustomerExportDto> toExport = new ArrayList<>();

        for (Customer customer : orderCustomers) {
            CustomerExportDto customerExport = this.modelMapper.map(customer, CustomerExportDto.class);
            //modelMapper.validate();
            toExport.add(customerExport);
        }
        
     return this.gson.toJson(toExport);
    }
}
