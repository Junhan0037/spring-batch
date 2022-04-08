package com.springbatch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

}
