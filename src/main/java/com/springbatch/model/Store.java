package com.springbatch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Store {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setStore(this);
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setStore(this);
    }

}
