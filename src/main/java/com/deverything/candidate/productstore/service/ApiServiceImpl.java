package com.deverything.candidate.productstore.service;

import com.deverything.candidate.productstore.model.ProductObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class ApiServiceImpl implements ApiService {
    private static final String API_BASE_PATH = Objects.requireNonNullElse(System.getenv("API_BASE_PATH"), "http://localhost:8080");
    private static final String API_KEY = Objects.requireNonNullElse(System.getenv("API_KEY"), "SOME_KEY");

    private final RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<ProductObject> getProducts() {

        return restTemplate.exchange(
                API_BASE_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductObject>>() {
                }
        ).getBody();
    }

    @Override
    public Object getProductDimensions(int productId) {
        return null;
    }

    @Override
    public Object getBoxes() {
        return null;
    }

    @Override
    public Object checkout(Object o) {
        return null;
    }

  /*public static void main(String[] args) {
    System.out.println(API_BASE_PATH);
  }*/
}
