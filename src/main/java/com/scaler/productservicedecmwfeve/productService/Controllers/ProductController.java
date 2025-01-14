package com.scaler.productservicedecmwfeve.productService.Controllers;


import com.scaler.productservicedecmwfeve.productService.Exceptions.ProductNotExistException;
import com.scaler.productservicedecmwfeve.productService.Repository.CategoryRepository;
import com.scaler.productservicedecmwfeve.productService.Repository.ProductRepository;
import com.scaler.productservicedecmwfeve.productService.Service.ProductService;
import com.scaler.productservicedecmwfeve.productService.commons.AuthenticationCommons;
import com.scaler.productservicedecmwfeve.productService.dto.UserDto;
import com.scaler.productservicedecmwfeve.productService.dto.Role;
import com.scaler.productservicedecmwfeve.productService.modal.Category;
import com.scaler.productservicedecmwfeve.productService.modal.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final RestTemplate restTemplate;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             RestTemplate restTemplate,
                             CategoryRepository categoryRepository,
                             ProductRepository productRepository,
                             AuthenticationCommons authenticationCommons) {
        this.restTemplate = restTemplate;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.productRepository=productRepository;
        this.authenticationCommons=authenticationCommons;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader("AuthenticationToken") String token) {

        UserDto userDto=authenticationCommons.validateToken(token);

        if(userDto==null){
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);
       }

        boolean isAdmin =  false;
        for(Role role: userDto.getRoles()){
            if(role.getName().equals("ADMIN")){
                isAdmin=true;
                break;
            }
        }

        if(!isAdmin) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ResponseEntity<List<Product>> response = new ResponseEntity<>(
                productService.getAllProducts(), HttpStatus.OK
        );
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotExistException {

        ResponseEntity<Product> response = new ResponseEntity<>(
                productService.getSingleProduct(id),
                HttpStatus.OK
        );
        return response;
    }

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestBody Product product) {

        Optional<Category> categoryOptional = categoryRepository.findByName(product.getCategory().getName());

        if(categoryOptional.isEmpty()){
            product.setCategory(categoryRepository.save(product.getCategory()));
        }else{
            product.setCategory(categoryOptional.get());
        }

//        Category category = product.getCategory();
//        if(category.getId() == null){
//            Category savedCategory = categoryRepository.save(category);
//            product.setCategory(savedCategory);
//        }
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if(existingProductOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Product existingProduct=existingProductOptional.get();

        if(product.getTitle()!=null){
            existingProduct.setTitle(product.getTitle());
        }
        if(product.getPrice()!=null){
            existingProduct.setPrice(product.getPrice());
        }
        if(product.getDescription()!=null){
            existingProduct.setDescription(product.getDescription());
        }
        if(product.getCategory()!=null){
            Optional<Category> categoryOptional=categoryRepository.findByName(product.getCategory().getName());
            existingProduct.setCategory(categoryOptional.orElseGet(()->categoryRepository.save(existingProduct.getCategory())));
        }
        Product savedProduct=productRepository.save(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if(existingProductOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
//        product.setId(id);
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    ///Exception handler in other directory works across the controller, so if any controller throws an exception the exception handler will catch and throw the response
    ///but if we want to make controller specific exception handler, we need to define the handler in that particular handler like below:
    @ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<Void> handleProductNotExistException() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    //so, if an exception occurs in a controller then jvm will first check if there is an exceptionhandler in the controller level if not it will search in the global level

}
