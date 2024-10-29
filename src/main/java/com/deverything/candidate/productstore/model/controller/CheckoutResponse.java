package com.deverything.candidate.productstore.model.controller;

import java.io.Serializable;
import java.util.Objects;

public class CheckoutResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private Result result;

  public CheckoutResponse(Result result) {
    this.result = result;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (o == this || getClass() == o.getClass()) return true;
    CheckoutResponse checkoutResponse = (CheckoutResponse) o;
    return Objects.equals(result, checkoutResponse.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result);
  }

  @Override
  public String toString() {
    return getClass() + " " + "result=" + result;
  }
}
