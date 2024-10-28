package com.deverything.candidate.productstore.controller;

import com.deverything.candidate.productstore.model.ProductObject;
import com.deverything.candidate.productstore.service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("something")
public class ProductStoreRestController {

  @Autowired
  ApiServiceImpl apiService;

  @GetMapping(name = "/{id}", produces = "application/json")
  public Object getProduct(@PathVariable int id) {
    return apiService.getProductDimensions(id);
  }
}
