package com.scaler.productservicedecmwfeve.productService.Controllers;

import com.scaler.productservicedecmwfeve.productService.Repository.CategoryRepository;
import com.scaler.productservicedecmwfeve.productService.Service.CategoryService;
import com.scaler.productservicedecmwfeve.productService.modal.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService){
        this.categoryRepository=categoryRepository;
        this.categoryService=categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        ResponseEntity<List<Category>> response = new ResponseEntity<>(
                categoryService.getAllCategory(), HttpStatus.OK
        );
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id){
        ResponseEntity<Category> response = new ResponseEntity<>(
                categoryService.getCategoryById(id),
                HttpStatus.OK
        );
        return response;
    }

    @PostMapping
    public ResponseEntity<Category> addNewCategory(@RequestBody Category category){
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable("id") Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        category.setId(id);
        Category savedProduct = categoryRepository.save(category);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("id")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
