package com.springdata.mapping.service.impl;

import com.springdata.mapping.Repository.AddressRepository;
import com.springdata.mapping.entity.Address;
import com.springdata.mapping.exception.NoneExistingEntityException;
import com.springdata.mapping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {
    private AddressRepository AddressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepository AddressRepo) {
        this.AddressRepo = AddressRepo;
    }

    @Override
    public List<Address> getAllAddresses() {
        return AddressRepo.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return AddressRepo.findById(id).orElseThrow(
                () ->  new NoneExistingEntityException
                        (String.format("Address with ID=%s deos not exists.",id)));
    }

    @Override
    @Transactional
    public Address addAddress(Address Address) {
        Address.setId(null);
        return AddressRepo.save(Address);
    }

    @Override
    @Transactional
    public Address updateAddress(Address Address) {
        getAddressById(Address.getId());
        return AddressRepo.save(Address);
    }

    @Override
    @Transactional
    public Address deleteAddress(Long id) {
        Address removed = getAddressById(id);
      AddressRepo.deleteById(id);
      return removed;
    }

    @Override
    public Long getAddressCount() {
        return AddressRepo.count();
    }
}
