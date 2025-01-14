package com.scaler.productservicedecmwfeve.productService.inheritanceDemo.singledClass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "2")
public class Instructor extends User {
    private String favouriteStudent;
}
