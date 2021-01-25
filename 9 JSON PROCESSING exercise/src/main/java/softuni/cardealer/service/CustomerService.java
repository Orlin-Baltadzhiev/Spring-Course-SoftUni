package softuni.cardealer.service;

import java.io.IOException;

public interface CustomerService {

    void seedCustomer() throws IOException;

    String orderedCustomers();
}
