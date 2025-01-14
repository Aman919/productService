package com.scaler.productservicedecmwfeve.productService.Service;

import com.scaler.productservicedecmwfeve.productService.Exceptions.CategoryNotExistException;
import com.scaler.productservicedecmwfeve.productService.Repository.CategoryRepository;
import com.scaler.productservicedecmwfeve.productService.modal.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelfCategoryService implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public SelfCategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if(categoryOptional.isEmpty()){
//            throw new CategoryNotExistException("Category with id: "+ id+ "doesn't exist");
            throw new RuntimeException();
        }


        Category category = categoryOptional.get();
        return category;
    }
}
