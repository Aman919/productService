package com.scaler.productservicedecmwfeve.productService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomTest {
    @Test
    void testOnePlusOneIsTwo(){
        int i=1+1; //act

        assert i==2;
        assertTrue(i==2);
    }
}
//a testcase is nothing but a method
//testcase method is doing: AAA
// -> Arrange
// -> Act
// -> Assert
//create a method for every testcase
//A testcase fails if any assertions within that test fails.
