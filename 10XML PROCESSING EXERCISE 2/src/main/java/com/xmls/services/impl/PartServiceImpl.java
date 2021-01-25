package com.xmls.services.impl;

import com.xmls.domain.dtos.ImportDtos.PartImportDto;
import com.xmls.domain.dtos.ImportDtos.PartImportRootDto;
import com.xmls.domain.entities.Part;
import com.xmls.domain.entities.Supplier;
import com.xmls.domain.repositories.PartRepository;
import com.xmls.domain.repositories.SupplierRepository;
import com.xmls.services.PartService;
import com.xmls.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final static String PART_PATH = "src/main/resources/xmls/parts.xml";

    private final ModelMapper modelMapper;
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;


    @Autowired
    public PartServiceImpl(ModelMapper modelMapper, PartRepository partRepository, SupplierRepository supplierRepository, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedParts() throws Exception {

        PartImportRootDto partImportRootDto = this.xmlParser.parseXml(PartImportRootDto.class, PART_PATH);

        for (PartImportDto partDto : partImportRootDto.getParts()) {

            Part part=this.modelMapper.map(partDto, Part.class);

            part.setSupplier(this.getRandomSupplier());

            this.partRepository.saveAndFlush(part);
        }

    }

    private Supplier getRandomSupplier() throws Exception {
        Random random = new Random();

        long index = random.nextInt((int) this.supplierRepository.count()) + 1;

        Optional<Supplier> supplier = this.supplierRepository.findById(index);

        if (supplier.isPresent()) {
            return supplier.get();
        } else {
            throw new Exception("Supplier doesn't exist");
        }

    }


}
