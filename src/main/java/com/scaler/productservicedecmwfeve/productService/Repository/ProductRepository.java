package com.scaler.productservicedecmwfeve.productService.Repository;

import com.scaler.productservicedecmwfeve.productService.Repository.projections.ProductWithIdAndTitle;
import com.scaler.productservicedecmwfeve.productService.modal.Category;
import com.scaler.productservicedecmwfeve.productService.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContaining(String word);

    Long deleteByTitle(String title);
    List<Product> findByTitleAndDescription(String title, String Description);
//    List<Product> findByPriceLessThanEqual(double price);
    List<Product> findByPriceBetween(double startRange, double endRange);
//    List<Category> findByCategory(Category category);
//    List<Category> findByIdAndCategoryOrderByTitle(Long id, Category category, String title);
    List<Product> findByCategory_Id(Long id);
    Optional<Product> findById(Long id);

// this above method will return null, if no product matches the id

    Product save(Product product);
//HQL
//    @Query("select p.id as id, p.title as title from Product p where p.price=? and p.description like '%?%' ")
//    List<ProductWithIdAndTitle> somethingSomething();

//below is a native query, it will be executed as it is in the database, while in HQL, we are using the class and hibernate will modify the query
//these type of queries are tightly coupled with the database, so if we shift from sql to mongo or postgres, we have to change the query as well.
    @Query(value="select * from product p where p.id= :id", nativeQuery = true)
    List<Product> somesome2(@Param("id") Long id);

    //native queries using projections
    @Query(value="select p.id, p.title from product where p.id=52", nativeQuery = true)
    List<ProductWithIdAndTitle> somesome3();
}
