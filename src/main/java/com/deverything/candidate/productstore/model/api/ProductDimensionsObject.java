package com.deverything.candidate.productstore.model.api;

import java.io.Serializable;
import java.util.Objects;

public class ProductDimensionsObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String statusCode;
  private int width;
  private int height;

  public ProductDimensionsObject() {
  }

  public ProductDimensionsObject(String statusCode, int width, int height) {
    this.statusCode = statusCode;
    this.width = width;
    this.height = height;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductDimensionsObject productDimensionsObject = (ProductDimensionsObject) o;
    return Objects.equals(statusCode, productDimensionsObject.statusCode) && productDimensionsObject.width == width && productDimensionsObject.height == height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, width, height);
  }

  @Override
  public String toString() {
    return getClass() + " " + "statusCode=" + statusCode + ", width=" + width + ", height=" + height;
  }
}
