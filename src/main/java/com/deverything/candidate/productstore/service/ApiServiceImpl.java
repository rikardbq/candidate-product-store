package com.deverything.candidate.productstore.service;

import com.deverything.candidate.productstore.ProductStoreApplication;
import com.deverything.candidate.productstore.model.api.*;
import com.deverything.candidate.productstore.model.exception.HttpNoContentException;
import com.deverything.candidate.productstore.model.exception.NoMatchingCriteriaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
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

    return makePOSTRequest("/checkout", checkoutObject, CheckoutSummaryObject.class);
  }

  public ResponseEntity<CheckoutSummaryObject> calculatePackagingForProducts(long priceThreshold, List<Integer> productIds) throws HttpNoContentException, NoMatchingCriteriaException {
    List<ProductDimensionsObject> productDimensionsObjects = new ArrayList<>();
    ResponseEntity<ProductObject> productObject = getProducts();

    if (productObject.getBody() == null) {
      throw new HttpNoContentException("ProductObject");
    }

    List<Product> products = productObject.getBody()
        .getProducts().stream()
        .filter(p -> p.getPrice() > priceThreshold && productIds.contains(p.getId()))
        .collect(Collectors.toList());

    if (products.isEmpty()) {
      throw new NoMatchingCriteriaException(
          "Product list empty, no matching criteria",
          "No products matched the criteria [productPrice > priceThreshold] with threshold=" + priceThreshold
      );
    }

    for (Product product : products) {
      ResponseEntity<ProductDimensionsObject> productDimensionsObject = getProductDimensions(product.getId());

      if (productDimensionsObject.getBody() == null) {
        throw new HttpNoContentException("ProductDimensionsObject");
      }

      productDimensionsObjects.add(productDimensionsObject.getBody());
    }

    ResponseEntity<BoxListObject> boxListObject = getBoxes();

    if (boxListObject.getBody() == null) {
      throw new HttpNoContentException("BoxListObject");
    }

    int totalWidth = productDimensionsObjects.stream()
        .map(ProductDimensionsObject::getWidth)
        .reduce(0, Integer::sum);
    int maxHeight = productDimensionsObjects.stream()
        .map(ProductDimensionsObject::getHeight)
        .max(Comparator.comparingInt(o -> o)).get();

    Optional<Box> boxOptional = boxListObject.getBody().getBoxes().stream()
        .filter(b -> b.getHeight() >= maxHeight && b.getWidth() >= totalWidth)
        .findFirst();

    if (boxOptional.isEmpty()) {
      throw new NoMatchingCriteriaException(
          "Box doesn't exist, no matching criteria",
          "No box matched the criteria [boxHeight >= productsMaxHeight && boxWidth >= productsTotalWidth] with productsMaxHeight=" + maxHeight + ",productsTotalWidth=" + totalWidth
      );
    }

    return checkout(new CheckoutObject(
            boxOptional.get().getId(),
            products.stream()
                .map(Product::getId)
                .collect(Collectors.toList())
        )
    );
  }

  private HttpEntity<?> makeHttpEntity(Object requestObj) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("USER", API_USER);
    headers.set("APIKEY", API_KEY);

    return new HttpEntity<>(requestObj, headers);
  }

  private <T> ResponseEntity<T> makeGETRequest(String path, Class<T> responseType, Object... uriVariables) {
    LOGGER.info("GET -> basePath={},path={},uriVariables={}", API_BASE_PATH, path, uriVariables);

    return restTemplate.exchange(
        API_BASE_PATH + path,
        HttpMethod.GET,
        makeHttpEntity(null),
        responseType,
        uriVariables
    );
  }

  private <T> ResponseEntity<T> makePOSTRequest(String path, Object requestObj, Class<T> responseType) {
    LOGGER.info("POST -> basePath={},path={}", API_BASE_PATH, path);

    return restTemplate.exchange(
        API_BASE_PATH + path,
        HttpMethod.POST,
        makeHttpEntity(requestObj),
        responseType
    );
  }
}
