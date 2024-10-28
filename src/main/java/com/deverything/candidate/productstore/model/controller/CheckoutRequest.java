package com.deverything.candidate.productstore.model.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CheckoutRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  private long priceThreshold;
  private List<Integer> productIds;

  public CheckoutRequest() {
  }

  public CheckoutRequest(long priceThreshold, List<Integer> productIds) {
    this.priceThreshold = priceThreshold;
    this.productIds = productIds;
  }


  public long getPriceThreshold() {
    return priceThreshold;
  }

  public void setPriceThreshold(long priceThreshold) {
    this.priceThreshold = priceThreshold;
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
    CheckoutRequest checkoutRequest = (CheckoutRequest) o;
    return checkoutRequest.priceThreshold == priceThreshold && Objects.equals(productIds, checkoutRequest.productIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(priceThreshold, productIds);
  }

  @Override
  public String toString() {
    return getClass() + " " + "priceThreshold=" + priceThreshold + ", productIds=" + productIds;
  }
}
