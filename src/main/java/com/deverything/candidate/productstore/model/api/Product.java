package com.deverything.candidate.productstore.model.api;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;
  private String name;
  private long price;

  public Product() {
  }

  public Product(int id, String name, long price) {
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
    Product product = (Product) o;
    return id == product.id && price == product.price && Objects.equals(name, product.name);
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
