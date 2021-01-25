package com.xmls.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {

    void seedCustomers() throws JAXBException;


    void exportOrdered() throws JAXBException;



}
