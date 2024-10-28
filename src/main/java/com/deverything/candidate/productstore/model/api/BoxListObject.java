package com.deverything.candidate.productstore.model.api;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BoxListObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String statusCode;
  private List<Box> boxes;

  public BoxListObject() {
  }

  public BoxListObject(String statusCode, List<Box> boxes) {
    this.statusCode = statusCode;
    this.boxes = boxes;
  }


  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public List<Box> getBoxes() {
    return boxes;
  }

  public void setBoxes(List<Box> boxes) {
    this.boxes = boxes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoxListObject boxListObject = (BoxListObject) o;
    return Objects.equals(statusCode, boxListObject.statusCode) && Objects.equals(boxes, boxListObject.boxes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, boxes);
  }

  @Override
  public String toString() {
    return getClass() + " " + "statusCode=" + statusCode + ", boxes=" + boxes;
  }
}
