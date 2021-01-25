package com.xmls.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    private String name;
    private double price;
    private int quantity;
    private Supplier supplier;
    private Set<Car> cars;

    public Part() {
    }

    @ManyToMany(mappedBy = "parts", fetch = FetchType.EAGER)
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
