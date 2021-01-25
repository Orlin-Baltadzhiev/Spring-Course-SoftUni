package softuni.cardealer.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.importt.PartSeedDto;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.PartRepository;
import softuni.cardealer.domain.repositories.SupplierRepository;
import softuni.cardealer.service.PartService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private static final String PART_PATH = "Z:\\JAVA\\SPRING\\9 JSON PROCESSING exercise\\src\\main\\resources\\json\\parts.json";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedPart() throws Exception {
        //read Json
        //JSON -> DTO
        //dto save db
        String content = String.join("",
                Files.readAllLines
                        (Path.of("Z:\\JAVA\\SPRING\\9 JSON PROCESSING exercise\\src\\main\\resources\\json\\parts.json")));
        PartSeedDto[] partSeedDto = this.gson.fromJson(content,PartSeedDto[].class);
        for (PartSeedDto PartseedDtoO : partSeedDto) {
               Part part = this.modelMapper.map(PartseedDtoO,Part.class);
               part.setSupplier(getRandomSupplier());

               this.partRepository.saveAndFlush(part);
        }
    }

    private Supplier getRandomSupplier() throws Exception {
        Random random = new Random();
        int index =  random.nextInt((int)this.supplierRepository.count()) + 1;
        Optional<Supplier> supplier = this.supplierRepository.findById((long) index);

        if(supplier.isPresent()){
            return supplier.get();
        } else {
            throw  new Exception("Supplier don`t exist");
        }
    }
}
