package com.xmls.services.impl;


import com.xmls.domain.dtos.ImportDtos.CarImportDto;
import com.xmls.domain.dtos.ImportDtos.CarImportRootDto;
import com.xmls.domain.dtos.export.CarExportDto;
import com.xmls.domain.dtos.export.CarExportRootDto;
import com.xmls.domain.dtos.export.PartExportDto;
import com.xmls.domain.dtos.export.PartExportRootDto;
import com.xmls.domain.entities.Car;
import com.xmls.domain.entities.Part;
import com.xmls.domain.repositories.CarRepository;
import com.xmls.domain.repositories.PartRepository;
import com.xmls.services.CarService;
import com.xmls.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private final static String CAR_PATH = "src/main/resources/xmls/cars.xml";
    private final static String CAR_PARTS_PATH = "Z:\\JAVA\\SPRING\\10xmlEXERCISElastOne\\src\\main\\resources\\xmls\\exported\\cars-and-parts.xml";

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    @Autowired
    public CarServiceImpl(ModelMapper modelMapper, CarRepository carRepository, PartRepository partRepository, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
    }


    @Override

    public void seedCars() throws Exception {

        CarImportRootDto carImportRootDto = this.xmlParser.parseXml(CarImportRootDto.class, "Z:\\JAVA\\SPRING\\10xmlEXERCISElastOne\\src\\main\\resources\\xmls\\cars.xml" );

        for (CarImportDto  carDto :carImportRootDto.getCars() ) {
            Car car = this.modelMapper.map(carDto, Car.class);
            car.setParts(getRandomParts());

            this.carRepository.saveAndFlush(car);

        }

    }


    private Set<Part> getRandomParts() throws Exception {
        Set<Part> parts = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Part part = this.getRandomPart();
            parts.add(part);
        }
        return parts;
    }

    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long index = random.nextInt((int) this.partRepository.count()) + 1;
        Optional<Part> part = this.partRepository.findById(index);

        if (part.isPresent()) {
            return part.get();
        } else {
            throw new Exception("Invalid!");
        }
    }

    @Override
    public void carsAndParts() throws JAXBException {
        List<Car> all = this.carRepository.findAll();
        CarExportRootDto carRootDto = new CarExportRootDto();
        List<CarExportDto> carExportDtos = new ArrayList<>();

        for (Car car : all) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);

            PartExportRootDto partRootDto = new PartExportRootDto();
            List<PartExportDto> partExportDtos = new ArrayList<>();

            for (Part part : car.getParts()) {
                PartExportDto partDto = this.modelMapper.map(part, PartExportDto.class);
                partExportDtos.add(partDto);

            }

            partRootDto.setParts(partExportDtos);
            carExportDto.setParts(partRootDto);
            carExportDtos.add(carExportDto);
        }
        carRootDto.setCars(carExportDtos);

        this.xmlParser.exportXml(carRootDto,CarExportRootDto.class,CAR_PARTS_PATH);


    }


}































































