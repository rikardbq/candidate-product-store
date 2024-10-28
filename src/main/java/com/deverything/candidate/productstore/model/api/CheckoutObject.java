package com.deverything.candidate.productstore.model.api;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CheckoutObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private int boxId;
  private List<Integer> productIds;

  public CheckoutObject() {
  }

  public CheckoutObject(int boxId, List<Integer> productIds) {
    this.boxId = boxId;
    this.productIds = productIds;
  }


  public int getBoxId() {
    return boxId;
  }

  public void setBoxId(int boxId) {
    this.boxId = boxId;
  }

  public List<Integer> getProductIds() {
    return productIds;
  }

  public void setProductIds(List<Integer> productIds) {
    this.productIds = productIds;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CheckoutObject checkoutObject = (CheckoutObject) o;
    return checkoutObject.boxId == boxId && Objects.equals(productIds, checkoutObject.productIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boxId, productIds);
  }

  @Override
  public String toString() {
    return getClass() + " " + "boxId=" + boxId + ", productIds=" + productIds;
  }
}
