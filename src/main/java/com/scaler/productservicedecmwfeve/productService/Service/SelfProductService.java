package com.scaler.productservicedecmwfeve.productService.Service;

import com.scaler.productservicedecmwfeve.productService.Exceptions.ProductNotExistException;
import com.scaler.productservicedecmwfeve.productService.Repository.CategoryRepository;
import com.scaler.productservicedecmwfeve.productService.Repository.ProductRepository;
import com.scaler.productservicedecmwfeve.productService.modal.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {
    //if there are multiple implemtation of an interface and there is an ambiguity at the time of calling the
    // object of one particular implemetation of that interface, then if @primary if present in a class, that
    // implementation will always takes precedence in case of an ambiguity. or use a Qualifiers.
//with @Service annotation, we inform spring that this is an important class, pls ensure to create an object of it and put it in application context

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
    }

    //@Autowired means we are informing spring that the arguments of the class i.e. productRepository
    // and categoryRepository would not be provided by anyone, so just take the bean from the applicationcontext

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistException {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotExistException("Product with id: "+id + " doesn't exist");
        }

        Product product = productOptional.get();
        return product;
        //or a better code is :
        // return productRepository.findById(id)
        //                .orElseThrow(()->new ProductNotExistException("Product with id: "+id +"doesn't exist");
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        Optional<Product> OptionalProduct = productRepository.findById(id);

        if(OptionalProduct.isEmpty()) throw new RuntimeException();

        Product savedProduct=OptionalProduct.get();

        if(product.getTitle() != null){
            savedProduct.setTitle(product.getTitle());
        }

        if(product.getDescription() != null){
            savedProduct.setDescription(product.getDescription());
        }

        if(product.getImage() !=null){
            savedProduct.setImage(product.getImage());
        }

        if(product.getPrice() != null){
            savedProduct.setPrice(product.getPrice());
        }
        return productRepository.save(savedProduct);
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        return null;
    }

    @Override
    public Product addNewProduct(Product product) {
        if(product==null || product.getTitle()==null || product.getPrice() ==null){
            throw new IllegalArgumentException("Invalid product data");
        }

        return  productRepository.save(product);
    }

//    @Override
//    public Boolean deleteProduct(Long id) throws ProductNotExistException {
//        if(!productRepository.existsById(id)){
//            throw new ProductNotExistException("Product with id: "+id+" doesn't exist");
//        }
//        productRepository.deleteById(id);
//        return true;
//    }

}
