package com.deverything.candidate.productstore.service;

import com.deverything.candidate.productstore.ProductStoreApplication;
import com.deverything.candidate.productstore.model.api.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ApiServiceImpl implements ApiService<ProductObject, ProductDimensionsObject, BoxListObject, CheckoutObject, CheckoutSummaryObject> {
  private static final Logger LOGGER = LogManager.getLogger();

  private static final String API_BASE_PATH = Objects.requireNonNullElse(System.getenv("API_BASE_PATH"), "http://localhost:8080");
  private static final String API_KEY = Objects.requireNonNullElse(System.getenv("API_KEY"), "SOME_KEY");
  private static final String API_USER = Objects.requireNonNullElse(ProductStoreApplication.applicationProperties().getProperty("API_USER"), "some_guy");

  private final RestTemplate restTemplate;

  public ApiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public ResponseEntity<ProductObject> getProducts() {

    return makeGETRequest("/products", ProductObject.class);
  }

  @Override
  public ResponseEntity<ProductDimensionsObject> getProductDimensions(int productId) {

    return makeGETRequest("/products/{productId}", ProductDimensionsObject.class, productId);
  }

  @Override
  public ResponseEntity<BoxListObject> getBoxes() {

    return makeGETRequest("/boxes", BoxListObject.class);
  }

  @Override
  public ResponseEntity<CheckoutSummaryObject> checkout(CheckoutObject checkoutObject) {
    return makePOSTRequest(checkoutObject, CheckoutSummaryObject.class);
  }

  public void calculatePackagingForProducts(long priceThreshold, List<Integer> productIds) {
    List<ProductDimensionsObject> productDimensionsObjects = new ArrayList<>();
    List<Product> products = getProducts().getBody().getProducts().stream().filter(p -> p.getPrice() > priceThreshold).collect(Collectors.toList());

    for (Product product : products) {

      if (productIds.contains(product.getId())) {
        ProductDimensionsObject productDimensionsObject = getProductDimensions(product.getId()).getBody();
        productDimensionsObjects.add(productDimensionsObject);
      }
    }

    List<Box> boxes = getBoxes().getBody().getBoxes();
    int totalWidth = productDimensionsObjects.stream()
        .map(ProductDimensionsObject::getWidth)
        .reduce(0, Integer::sum);
    int maxHeight = productDimensionsObjects.stream()
        .map(ProductDimensionsObject::getHeight)
        .max(Comparator.comparingInt(o -> o)).get();

    LOGGER.info("HELLO WORLD WIDTH, HEIGHT {} {}", totalWidth, maxHeight);

    Box boxThatFits = boxes.stream().filter(b -> b.getHeight() >= maxHeight && b.getWidth() >= totalWidth).findFirst().get();

    LOGGER.info("BOXX {}", boxThatFits);
  }

  private HttpEntity<?> makeHttpEntity(Object requestObj) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("USER", API_USER);
    headers.set("APIKEY", API_KEY);

    return new HttpEntity<>(requestObj, headers);
  }

  private <T> ResponseEntity<T> makeGETRequest(String path, Class<T> responseType, Object... uriVariables) {
    LOGGER.info("GET -> {}{}{}", API_BASE_PATH, path, uriVariables);
    HttpHeaders headers = new HttpHeaders();
    headers.set("USER", API_USER);
    headers.set("APIKEY", API_KEY);
    return restTemplate.exchange(
        API_BASE_PATH + path,
        HttpMethod.GET,
        new HttpEntity<>(headers),
        responseType,
        uriVariables
    );
  }

  private <T> ResponseEntity<T> makePOSTRequest(Object requestObj, Class<T> responseType) {
    return restTemplate.exchange(
        API_BASE_PATH,
        HttpMethod.POST,
        makeHttpEntity(requestObj),
        responseType
    );
  }
}
