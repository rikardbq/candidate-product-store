package com.deverything.candidate.productstore.model.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Result implements Serializable {
  private static final long serialVersionUID = 1L;

  private Object value;
  private List<String> error;

  public Result() {
  }

  public Object getValue() {
    return value;
  }

  public <T> void setValue(T value) {
    this.value = value;
  }

  public List<String> getError() {
    return error;
  }

  public void setError(List<String> error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (o == this || getClass() == o.getClass()) return true;
    Result result = (Result) o;
    return Objects.equals(value, result.value) && Objects.equals(error, result.error);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, error);
  }

  @Override
  public String toString() {
    return getClass() + " " + "value=" + value + ", error=" + error;
  }
}
