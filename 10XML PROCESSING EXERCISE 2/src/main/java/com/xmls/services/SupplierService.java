package com.xmls.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SupplierService {

    void seedSupplierInDB() throws IOException, JAXBException;


}
