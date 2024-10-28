package com.deverything.candidate.productstore;

import com.deverything.candidate.productstore.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiServiceTest {

  @Autowired
  ApiService api;

  @Test
  public void testAllTheThings() {
    // mock api calls
    // expect call count
    // assert values
    System.out.println("Let's get all products from the API:");
    System.out.println("YOUR-RESULT-HERE");

    // mock api calls
    // expect call count
    // assert product list size based on the price range
    // assert values
    System.out.println("Let's list all products with a price higher then 300");
    System.out.println("YOUR-RESULT-HERE");

    System.out.println("Let's get product dimensions for products with id 3 and 7");
    System.out.println("YOUR-RESULT-HERE");

    System.out.println("Get all boxes and choose the best one that fits both the products 3 and 7 in a single box");
    System.out.println("YOUR-RESULT-HERE");

    System.out.println("Now we place the order using the checkout in the API");
    System.out.println("YOUR-RESULT-HERE");
  }
}