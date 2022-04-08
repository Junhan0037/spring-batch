package com.springbatch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Teacher {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    public Teacher(String name) {
        this.name = name;
        this.students = new HashSet<>();
    }

}
