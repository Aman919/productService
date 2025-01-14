package com.scaler.productservicedecmwfeve.productService.Service;

import com.scaler.productservicedecmwfeve.productService.Exceptions.ProductNotExistException;
import com.scaler.productservicedecmwfeve.productService.modal.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotExistException;

    List<Product> getAllProducts();

    Product replaceProduct(Long id, Product product);
    Product updateProduct(Long id, Product product);

    Product addNewProduct(Product product);
//    Boolean deleteProduct(Long id) throws ProductNotExistException;

}
