package com.deverything.candidate.productstore.model;

import java.io.Serializable;
import java.util.Objects;

public class BoxListObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;
  private int width;
  private int height;

  public BoxListObject() {
  }

  public BoxListObject(int id, int width, int height) {
    this.id = id;
    this.width = width;
    this.height = height;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
    BoxListObject boxListObject = (BoxListObject) o;
    return id == boxListObject.id && width == boxListObject.width && height == boxListObject.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, width, height);
  }

  @Override
  public String toString() {
    return getClass() + " " + "id=" + id + ", width=" + width + ", height=" + height;
  }
}
