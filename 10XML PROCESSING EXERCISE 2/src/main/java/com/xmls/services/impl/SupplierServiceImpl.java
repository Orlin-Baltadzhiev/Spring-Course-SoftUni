package com.xmls.services.impl;



import com.xmls.domain.dtos.ImportDtos.SupplierImportDto;
import com.xmls.domain.dtos.ImportDtos.SupplierImportRootDto;
import com.xmls.domain.entities.Supplier;
import com.xmls.domain.repositories.SupplierRepository;
import com.xmls.services.SupplierService;
import com.xmls.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final static String SUPPLIER_PATH = "Z:\\JAVA\\SPRING\\10xmlEXERCISElastOne\\src\\main\\resources\\xmls\\suppliers.xml";

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;



    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedSupplierInDB() throws IOException, JAXBException {
        SupplierImportRootDto supplierImportRootDto = this.xmlParser.parseXml(SupplierImportRootDto.class, "src/main/resources/xmls/suppliers.xml");
        System.out.println();
        for (SupplierImportDto supplier : supplierImportRootDto.getSupplier()) {
            this.supplierRepository.save(this.modelMapper.map(supplier, Supplier.class));

        }

    }
}
