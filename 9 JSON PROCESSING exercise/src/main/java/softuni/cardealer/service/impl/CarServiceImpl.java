package softuni.cardealer.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.cardealer.domain.dtos.export.CarExportDto;
import softuni.cardealer.domain.dtos.importt.CarSeedDto;
import softuni.cardealer.domain.entities.Car;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.repositories.CarRepository;
import softuni.cardealer.domain.repositories.PartRepository;
import softuni.cardealer.service.CarService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {
    private static final String CARS_PATH = "Z:\\JAVA\\SPRING\\9 JSON PROCESSING exercise\\src\\main\\resources\\json\\cars.json";
    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {
        String content = String.join("", Files.readAllLines(Path.of(CARS_PATH)));
        CarSeedDto[] carSeedDtos = this.gson.fromJson(content, CarSeedDto[].class);

        for (CarSeedDto carSeedDto : carSeedDtos) {
            Car car = this.modelMapper.map(carSeedDto, Car.class);
            car.setParts(setRandomParts());
            this.carRepository.saveAndFlush(car);
        }
    }

    @Override
    public String findByToyota() {
        Set<Car> toyota = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        List<CarExportDto> carExportDtos = new ArrayList<>();
        for (Car car : toyota) {
            CarExportDto carsToAdd = this.modelMapper.map(car, CarExportDto.class);
            carExportDtos.add(carsToAdd);
        }
        return this.gson.toJson(carExportDtos);
    }


    private Set<Part> setRandomParts() throws Exception {

        Set<Part> parts = new HashSet<>();


        for (int i = 0; i < 3; i++) {

            Part part = this.getRandomParts();

            parts.add(part);
        }
        return parts;
    }

    private Part getRandomParts() throws Exception {
        Random random = new Random();
        long index = random.nextInt((int) this.partRepository.count()) + 1;
        Optional<Part> part = this.partRepository.findById(index);

        if(part.isPresent()){
            return part.get();

        }else {
            throw new Exception("Invalid part id");
        }
    }
}
