package com.springdata.mapping.service;

import com.springdata.mapping.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<Address> getAllAddresses();
    Address getAddressById(Long id);
    Address addAddress(Address Address);
    Address updateAddress(Address Address);
    Address deleteAddress(Long id);
    Long getAddressCount();


}
