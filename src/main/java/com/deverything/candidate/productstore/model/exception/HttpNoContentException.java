package com.deverything.candidate.productstore.model.exception;

public class HttpNoContentException extends RuntimeException {

  public HttpNoContentException(String entity) {
    super("STATUS=204,NO-CONTENT", new Throwable("HTTP response contained no body for entity=" + entity));
  }
}
