package com.xmls.services.impl;


import com.google.gson.Gson;
import com.xmls.domain.dtos.ImportDtos.CustomerImportDto;
import com.xmls.domain.dtos.ImportDtos.CustomerImportRootDto;
import com.xmls.domain.dtos.export.CustomOrderedRootExportDto;
import com.xmls.domain.dtos.export.CustomerOrderedExportDto;
import com.xmls.domain.entities.Customer;
import com.xmls.domain.repositories.CustomerRepository;
import com.xmls.services.CustomerService;
import com.xmls.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final static String CUSTOMER_PATH = "Z:\\JAVA\\SPRING\\10xmlEXERCISElastOne\\src\\main\\resources\\xmls\\customers.xml";
    private final static String CUSTOMER_ORDERED_PATH = "Z:\\JAVA\\SPRING\\10xmlEXERCISElastOne\\src\\main\\resources\\xmls\\exported\\ordered-customers.xml";

    private final ModelMapper modelMapper;
   private final XmlParser xmlParser;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ModelMapper modelMapper, Gson gson, XmlParser xmlParser, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.customerRepository = customerRepository;
    }


    @Override
    public void seedCustomers() throws JAXBException {
        CustomerImportRootDto customerImportRootDto = this.xmlParser.parseXml(CustomerImportRootDto.class, CUSTOMER_PATH);

        for (CustomerImportDto customerDto : customerImportRootDto.getCustomers()) {
             this.customerRepository.saveAndFlush(this.modelMapper.map(customerDto, Customer.class));

        }
        
    }

    @Override
    public void exportOrdered() throws JAXBException {
        List<CustomerOrderedExportDto> dtos = this.customerRepository.findAllAndSort()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedExportDto.class))
                .collect(Collectors.toList());

        CustomOrderedRootExportDto rootDto = new CustomOrderedRootExportDto();
    rootDto.setCustomers(dtos);
    this.xmlParser.exportXml(rootDto, CustomOrderedRootExportDto.class, CUSTOMER_ORDERED_PATH);
    }
}
