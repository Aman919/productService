package com.scaler.productservicedecmwfeve.productService.inheritanceDemo.mappedSuperClass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ms_instructor")
public class Instructor extends User {
    private String favouriteStudent;
}
