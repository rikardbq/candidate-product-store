package com.deverything.candidate.productstore.controller;

import com.deverything.candidate.productstore.model.controller.CheckoutRequest;
import com.deverything.candidate.productstore.model.controller.CheckoutResponse;
import com.deverything.candidate.productstore.model.controller.Result;
import com.deverything.candidate.productstore.service.ApiServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("product-store")
public class ProductStoreRestController {
  private static final Logger LOGGER = LogManager.getLogger();

  @Autowired
  ApiServiceImpl apiService;

  @PostMapping(value = "/checkout", produces = "application/json")
  public Object checkout(@RequestBody CheckoutRequest checkoutRequest) {
    CheckoutResponse response = new CheckoutResponse(new Result());

    try {
      response.getResult().setValue(
          apiService.calculatePackagingForProducts(
              checkoutRequest.getPriceThreshold(),
              checkoutRequest.getProductIds()
          ).getBody()
      );
    } catch (Exception e) {
      LOGGER.error("ERROR={},MESSAGE={},CAUSE={}", e.getClass().getName(), e.getMessage(), e.getCause().getMessage());

      response.getResult().setError(Arrays.asList(
          e.getClass().getSimpleName(),
          e.getMessage(),
          e.getCause().getMessage()
      ));
    }

    return response;
  }
}
