package com.springbatch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NoArgsConstructor
public class StoreHistory {

    @Id @GeneratedValue
    private Long id;

    private String storeName;
    private String productNames;
    private String employeeNames;

    public StoreHistory(Store store, List<Product> products, List<Employee> employees) {
        this.storeName = store.getName();
        this.productNames = products.stream().map(Product::getName).collect(Collectors.joining(","));
        this.employeeNames = employees.stream().map(Employee::getName).collect(Collectors.joining(","));
    }



}
