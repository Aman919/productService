package com.scaler.productservicedecmwfeve.productService.modal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {

    private String name;
    @OneToMany(mappedBy="category", cascade = {CascadeType.REMOVE})//being already mapped by an attribute called category
    private List<Product> products;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
