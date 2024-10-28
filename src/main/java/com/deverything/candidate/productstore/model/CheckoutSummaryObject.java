package com.deverything.candidate.productstore.model;

import java.io.Serializable;
import java.util.Objects;

public class CheckoutSummaryObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String statusCode;
  private String result;

  public CheckoutSummaryObject() {
  }

  public CheckoutSummaryObject(String statusCode, String result) {
    this.statusCode = statusCode;
    this.result = result;
  }


  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CheckoutSummaryObject checkoutSummaryObject = (CheckoutSummaryObject) o;
    return Objects.equals(statusCode, checkoutSummaryObject.statusCode) && Objects.equals(result, checkoutSummaryObject.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, result);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}

