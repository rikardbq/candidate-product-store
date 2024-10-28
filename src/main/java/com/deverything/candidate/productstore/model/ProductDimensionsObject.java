package com.deverything.candidate.productstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(value = {"statusCode"}, allowGetters = true)
public class ProductDimensionsObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private int width;
  private int height;

  public ProductDimensionsObject() {
  }

  public ProductDimensionsObject(int width, int height) {
    this.width = width;
    this.height = height;
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
    return productDimensionsObject.width == width && productDimensionsObject.height == height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }

  @Override
  public String toString() {
    return getClass() + " " + "width=" + width + ", height=" + height;
  }
}
