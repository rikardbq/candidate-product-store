package com.deverything.candidate.productstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApiServiceTest {

    @Autowired
    ApiService api;

    @Test
    public void testAllTheThings(){
        System.out.println("Let's get all products from the API:");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Let's list all products with a price higher then 300");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Let's get product dimensions for products with id 3 and 7");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Create a box using the API with size to fit both the products 3 and 7 in a single box");
        System.out.println("YOUR-RESULT-HERE");

        System.out.println("Now we place the order using the checkout in the API");
        System.out.println("YOUR-RESULT-HERE");
    }
}