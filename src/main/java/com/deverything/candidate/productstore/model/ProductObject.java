package com.deverything.candidate.productstore.model;

import java.io.Serializable;
import java.util.Objects;

public class ProductObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;
  private String name;
  private long price;

  public ProductObject() {
  }

  public ProductObject(int id, String name, long price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductObject productObject = (ProductObject) o;
    return id == productObject.id && price == productObject.price && Objects.equals(name, productObject.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price);
  }

  @Override
  public String toString() {
    return getClass() + " " + "id=" + id + ", name=" + name + ", price=" + price;
  }
}
