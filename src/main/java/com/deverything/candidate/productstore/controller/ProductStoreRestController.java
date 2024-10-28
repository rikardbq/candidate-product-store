package com.deverything.candidate.productstore.controller;

import com.deverything.candidate.productstore.model.controller.CheckoutRequest;
import com.deverything.candidate.productstore.service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product-store")
public class ProductStoreRestController {

  @Autowired
  ApiServiceImpl apiService;

  @PostMapping(value = "/checkout", produces = "application/json")
  public Object checkout(@RequestBody CheckoutRequest checkoutRequest) {
    // stuff done here
    System.out.println(checkoutRequest.getProductIds());
    apiService.calculatePackagingForProducts(checkoutRequest.getPriceThreshold(), checkoutRequest.getProductIds());
    return null;
  }
}
