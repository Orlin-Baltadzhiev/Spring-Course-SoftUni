package softuni.cardealer.service;

import java.io.IOException;

public interface SupplierService {

    void seedSupplier() throws IOException;

    String findSupplierIsImportFalse();
}
