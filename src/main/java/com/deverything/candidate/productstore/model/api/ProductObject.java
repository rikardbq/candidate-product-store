package com.deverything.candidate.productstore.model.api;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProductObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String statusCode;
  private List<Product> products;

  public ProductObject() {
  }

  public ProductObject(String statusCode, List<Product> products) {
    this.statusCode = statusCode;
    this.products = products;
  }


  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductObject productObject = (ProductObject) o;
    return Objects.equals(statusCode, productObject.statusCode) && Objects.equals(products, productObject.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, products);
  }

  @Override
  public String toString() {
    return getClass() + " " + "statusCode=" + statusCode + ", products=" + products;
  }
}
