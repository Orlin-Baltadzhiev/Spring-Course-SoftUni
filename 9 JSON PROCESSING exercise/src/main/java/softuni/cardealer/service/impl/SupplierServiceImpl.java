package softuni.cardealer.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.export.SupplierExportDto;
import softuni.cardealer.domain.dtos.importt.SupplierSeedDto;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.PartRepository;
import softuni.cardealer.domain.repositories.SupplierRepository;
import softuni.cardealer.service.SupplierService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final static String SUPPLIER_PATH = "src/main/resources/json/suppliers.json";
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SupplierServiceImpl(SupplierRepository supplierRepository, PartRepository partRepository, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSupplier() throws IOException {
        String content = String.join("", Files
                .readAllLines(Path.of("Z:\\JAVA\\SPRING\\9 JSON PROCESSING exercise\\src\\main\\resources\\json\\suppliers.json")));
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplierSeedDto,Supplier.class));
        }
        //read Json
        //JSON -> DTO
        //dto save db
    }

    @Override
    public String findSupplierIsImportFalse() {
        Set<Supplier> orderSupplier = this.supplierRepository.findAllByImporterIsFalse();

        List<SupplierExportDto> toExport = new ArrayList<>();

        for (Supplier supplier : orderSupplier) {
            SupplierExportDto supplierExportDto = this.modelMapper.map(supplier, SupplierExportDto.class);
            long idToFind = supplierExportDto.getId();
            supplierExportDto.setPartCount(getPartsCount(this.partRepository.getOne(idToFind)));
            toExport.add(supplierExportDto);

        }
        return this.gson.toJson(toExport);
    }

    private int getPartsCount(Part one) {
        return this.partRepository.findParts(one.getId());
    }


}
