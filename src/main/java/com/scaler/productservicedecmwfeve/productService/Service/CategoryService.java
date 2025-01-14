package com.scaler.productservicedecmwfeve.productService.Service;

import com.scaler.productservicedecmwfeve.productService.Exceptions.CategoryNotExistException;
import com.scaler.productservicedecmwfeve.productService.modal.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();

    Category getCategoryById(Long id);
}
