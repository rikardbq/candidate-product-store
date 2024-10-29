package com.deverything.candidate.productstore.model.exception;

public class NoMatchingCriteriaException extends RuntimeException {

  public NoMatchingCriteriaException(String message, String cause) {
    super(message, new Throwable(cause));
  }
}
